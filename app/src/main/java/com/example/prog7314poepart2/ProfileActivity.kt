package com.example.prog7314poepart2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_notifications)

        val editProfileButton = findViewById<Button>(R.id.editProfileButton)
        val manageTripsButton = findViewById<Button>(R.id.manageTripsButton)
        val helpButton = findViewById<Button>(R.id.helpButton)
        val aboutButton = findViewById<Button>(R.id.aboutButton)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        // Handle Edit Profile button click
        editProfileButton.setOnClickListener {
            val intent = Intent(this, ManageProfileActivity::class.java)
            startActivity(intent)
        }

        // Handle Manage Trips button click
        manageTripsButton.setOnClickListener {
            // Navigate to Manage Trips activity
            val intent = Intent(this, ManageTrips::class.java)
            startActivity(intent)
        }

        // Handle Help button click
        helpButton.setOnClickListener {
            val helpDialog = AlertDialog.Builder(this)
                .setTitle("Help")
                .setMessage("Here you can change your email, password, or delete your account. For more assistance, please contact support.")
                .setPositiveButton("OK", null)
                .create()

            helpDialog.show()
        }

        // Handle About button click
        aboutButton.setOnClickListener {
            val aboutDialog = AlertDialog.Builder(this)
                .setTitle("About")
                .setMessage(
                    "This app was developed by [Your Name].\n" +
                            "Version: 1.0\n" +
                            "For more information, visit our website or contact support."
                )
                .setPositiveButton("OK", null)
                .create()

            aboutDialog.show()
        }

        // Handle Log out button click
        logoutButton.setOnClickListener {
            // Clear user preferences and navigate back to Login Activity
            val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
