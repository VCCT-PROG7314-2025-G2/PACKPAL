package com.example.prog7314poepart2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ManageProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_profile)

        val emailField = findViewById<EditText>(R.id.editEmail)
        val usernameField = findViewById<EditText>(R.id.editUsername)
        val changePasswordButton = findViewById<Button>(R.id.changePasswordButton)
        val changeEmailButton = findViewById<Button>(R.id.changeEmailButton)
        val deleteAccountButton = findViewById<Button>(R.id.deleteAccountButton)

        val sharedPref: SharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val editor = sharedPref.edit()

        // Populate fields with current email and username (assuming they were saved in SharedPreferences)
        emailField.setText(sharedPref.getString("email", ""))
        usernameField.setText(sharedPref.getString("username", ""))

        // Handle Change Email button click
        changeEmailButton.setOnClickListener {
            val newEmail = emailField.text.toString().trim()
            if (newEmail.isNotEmpty()) {
                editor.putString("email", newEmail)
                editor.apply()
                Toast.makeText(this, "Email updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle Change Password button click
        changePasswordButton.setOnClickListener {
            // Prompt user to enter the new password via an AlertDialog
            val newPasswordEditText = EditText(this).apply {
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

            val alertDialog = AlertDialog.Builder(this)
                .setTitle("Change Password")
                .setMessage("Enter new password:")
                .setView(newPasswordEditText)
                .setPositiveButton("OK") { _, _ ->
                    val newPassword = newPasswordEditText.text.toString().trim()

                    if (newPassword.isNotEmpty()) {
                        // Save new password to SharedPreferences
                        editor.putString("password", newPassword)
                        editor.apply()
                        Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Please enter a valid password", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .create()

            alertDialog.show()
        }

        // Handle Delete Account button click
        deleteAccountButton.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account?")
                .setPositiveButton("Yes") { _, _ ->
                    editor.clear()  // Clear all shared preferences (email, username, etc.)
                    editor.apply()
                    Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show()

                    // Redirect to login page after account deletion
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("No", null)
                .create()

            alertDialog.show()
        }
    }
}
