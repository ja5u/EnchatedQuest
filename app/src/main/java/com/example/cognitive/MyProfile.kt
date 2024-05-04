package com.example.cognitive

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MyProfile : AppCompatActivity() {
    lateinit var profileUsername:TextView
    lateinit var profileEmail:TextView
    lateinit var profilePassword:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

                val username =intent.getStringExtra("username")
                val email = intent.getStringExtra("email")
                val password = intent.getStringExtra("password")

                // Set user details to TextViews
        val titleName=findViewById<TextView>(R.id.titleName)
        val titleUsername=findViewById<TextView>(R.id.titleUsername)

        profileUsername=findViewById(R.id.profileUsername)
        profileEmail=findViewById(R.id.profileEmail)
        profilePassword=findViewById(R.id.profilePassword)

                profileUsername.text = username
                profileEmail.text = email
                profilePassword.text = password
        titleName.text=username
        titleUsername.text=email

        // Get user scores
       val scoreDataManager = ScoreDataManager(this)
        val userScores = username?.let { scoreDataManager.getUserScores(it) }

        val best_count=findViewById<TextView>(R.id.best_count)
        val avg_count=findViewById<TextView>(R.id.avg_count)
        val low_count=findViewById<TextView>(R.id.low_count)
        // Calculate best, average, and low scores
        val bestScores = userScores?.filter { it >= 90.0 }?.size
        val averageScores = userScores?.filter { it in 70.0..89.9 }?.size
        val lowScores = userScores?.filter { it < 70.0 }?.size

        // Now you can use these values as needed, e.g., display them in TextViews
        best_count.text = bestScores.toString()
        avg_count.text = averageScores.toString()
        low_count.text = lowScores.toString()
            }
        }

