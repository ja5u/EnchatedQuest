package com.example.cognitive

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Key : AppCompatActivity() {

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key)

        // Initialize the linear layout to hold the cards
        val cardContainer: LinearLayout = findViewById(R.id.card_container) // Replace with your card container's id

        val ket_subject = "Android" //intent.getStringExtra("subject")
        val quizHelper = DatabaseHelper(this)
        val quizCursor = ket_subject?.let { quizHelper.getAllQuestionsBySubject(it) }

        // Iterate through the cursor to fetch questions and correct answers
        quizCursor?.moveToFirst()
        var questionNumber = 1
        if (quizCursor != null) {
            while (!quizCursor.isAfterLast) {
                val question = quizCursor.getString(quizCursor.getColumnIndex("question"))
                val correctAnswer = quizCursor.getString(quizCursor.getColumnIndex("correct_answer"))

                // Inflate the card layout
                val cardView = layoutInflater.inflate(R.layout.card_layout, null) as LinearLayout // Replace with your card layout's id

                // Find TextViews for question and answer within the card
                val textViewQuestion: TextView = cardView.findViewById(R.id.text_view_question)
                val textViewAnswer: TextView = cardView.findViewById(R.id.text_view_answer)

                // Set question and answer texts
                textViewQuestion.text = "Question $questionNumber: $question"
                textViewAnswer.text = "Correct Answer: $correctAnswer"

                // Add the card to the container
                cardContainer.addView(cardView)

                // Move to the next question
                quizCursor.moveToNext()
                questionNumber++
            }
        }
    }
}