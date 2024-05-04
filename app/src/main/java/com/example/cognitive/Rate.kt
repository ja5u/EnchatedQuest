package com.example.cognitive

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class Rate : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate)

        // Retrieve username from intent extras
        val username = intent.getStringExtra("username")

        // Initialize views
        val feedbackTextInput = findViewById<TextInputEditText>(R.id.feedbackTextInput)
        val rateUs = findViewById<RatingBar>(R.id.rateus)
        val rateBtn = findViewById<Button>(R.id.rateBtn)

        rateBtn.setOnClickListener {
            // Get the feedback text
            val feedback = feedbackTextInput.text.toString().trim()

            // Get the rating value
            val rating = rateUs.rating

            // Save username, feedback, and rating in SharedPreferences
            saveFeedback(username, feedback, rating)

            // Show a thank you message
            Toast.makeText(this, "Thank You For The Rating: $rating out of 5", Toast.LENGTH_SHORT).show()

            // Finish the activity
            finish()
        }
    }

    private fun saveFeedback(username: String?, feedback: String, rating: Float) {
        // Initialize SharedPreferences
        val sharedPreferences = getSharedPreferences("feedback_data", MODE_PRIVATE)

        // Save username, feedback, and rating in SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("feedback", feedback)
        editor.putFloat("rating", rating)
        editor.apply()
    }
}
