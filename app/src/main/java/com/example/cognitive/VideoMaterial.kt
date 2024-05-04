package com.example.cognitive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.MediaController
import android.widget.VideoView
import android.content.Intent
import android.widget.Button

class VideoMaterial : AppCompatActivity() {
    private lateinit var videoView: VideoView
    private lateinit var mediaController: MediaController
    private lateinit var gestureDetector: GestureDetector
    private var currentPosition: Int = 0
    private var isVideoPlaying: Boolean = false
    private var isPrepared: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_material)

        // Get the VideoView and set its media controller
        videoView = findViewById(R.id.videoView)
        mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)


        val videoPath = "android.resource://" + packageName + "/" + R.raw.v1
        videoView.setVideoPath(videoPath)

        // Set up the gesture detector
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                if (isPrepared) {
                    if (videoView.isPlaying) {
                        videoView.pause()
                        isVideoPlaying = false
                    } else {
                        videoView.start()
                        isVideoPlaying = true
                    }
                }
                return true
            }
        })

        // Set up the video view to handle touch events
        videoView.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            true
        }

        // Set up the home button
        val homeButton = findViewById<Button>(R.id.homeButton)
        homeButton.setOnClickListener {
           val moveHome= Intent(this,HomeActivity::class.java)

            startActivity(moveHome)
        }

        // Restore the video position if savedInstanceState is not null
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("currentPosition")
            isVideoPlaying = savedInstanceState.getBoolean("isVideoPlaying")
            videoView.setOnPreparedListener {
                isPrepared = true
                if (!isVideoPlaying) {
                    videoView.seekTo(currentPosition)
                }
            }
        } else {
            videoView.setOnPreparedListener {
                isPrepared = true
                if (!isVideoPlaying) {
                    videoView.seekTo(currentPosition)
                } else {
                    videoView.start()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (videoView.isPlaying) {
            currentPosition = videoView.currentPosition
            videoView.pause()
            isVideoPlaying = false
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isVideoPlaying && videoView.canSeekForward()) {
            videoView.seekTo(currentPosition)
            videoView.start()
            isVideoPlaying = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentPosition", currentPosition)
        outState.putBoolean("isVideoPlaying", isVideoPlaying)
    }
}
