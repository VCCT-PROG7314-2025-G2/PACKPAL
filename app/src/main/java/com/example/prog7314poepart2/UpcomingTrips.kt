package com.example.prog7314poepart2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class UpcomingTrips : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_trips)

        // Back button: goes to MainActivity (homepage)
        findViewById<Button>(R.id.btnBack).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Launch CreateTrips activity
        val createTripLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                // no adapter anymore, so handle UI update differently if needed
            }
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            createTripLauncher.launch(Intent(this, CreateTrips::class.java))
        }

        findViewById<Button>(R.id.button3).setOnClickListener {
            startActivity(Intent(this, ManageTrips::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
    }
}

