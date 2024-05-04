package com.example.cognitive

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDate
import java.util.*

class ScoreDataManager(private val context: Context) {

    fun saveUserData(username: String, subject: String, date: LocalDate, score: Double)
    {
        val filename = "user_score_history.txt"
        val fileContents = "$username|$subject|$date|$score\n"
        try {
            context.openFileOutput(filename, Context.MODE_APPEND).use {
                it.write(fileContents.toByteArray())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle exception
        }
    }

    fun getUserScores(username: String): List<Double> {
        val scores = mutableListOf<Double>()
        val filename = "user_score_history.txt"
        try {
            val file = context.openFileInput(filename)
            val reader = BufferedReader(InputStreamReader(file))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val parts = line?.split("|")
                if (parts?.get(0) == username) {
                    val score = parts[3].toDoubleOrNull()
                    score?.let { scores.add(it) }
                }
            }
            file.close()
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle exception
        }
        return scores
    }
}
