package com.example.cognitive

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView

class Material : AppCompatActivity() {

    private val imageAndAmazonUrls = listOf(
        Triple("Android Programming: The Big Nerd Ranch Guide", R.drawable.andro1, "https://www.amazon.in/dp/0134706056?geniuslink=true"),
        Triple("Android Development 2e", R.drawable.andro2, "https://www.amazon.in/dp/1491974052?geniuslink=true"),
        Triple("Android Developers For Dummies", R.drawable.andro3, "https://www.amazon.in/dp/B01M27CCCE?geniuslink=true"),
        Triple("Android Programming: Pushing the Limits", R.drawable.andro4, "https://www.amazon.in/dp/B00G25D7ZM?geniuslink=true"),
        Triple("Android Development Advance Guide", R.drawable.andro5, "https://www.amazon.in/dp/098167805X?geniuslink=true"),
        Triple("Java: A Brain-Friendly Guide", R.drawable.java1, "https://www.amazon.in/Head-First-Java-Brain-Friendly-Guide/dp/8173666024"),
        Triple("Java Programming Basics: For Absolute Beginners", R.drawable.java2, "https://www.amazon.in/Java-Programming-Basics-Absolute-Beginners/dp/1978104472"),
        Triple("Java Core Volume I - Fundamentals", R.drawable.java3, "https://www.amazon.in/Core-Java-I-Fundamentals-Cay-Horstmann/dp/0135166306"),
        Triple("C++ Programming Language", R.drawable.c1, "https://www.amazon.in/C-Programming-Language-Bjarne-Stroustrup/dp/0321563840"),
        Triple("C++ Template Metaprogramming", R.drawable.c2, "https://www.amazon.in/Template-Metaprogramming-Techniques-Documents-Depth-ebook/dp/B003XNTTBW"),
        Triple("C++ Components: Building Blocks for Applications", R.drawable.c3, "https://www.amazon.in/Components-Algorithms-Scott-Robert-Ladd/dp/1558514082"),
        Triple("Python Crash Course", R.drawable.python1, "https://www.amazon.in/Python-Crash-Course-Eric-Matthes/dp/1593279280"),
        Triple("Python Cookbook: Recipes for Mastering Python 3", R.drawable.python2, "https://www.amazon.in/Python-Cookbook-3e-David-Beazley/dp/1449340377"),
        Triple("Python Data Analysis: Data Wrangling with Pandas, NumPy, and IPython", R.drawable.python3, "https://www.amazon.in/Python-Data-Analysis-Wrangling-Grayscale/dp/9355421907")
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)

        val cardContainer = findViewById<LinearLayout>(R.id.cardContainer)
        val searchView = findViewById<SearchView>(R.id.searchView)

        // Inflate and add cards dynamically
        imageAndAmazonUrls.forEach { (title, imageResId, url) ->
            val cardView = LayoutInflater.from(this)
                .inflate(R.layout.item_image_with_buy_button, cardContainer, false)

            val titleTextView = cardView.findViewById<TextView>(R.id.titleTextView)
            val imageView = cardView.findViewById<ImageView>(R.id.imageView)
            val button = cardView.findViewById<Button>(R.id.button)

            titleTextView.text = title
            imageView.setImageResource(imageResId)
            button.setOnClickListener {
                // Extract the URL associated with the clicked card
                val (_, _, url) = imageAndAmazonUrls.first { it.first == title }

                // Create an Intent to open the URL in a web browser
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }


            cardContainer.addView(cardView)
        }

        // Add a text change listener to the searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterCards(newText)
                return true
            }
        })
    }

    @SuppressLint("MissingInflatedId")
    private fun filterCards(query: String?) {
        val cardContainer = findViewById<LinearLayout>(R.id.cardContainer)
        cardContainer.removeAllViews()

        val filteredList = if (query.isNullOrEmpty()) {
            imageAndAmazonUrls // No filtering needed, show all cards
        } else {
            imageAndAmazonUrls.filter { it.first.contains(query, ignoreCase = true) }
        }

        filteredList.forEach { (title, imageResId, url) ->
            val cardView =
                LayoutInflater.from(this).inflate(R.layout.item_image_with_buy_button, cardContainer, false)

            val titleTextView = cardView.findViewById<TextView>(R.id.titleTextView)
            val imageView = cardView.findViewById<ImageView>(R.id.imageView)
            val button = cardView.findViewById<Button>(R.id.button)

            titleTextView.text = title
            imageView.setImageResource(imageResId)
            button.setOnClickListener {
                // Extract the URL associated with the clicked card
                val (_, _, url) = filteredList.first { it.first == title }

                // Create an Intent to open the URL in a web browser
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }

            cardContainer.addView(cardView)
        }
    }
}
