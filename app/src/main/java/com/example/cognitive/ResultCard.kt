package com.example.cognitive

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate

class ResultCard : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private lateinit var scorePercentage: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_card)


        val score: TextView = findViewById(R.id.text_score)
        val performance: TextView = findViewById(R.id.text_performance)
        val keyButton: Button = findViewById(R.id.keybtn)
        val retryButton: Button = findViewById(R.id.trybtn)
        val homeButton: Button = findViewById(R.id.homebtn)
        val videoButton: Button = findViewById(R.id.videobtn)
        val materialButton: Button = findViewById(R.id.mattbn)

         scorePercentage = findViewById(R.id.scorePercentage)


        val subject_val = intent.getStringExtra("subject")
        val scoreMessage = intent.getStringExtra("result")
        val percentageScore = intent.getFloatExtra("score", 0f)


        score.text = scoreMessage+" "+"$percentageScore"
        scorePercentage.max= 100
       scorePercentage.progress = percentageScore.toInt()
        val username = intent.getStringExtra("username")// Retrieve the username from wherever it's stored

        // Call saveUserData function to save user score data
        val userDataManager = ScoreDataManager(this)
        val today = LocalDate.now()
        if (subject_val != null) {
            if (username != null) {
                userDataManager.saveUserData(username, subject_val, today, percentageScore.toDouble())
            }
        }


        if (percentageScore <= 50) {
            performance.text = "You Have Room To Grow, Try These Basic Materials"
        } else if (percentageScore <= 80) {
            performance.text = "Good, You Have Strong Basics, Try These Intermediate Materials"
        } else {
            performance.text =
                "Excellent, Why Not Master this Subject? Try These Advanced Materials"
        }

        // Map to store the level thresholds for each subject
        val thresholdsMap = mapOf(
            "Android" to listOf(50, 80),
            "Java" to listOf(50, 80),
            "C++" to listOf(50, 80),
            "Python" to listOf(50, 80)
        )

        // Function to get the level based on the percentage score and subject
        fun getLevel(subject: String, percentageScore: Int): String {
            val thresholds = thresholdsMap[subject] ?: return ""
            return when {
                percentageScore <= thresholds[0] -> "${subject.toLowerCase()}Basic"
                percentageScore <= thresholds[1] -> "${subject.toLowerCase()}Intermediate"
                else -> "${subject.toLowerCase()}Advanced"
            }
        }

        val level = subject_val?.let { getLevel(it, percentageScore.toInt()) }

        keyButton.setOnClickListener {
            // Handle Key button click
            val keyInt = Intent(this, Key::class.java)
            keyInt.putExtra("subject", "Android")
            startActivity(keyInt)
        }

        retryButton.setOnClickListener {
            // Handle Retry button click
            val retry = Intent(this, RevisionTest::class.java)
            retry.putExtra("subject", subject_val)
            startActivity(retry)
        }

        homeButton.setOnClickListener {
            // Handle Home button click
            val homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
        }

        videoButton.setOnClickListener {
            // Handle Video button click
            val videoIntent = Intent(this, VideoMaterial::class.java)
            startActivity(videoIntent)
        }

        materialButton.setOnClickListener {
            // Handle Material button click

            val noteIntent = Intent(this, NotesMaterial::class.java)
            noteIntent.putExtra("level",level)
            startActivity(noteIntent)
        }
    }
}
