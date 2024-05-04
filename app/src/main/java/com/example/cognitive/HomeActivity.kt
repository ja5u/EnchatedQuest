package com.example.cognitive

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import java.util.Calendar

class HomeActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var mainLayout: ConstraintLayout
    companion object {
        const val SETTINGS_REQUEST_CODE = 1001
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var toolbar = findViewById<Toolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)
        // Initialize views
        mainLayout = findViewById(R.id.main)


        val email=intent.getStringExtra("email")

        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)

        var userName: String? = null
        var userPassword: String? = null

        val allKeys = sharedPreferences.all.keys

        for (key in allKeys) {
            // Check if the key contains the provided email
            if (email?.let { key.contains(it) } == true) {
                // Extract the user details based on the key
                when {
                    key.startsWith("username") -> userName = sharedPreferences.getString(key, "")
                    key.startsWith("password") -> userPassword = sharedPreferences.getString(key, "")
                }
            }
        }
        Log.d(TAG, "Entered email: $email")
        Log.d(TAG, "Entered name: $userName")
        Log.d(TAG, "Saved pasword: $userPassword")


        val android = findViewById<CardView>(R.id.android_id)
        val java = findViewById<CardView>(R.id.java_id)
        val cpp = findViewById<CardView>(R.id.cpp_id)
        val python = findViewById<CardView>(R.id.python_id)

        android.setOnClickListener {
            val andropage = Intent(this, RevisionTest::class.java)
            andropage.putExtra("subject", "Android")
            startActivity(andropage)
        }

        java.setOnClickListener {
            val javapage = Intent(this, RevisionTest::class.java)
            javapage.putExtra("subject", "Java")
            startActivity(javapage)
        }

        cpp.setOnClickListener {
            val cpppage = Intent(this, RevisionTest::class.java)
            cpppage.putExtra("subject", "C++")
            startActivity(cpppage)
        }

        python.setOnClickListener {
            val pythonpage = Intent(this, RevisionTest::class.java)
            pythonpage.putExtra("subject", "Python")
            startActivity(pythonpage)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.selectedItemId = R.id.bottom_home

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> true
                R.id.bottom_home -> {
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }
                R.id.bottom_remainder -> {
                    startActivity(Intent(applicationContext, Remainder::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }

                R.id.bottom_book -> {
                    startActivity(Intent(applicationContext, Material::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }

                R.id.bottom_score -> {
                    startActivity(Intent(applicationContext, Score::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }

                else -> false
            }

        }

        drawerLayout = findViewById(R.id.drawer_layout)

        // Setup ActionBarDrawerToggle
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set up navigation view item click listener
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Handle Home click
                    val homeNav=Intent(this,HomeActivity::class.java)
                    startActivity(homeNav)
                    true
                }
                R.id.nav_profile -> {

                    val profileNav=Intent(this,MyProfile::class.java)
                    profileNav.putExtra("username",userName)
                    profileNav.putExtra("email",email)
                    profileNav.putExtra("password",userPassword)
                    startActivity(profileNav)
                    true
                }
                R.id.nav_settings -> {
                    // Handle Settings click
                    val intent = Intent(this, Settings::class.java)
                    startActivityForResult(intent, SETTINGS_REQUEST_CODE)
                    true
                }
                R.id.nav_rate -> {
                    // Handle Rate Us click
                    val rateNv=Intent(this,Rate::class.java)
                    rateNv.putExtra("username",userName)
                    startActivity(rateNv)
                    true
                }
                R.id.nav_about -> {
                    // Handle About Us click
                  val HomeNav= Intent(this,About::class.java)
                    startActivity(HomeNav)
                    true
                }
                R.id.nav_logout -> {
                    // Handle Logout click
                  val logNav= Intent(this,MainActivity::class.java)
                    startActivity(logNav)
                    Toast.makeText(this,"Successfully Logged Out",Toast.LENGTH_LONG).show()
                    true
                }
                else -> false
            }
        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SETTINGS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Get the background color from the result data
            val color = data?.getIntExtra("backgroundColor", Color.WHITE)
            // Set the background color of the main layout
            if (color != null) {
                mainLayout.setBackgroundColor(color)
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem):Boolean{
        val id:Int = item.itemId
        if (id == R.id.menu_calendar) {
            // Create a Calendar instance to initialize the DatePickerDialog with the current date
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            // Create and show the DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    // Handle the selected date
                    val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                    Toast.makeText(applicationContext, selectedDate, Toast.LENGTH_LONG).show()
                },
                year,
                month,
                dayOfMonth
            )
            datePickerDialog.show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    }
