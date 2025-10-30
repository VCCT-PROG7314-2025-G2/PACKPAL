package com.example.prog7314poepart2

import android.content.Intent
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

        val changePasswordButton = findViewById<Button>(R.id.changePasswordButton)
        val deleteAccountButton = findViewById<Button>(R.id.deleteAccountButton)

        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val editor = sharedPref.edit()


        changePasswordButton.setOnClickListener {
            val newPasswordInput = EditText(this).apply {
                hint = "Enter new password"
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

            AlertDialog.Builder(this)
                .setTitle("Change Password")
                .setView(newPasswordInput)
                .setPositiveButton("Save") { _, _ ->
                    val newPassword = newPasswordInput.text.toString().trim()
                    if (newPassword.isNotEmpty()) {
                        editor.putString("user_password", newPassword)
                        editor.apply()
                        Toast.makeText(this, "Password updated successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Password cannot be empty.", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        findViewById<Button>(R.id.btnBack2).setOnClickListener { finish() }


        deleteAccountButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account? This cannot be undone.")
                .setPositiveButton("Yes") { _, _ ->
                    editor.clear()
                    editor.apply()
                    Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }
}
// (FineGap, 2025)
