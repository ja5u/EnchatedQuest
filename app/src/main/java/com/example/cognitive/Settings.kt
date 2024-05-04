package com.example.cognitive

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Settings : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize views
        val b1 = findViewById<Button>(R.id.whitebtn)
        val b2 = findViewById<Button>(R.id.blackbtn)
        val b3 = findViewById<Button>(R.id.bluebtn)
        val b4 = findViewById<Button>(R.id.graybtn)

        // Set click listener for apply button
        b1.setOnClickListener {
            // Get the selected color from a color picker or some other UI element
            val selectedColor = Color.WHITE // Example color, replace with actual color selection

            // Return the selected color to the Home activity
            val resultIntent = Intent()
            resultIntent.putExtra("backgroundColor", selectedColor)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
        b2.setOnClickListener {
            // Get the selected color from a color picker or some other UI element
            val selectedColor = Color.BLACK // Example color, replace with actual color selection

            // Return the selected color to the Home activity
            val resultIntent = Intent()
            resultIntent.putExtra("backgroundColor", selectedColor)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        b3.setOnClickListener {
            // Get the selected color from a color picker or some other UI element
            val selectedColor = Color.BLUE // Example color, replace with actual color selection

            // Return the selected color to the Home activity
            val resultIntent = Intent()
            resultIntent.putExtra("backgroundColor", selectedColor)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        b4.setOnClickListener {
            // Get the selected color from a color picker or some other UI element
            val selectedColor = Color.GRAY // Example color, replace with actual color selection

            // Return the selected color to the Home activity
            val resultIntent = Intent()
            resultIntent.putExtra("backgroundColor", selectedColor)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
