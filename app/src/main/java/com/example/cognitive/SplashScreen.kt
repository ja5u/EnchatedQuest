package com.example.cognitive

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

class SplashScreen : AppCompatActivity() {
    private lateinit var appName: TextView
    private lateinit var lottie: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        appName = findViewById(R.id.appname)
        lottie = findViewById(R.id.lottie)

        // Animate TextView
        appName.animate().translationY(-1400f).setDuration(3000).setStartDelay(0)

        // Animate LottieAnimationView
        lottie.animate().translationY(1400f).setDuration(3000).setStartDelay(0)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                val int = Intent(this,MainActivity::class.java)
                startActivity(int)
                finish()
            }, 3000
        )

    }
}
