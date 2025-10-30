package com.example.prog7314poepart2.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment  // Make sure to import the correct Fragment
import com.example.prog7314poepart2.LoginActivity
import com.example.prog7314poepart2.ManageProfileActivity
import com.example.prog7314poepart2.ManageTrips
import com.example.prog7314poepart2.R

class NotificationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        val rootView = inflater.inflate(R.layout.fragment_notifications, container, false)

        // Get references to the buttons
        val editProfileButton = rootView.findViewById<Button>(R.id.editProfileButton)
        val manageTripsButton = rootView.findViewById<Button>(R.id.manageTripsButton)
        val helpButton = rootView.findViewById<Button>(R.id.helpButton)
        val aboutButton = rootView.findViewById<Button>(R.id.aboutButton)
        val logoutButton = rootView.findViewById<Button>(R.id.logoutButton)

        // Handle Edit Profile button click
        editProfileButton.setOnClickListener {
            val intent = Intent(activity, ManageProfileActivity::class.java)
            startActivity(intent)
        }

        // Handle Manage Trips button click
        manageTripsButton.setOnClickListener {
            val intent = Intent(activity, ManageTrips::class.java)
            startActivity(intent)
        }

        // Handle Help button click
        helpButton.setOnClickListener {
            val helpDialog = AlertDialog.Builder(requireActivity())  // Using requireActivity for context
                .setTitle("Help")
                .setMessage("Here you can change your email, password, or delete your account. For more assistance, please contact support.")
                .setPositiveButton("OK", null)
                .create()

            helpDialog.show()
        }

        // Handle About button click
        aboutButton.setOnClickListener {
            val aboutDialog = AlertDialog.Builder(requireActivity())  // Using requireActivity for context
                .setTitle("About")
                .setMessage(
                    "This app was developed by PackPal.\n" +
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
            val sharedPref = activity?.getSharedPreferences("UserPrefs", AppCompatActivity.MODE_PRIVATE)  // Using activity context
            val editor = sharedPref?.edit()
            editor?.remove("logged_in_email")
            editor?.apply()

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()  // Use activity?.finish() to finish the current activity
        }

        return rootView
    }
}