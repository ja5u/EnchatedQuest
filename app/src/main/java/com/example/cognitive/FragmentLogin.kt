package com.example.cognitive
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class FragmentLogin : Fragment() {
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Initialize views
        editTextEmail = view.findViewById(R.id.login_email)
        editTextPassword = view.findViewById(R.id.login_password)
        buttonLogin = view.findViewById(R.id.login_button)

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)

        // Set click listener for login button
        buttonLogin.setOnClickListener { login() }

        return view
    }

    private fun login() {
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        // Retrieve saved email and password from SharedPreferences
        val savedEmail = sharedPreferences.getString("email_$email", "")
        val savedPassword = sharedPreferences.getString("password_$email", "")

        // Check if email and password match the saved data
        if (email == savedEmail && password == savedPassword) {
            // Login successful
            showToast("Login successful")

            // Navigate to home page
            navigateToHome(email)
        } else {
            // Login failed
            showToast("Invalid email or password")
        }
    }

    private fun navigateToHome(email: String) {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
