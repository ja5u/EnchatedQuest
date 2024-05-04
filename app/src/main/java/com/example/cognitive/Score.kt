package com.example.cognitive

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Score : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        // Get the username from the intent extras
        val username =  "Jaswant" //intent.getStringExtra("USERNAME")

        // Display user history for the given username
        displayUserHistory(username)
    }

    private fun displayUserHistory(username: String?) {
        val userDataList = readUserDataFromFile()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = UserHistoryAdapter(this, userDataList, username)
    }

    private fun readUserDataFromFile(): List<String> {
        val userDataList = mutableListOf<String>()
        val filename = "user_score_history.txt"
        try {
            val inputStream = openFileInput(filename)
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                userDataList.add(line!!)
            }
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle exception
        }
        return userDataList
    }
}

class UserHistoryAdapter(
    private val context: Context,
    private val userDataList: List<String>,
    private val username: String?
) : RecyclerView.Adapter<UserHistoryAdapter.UserHistoryViewHolder>() {

    class UserHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)
        val subjectTextView: TextView = itemView.findViewById(R.id.subjectTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val scoreTextView: TextView = itemView.findViewById(R.id.scoreTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHistoryViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.user_history_card, parent, false)
        return UserHistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserHistoryViewHolder, position: Int) {
        val tokens = userDataList[position].split("|")
        if (tokens.size == 4 && tokens[0] == username) {
            holder.usernameTextView.text = "Username: ${tokens[0]}"
            holder.subjectTextView.text = "Subject: ${tokens[1]}"
            val date = LocalDate.parse(tokens[2])
            // Format the LocalDate object into a string for display
            val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
            holder.dateTextView.text = "Date: ${date.format(dateFormat)}"
            holder.scoreTextView.text = "Score: ${tokens[3]}"

            // Add click listener to the card
            holder.itemView.setOnClickListener {
                val score = tokens[3].toDoubleOrNull() ?: 0.0
                showResultPopup(it, score)
            }
        }
    }

    override fun getItemCount(): Int {
        return userDataList.size
    }

    private fun showResultPopup(anchorView: View, score: Double) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.custom_popup, null)
        val popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val resultTextView = popupView.findViewById<TextView>(R.id.popUpText)
        resultTextView.text = when {
            score >= 80 -> "Good Result"
            score >= 60 -> "Average Result"
            else -> "Bad Result"
        }

        popupWindow.isFocusable = true
        popupWindow.showAsDropDown(anchorView, 0, 0)
    }

}
