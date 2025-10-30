package com.example.prog7314poepart2

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()

        val emailField = findViewById<EditText>(R.id.editTextSignupEmail)
        val passwordField = findViewById<EditText>(R.id.editTextSignupPassword)
        val confirmPasswordField = findViewById<EditText>(R.id.editTextSignupConfirmPassword)
        val signupButton = findViewById<Button>(R.id.btnCreateAccount)

        val sharedPref: SharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        signupButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val confirmPassword = confirmPasswordField.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    // ✅ Save user account credentials
                    val editor = sharedPref.edit()
                    editor.putString("user_email", email)
                    editor.putString("user_password", password)
                    editor.apply()

                    Toast.makeText(this, "Account created! Please login.", Toast.LENGTH_SHORT).show()

                   
                    finish()
                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
