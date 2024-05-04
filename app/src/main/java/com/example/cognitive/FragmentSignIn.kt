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

class FragmentSignIn : Fragment() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextUsername: EditText
    private lateinit var buttonSignIn: Button

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        // Initialize views
        editTextEmail = view.findViewById(R.id.signup_email)
        editTextPassword = view.findViewById(R.id.signup_confirm)
        editTextUsername = view.findViewById(R.id.signup_username)
        buttonSignIn = view.findViewById(R.id.signup_button)

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)

        // Set click listener for sign-in button
        buttonSignIn.setOnClickListener { signIn() }

        return view
    }

    private fun signIn() {
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        val username = editTextUsername.text.toString().trim()

        // Check if the email already exists
        if (isEmailExists(email)) {
            showToast("Email already exists")
            return
        }

        // Get a reference to the SharedPreferences editor
        val editor = sharedPreferences.edit()

        // Retrieve the existing set of emails
        val emailsSet = sharedPreferences.getStringSet("emails", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

        // Add the new email to the set
        emailsSet.add(email)

        // Save the updated set of emails
        editor.putStringSet("emails", emailsSet)

        // Save the user data using the email as the key
        editor.putString("email_$email", email)
        editor.putString("password_$email", password)
        editor.putString("username_$email", username)

        // Apply changes to SharedPreferences
        editor.apply()

        // Show success message
        showToast("Data saved successfully")

        // Navigate to home page after sign-in
        navigateToHome()
    }

    private fun isEmailExists(email: String): Boolean {
        // Retrieve the set of emails from SharedPreferences
        val emailsSet = sharedPreferences.getStringSet("emails", mutableSetOf()) ?: mutableSetOf()

        // Check if the provided email exists in the set
        return emailsSet.contains(email)
    }


    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToHome() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }
}
