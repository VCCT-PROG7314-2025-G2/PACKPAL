package com.example.prog7314poepart2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.prog7314poepart2.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Inflate the layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the BottomNavigationView
        val navView: BottomNavigationView = binding.navView

        // Setup the NavController
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Remove ActionBar setup
        // (No setupActionBarWithNavController needed because we have NoActionBar theme)
        navView.setupWithNavController(navController)
    }
}
