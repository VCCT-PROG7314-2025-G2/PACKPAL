package com.example.prog7314poepart2.ui.notifications

<<<<<<< HEAD
import android.content.Context
import android.content.Intent
=======
<<<<<<< HEAD
=======
import android.content.Context
import android.content.Intent
>>>>>>> 59c735b (Profile Edits)
>>>>>>> cc20775 (Profile Changes)
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.prog7314poepart2.LoginActivity
=======
<<<<<<< HEAD
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
=======
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.prog7314poepart2.LoginActivity
>>>>>>> 59c735b (Profile Edits)
>>>>>>> cc20775 (Profile Changes)
import com.example.prog7314poepart2.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
<<<<<<< HEAD
=======
<<<<<<< HEAD

    // This property is only valid between onCreateView and
    // onDestroyView.
=======
>>>>>>> 59c735b (Profile Edits)
>>>>>>> cc20775 (Profile Changes)
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
<<<<<<< HEAD
=======
<<<<<<< HEAD
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

>>>>>>> cc20775 (Profile Changes)
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
<<<<<<< HEAD

=======
=======
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

>>>>>>> 59c735b (Profile Edits)
>>>>>>> cc20775 (Profile Changes)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
<<<<<<< HEAD
}

=======
<<<<<<< HEAD
}
=======
}
>>>>>>> 59c735b (Profile Edits)
>>>>>>> cc20775 (Profile Changes)
