package com.example.cognitive

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RevisionTest : AppCompatActivity() {

    private lateinit var textQuestion: TextView
    private lateinit var btnNext: Button
    private lateinit var btnChoose1: Button
    private lateinit var btnChoose2: Button
    private lateinit var btnChoose3: Button
    private lateinit var btnChoose4: Button
    private lateinit var quizCursor: Cursor
    private var currentScore = 0
    private var totalQuestions = 0
    private lateinit var progressBar: ProgressBar
    private lateinit var nof:TextView
    private lateinit var subject:String


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_revision_test)
         subject = intent.getStringExtra("subject").toString()

        // Initialize views
        textQuestion = findViewById(R.id.text_question)
        btnNext = findViewById(R.id.btn_next)
        btnChoose1 = findViewById(R.id.btn_choose1)
        btnChoose2 = findViewById(R.id.btn_choose2)
        btnChoose3 = findViewById(R.id.btn_choose3)
        btnChoose4 = findViewById(R.id.btn_choose4)
        progressBar = findViewById(R.id.progressBar)
        nof = findViewById(R.id.nof)

        // Assume you have an instance of DatabaseHelper for the Android subject
        val quizHelper = DatabaseHelper(this)
        quizCursor = subject?.let { quizHelper.getAllQuestionsBySubject(it) }!!

        // Set progress bar max value
        progressBar.max = 10

        // Load initial question
        loadNextQuestion()

        // Set click listener for Next button
        btnNext.setOnClickListener {
            // Load next question
            loadNextQuestion()
        }

        // Set click listener for Choose buttons
        btnChoose1.setOnClickListener { checkAnswer(btnChoose1.text.toString()) }
        btnChoose2.setOnClickListener { checkAnswer(btnChoose2.text.toString()) }
        btnChoose3.setOnClickListener { checkAnswer(btnChoose3.text.toString()) }
        btnChoose4.setOnClickListener { checkAnswer(btnChoose4.text.toString()) }
    }

    @SuppressLint("Range")
    private fun loadNextQuestion() {
        // Load the next question from the database
        if (quizCursor.moveToNext()) {
            val question = quizCursor.getString(quizCursor.getColumnIndex("question"))
            val option1 = quizCursor.getString(quizCursor.getColumnIndex("option_a"))
            val option2 = quizCursor.getString(quizCursor.getColumnIndex("option_b"))
            val option3 = quizCursor.getString(quizCursor.getColumnIndex("option_c"))
            val option4 = quizCursor.getString(quizCursor.getColumnIndex("option_d"))

            textQuestion.text = question
            btnChoose1.text = option1
            btnChoose2.text = option2
            btnChoose3.text = option3
            btnChoose4.text = option4

            // Update progress

            totalQuestions++
            progressBar.progress = totalQuestions
            nof.text="${totalQuestions}/10"
        } else {
            // If no more questions, show the score
            showScore()
        }
    }

    @SuppressLint("Range")
    private fun checkAnswer(chosenOption: String) {
        val correctAnswer = quizCursor.getString(quizCursor.getColumnIndex("correct_answer"))
        // Compare chosen option with correct answer
        if (chosenOption == correctAnswer) {
            // Increment the score if the answer is correct
            currentScore++
        }
        // Load the next question
        loadNextQuestion()
    }

    private fun showScore() {
        // Calculate the percentage score
        val percentageScore = (currentScore.toFloat() / progressBar.max.toFloat()) * 100

        // Display the score
        val scoreMessage = "You scored $currentScore out of ${progressBar.max}. Percentage Score: $percentageScore%"
        val resIntent= Intent(this, ResultCard::class.java)
        resIntent.putExtra("result", scoreMessage)
        resIntent.putExtra("score", percentageScore)
        resIntent.putExtra("subject", subject)
        startActivity(resIntent)


    //        textQuestion.text = scoreMessage
//
//        // Hide the Choose buttons
//        btnChoose1.visibility = View.GONE
//        btnChoose2.visibility = View.GONE
//        btnChoose3.visibility = View.GONE
//        btnChoose4.visibility = View.GONE
//
//        // Disable the Next button
//        btnNext.isEnabled = false
    }
}
