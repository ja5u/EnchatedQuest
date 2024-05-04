package com.example.cognitive

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class Remainder : AppCompatActivity() {

    private lateinit var selectTimeTextView: TextView
    private lateinit var setAlarmButton: Button
    private lateinit var cancelAlarmButton: Button

    private lateinit var timePicker: MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remiander)

        selectTimeTextView = findViewById(R.id.selectTime)
        setAlarmButton = findViewById(R.id.setAlarm)
        cancelAlarmButton = findViewById(R.id.cancelAlarm)

        createNotificationChannel()

        selectTimeTextView.setOnClickListener {
            showTimePickerDialog()
        }

        setAlarmButton.setOnClickListener {
            setAlarm()
        }

        cancelAlarmButton.setOnClickListener {
            cancelAlarm()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "akchannel"
            val desc = "Channel for Alarm Manager"
            val imp = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("androidknowledge", name, imp)
            channel.description = desc

            val notificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showTimePickerDialog() {
        timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Alarm Time")
            .build()

        timePicker.show(supportFragmentManager, "androidknowledge")
        timePicker.addOnPositiveButtonClickListener {
            val timeText = if (timePicker.hour > 12) {
                "${String.format("%02d", timePicker.hour - 12)}:${String.format("%02d", timePicker.minute)} PM"
            } else {
                "${timePicker.hour}:${timePicker.minute} AM"
            }
            selectTimeTextView.text = timeText


        }
    }

    private fun setAlarm() {
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 123, intent,
            PendingIntent.FLAG_IMMUTABLE)
        val currentTimeMillis = System.currentTimeMillis()
        if (timePicker.hour > 12){
            val selectedTimeMillis = calculateAlarmTime(timePicker.hour - 12, timePicker.minute, currentTimeMillis)
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                1000,
                pendingIntent
            )
            Log.d("AlarmReceiver", "${selectedTimeMillis}")
        }
        val selectedTimeMillis = calculateAlarmTime(timePicker.hour, timePicker.minute, currentTimeMillis)

    alarmManager.set(
            AlarmManager.RTC_WAKEUP,
        1000,
            pendingIntent
        )

        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show()
        Log.d("AlarmReceiver", "${timePicker.hour}")
    }

    private fun calculateAlarmTime(hour: Int, minute: Int, currentTimeMillis: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTimeMillis
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        return if (calendar.timeInMillis <= currentTimeMillis) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            calendar.timeInMillis
        } else {
            calendar.timeInMillis
        }

    }
    private fun cancelAlarm() {
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 123, intent,
            PendingIntent.FLAG_IMMUTABLE)

        if (::alarmManager.isInitialized.not()) {
            alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        }
        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show()
    }
}
