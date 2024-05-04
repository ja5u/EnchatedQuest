package com.example.cognitive

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.barteksc.pdfviewer.PDFView

class NotesMaterial : AppCompatActivity() {
    private lateinit var pdfView: PDFView
    private val pdfMap = mapOf(
        "androidBasic" to "android_basic.pdf",
        "androidIntermediate" to "android_intermediate.pdf",
        "androidAdvanced" to "android_advanced.pdf",
        "c++Basic" to "c++_basic.pdf",
        "c++Intermediate" to "c++_intermediate.pdf",
        "c++Advanced" to "c++_advanced.pdf",
        "javaBasic" to "java_basic.pdf",
        "javaIntermediate" to "java_intermediate.pdf",
        "javaAdvanced" to "java_advanced.pdf",
        "pythonBasic" to "python_basic.pdf",
        "pythonIntermediate" to "python_intermediate.pdf",
        "pythonAdvanced" to "python_advanced.pdf",
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_material)

        // Retrieve the level from the intent
        val level = intent.getStringExtra("level")

        // Get the corresponding PDF file name from the map
        val pdfFileName = pdfMap[level]

        // Check if the pdfFileName is not null
        if (pdfFileName != null) {
            // Initialize the PDFView and load the PDF
            pdfView = findViewById(R.id.pdfView)
            pdfView.fromAsset(pdfFileName).load()
        } else {
            pdfView = findViewById(R.id.pdfView)
            pdfView.fromAsset("default.pdf").load()
        }
    }

}
