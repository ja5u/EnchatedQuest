package com.example.cognitive

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "quiz_database"
        private const val TABLE_QUESTIONS = "questions"
        private const val COLUMN_ID = "id"
        private const val COLUMN_SUBJECT = "subject"
        private const val COLUMN_QUESTION = "question"
        private const val COLUMN_OPTION_A = "option_a"
        private const val COLUMN_OPTION_B = "option_b"
        private const val COLUMN_OPTION_C = "option_c"
        private const val COLUMN_OPTION_D = "option_d"
        private const val COLUMN_CORRECT_ANSWER = "correct_answer"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_QUESTIONS "
                + "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_SUBJECT TEXT, "
                + "$COLUMN_QUESTION TEXT, "
                + "$COLUMN_OPTION_A TEXT, "
                + "$COLUMN_OPTION_B TEXT, "
                + "$COLUMN_OPTION_C TEXT, "
                + "$COLUMN_OPTION_D TEXT, "
                + "$COLUMN_CORRECT_ANSWER TEXT)")
        db.execSQL(createTableQuery)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_QUESTIONS")
        onCreate(db)
    }

    fun addQuestion(subject: String, question: String, optionA: String, optionB: String, optionC: String, optionD: String, correctAnswer: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_SUBJECT, subject)
        values.put(COLUMN_QUESTION, question)
        values.put(COLUMN_OPTION_A, optionA)
        values.put(COLUMN_OPTION_B, optionB)
        values.put(COLUMN_OPTION_C, optionC)
        values.put(COLUMN_OPTION_D, optionD)
        values.put(COLUMN_CORRECT_ANSWER, correctAnswer)
        val id = db.insert(TABLE_QUESTIONS, null, values)
        db.close()
        return id
    }

    fun deleteQuestion(id: Long): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_QUESTIONS, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun getAllQuestionsBySubject(subject: String): Cursor {
        val db = this.readableDatabase
        val selection = "$COLUMN_SUBJECT = ?"
        val selectionArgs = arrayOf(subject)
        return db.query(TABLE_QUESTIONS, null, selection, selectionArgs, null, null, null)
    }


}
