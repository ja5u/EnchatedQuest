package com.example.cognitive

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class About : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private val images = listOf(
        R.drawable.q1,
        R.drawable.q2,
        R.drawable.q3
    )
    private var currentIndex = 0
    private val handler = Handler()

    private val imageChangerRunnable = object : Runnable {
        override fun run() {
            // Change the image
            imageView.setImageResource(images[currentIndex])
            currentIndex = (currentIndex + 1) % images.size
            // Post the same runnable after 3 seconds
            handler.postDelayed(this, 3000)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about)

        // Get the ImageView
        imageView = findViewById(R.id.aboutView)

        // Apply padding to handle system insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        // Start the image changing loop when the activity is resumed
        handler.postDelayed(imageChangerRunnable, 3000)
    }

    override fun onPause() {
        super.onPause()
        // Stop the image changing loop when the activity is paused to prevent memory leaks
        handler.removeCallbacks(imageChangerRunnable)
    }
}
