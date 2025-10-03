package com.example.prog7314poepart2.ui.notifications

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.prog7314poepart2.LoginActivity
import com.example.prog7314poepart2.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Load currently logged-in email
        val sharedPref = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val email = sharedPref.getString("logged_in_email", "No Email Found")

        binding.email.text = "Email: $email"

        // Logout button action
        binding.logoutButton.setOnClickListener {
            // 1️⃣ Clear logged-in email
            sharedPref.edit().remove("logged_in_email").apply()

            // 2️⃣ Show toast
            Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show()

            // 3️⃣ Redirect to LoginActivity
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

