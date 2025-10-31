package com.example.prog7314poepart2.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.prog7314poepart2.LoginActivity
import com.example.prog7314poepart2.ManageProfileActivity
import com.example.prog7314poepart2.ManageTrips
import com.example.prog7314poepart2.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class NotificationsFragment : Fragment() {

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_notifications, container, false)

        val editProfileButton = rootView.findViewById<Button>(R.id.editProfileButton)
        val manageTripsButton = rootView.findViewById<Button>(R.id.manageTripsButton)
        val helpButton = rootView.findViewById<Button>(R.id.helpButton)
        val aboutButton = rootView.findViewById<Button>(R.id.aboutButton)
        val logoutButton = rootView.findViewById<Button>(R.id.logoutButton)

        // Setup Google Sign-In Client (same config as LoginActivity)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        // --- Button Listeners ---

        editProfileButton.setOnClickListener {
            startActivity(Intent(activity, ManageProfileActivity::class.java))
        }

        manageTripsButton.setOnClickListener {
            startActivity(Intent(activity, ManageTrips::class.java))
        }

        helpButton.setOnClickListener {
            AlertDialog.Builder(requireActivity())
                .setTitle("Help")
                .setMessage("Here you can change your email, password, or delete your account. For more assistance, please contact support.")
                .setPositiveButton("OK", null)
                .show()
        }

        aboutButton.setOnClickListener {
            AlertDialog.Builder(requireActivity())
                .setTitle("About")
                .setMessage("This app was developed by PackPal.\nVersion: 1.0\nFor more information, visit our website or contact support.")
                .setPositiveButton("OK", null)
                .show()
        }

        // ✅ Proper Google Logout
        logoutButton.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

            // Step 1: Sign out from Firebase
            com.google.firebase.auth.FirebaseAuth.getInstance().signOut()

            // Step 2: Sign out from Google
            googleSignInClient.signOut().addOnCompleteListener {
                // Step 3: Clear prefs (optional)
                val sharedPref = activity?.getSharedPreferences("UserPrefs", AppCompatActivity.MODE_PRIVATE)
                sharedPref?.edit()?.clear()?.apply()

                // Step 4: Go to LoginActivity and clear backstack
                val intent = Intent(requireContext(), com.example.prog7314poepart2.LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()
            }
        }

        return rootView
    }
}
