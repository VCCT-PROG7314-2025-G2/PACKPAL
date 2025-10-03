package com.example.prog7314poepart2

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.prog7314poepart2.R

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val tvTripName = findViewById<TextView>(R.id.tvTripName)
        val tvCountry = findViewById<TextView>(R.id.tvCountry)
        val tvStartDate = findViewById<TextView>(R.id.tvStartDate)
        val tvEndDate = findViewById<TextView>(R.id.tvEndDate)
        val tvNotes = findViewById<TextView>(R.id.tvNotes)
        val tvTripTypes = findViewById<TextView>(R.id.tvTripTypes)
        val tvWeatherCondition = findViewById<TextView>(R.id.tvWeatherCondition)
        val weatherIcon = findViewById<ImageView>(R.id.weatherIcon)
        val btnBack = findViewById<Button>(R.id.btnBack)

        // Get trip index from Intent, default to 0 if trips exist
        var tripIndex = intent.getIntExtra("tripIndex", -1)
        Log.d("WeatherActivity", "Received tripIndex: $tripIndex, trips size: ${TripRepository.trips.size}, trips: ${TripRepository.trips}")

        if (tripIndex == -1 && TripRepository.trips.isNotEmpty()) {
            tripIndex = 0 // Default to first trip
            Log.d("WeatherActivity", "No tripIndex provided, defaulting to tripIndex: 0")
        }

        if (tripIndex != -1 && tripIndex < TripRepository.trips.size) {
            val trip = TripRepository.trips[tripIndex]
            Log.d("WeatherActivity", "Trip details: $trip")

            // Set trip details
            tvTripName.text = "Trip: ${trip.tripName}"
            tvCountry.text = "Country: ${trip.country}"
            tvStartDate.text = "Start Date: ${trip.startDate}"
            tvEndDate.text = "End Date: ${trip.endDate}"
            tvNotes.text = "Notes: ${trip.notes ?: "None"}"
            tvTripTypes.text = "Trip Types: ${if (trip.tripTypes.isNotEmpty()) trip.tripTypes.joinToString(", ") else "None"}"
            tvWeatherCondition.text = "Weather: ${trip.weatherCondition}"

            // Set weather icon
            val iconResId = when (trip.weatherCondition.lowercase()) {
                "clear" -> R.drawable.ic_sunny
                "clouds" -> R.drawable.ic_cloudy
                "rain" -> R.drawable.ic_rainy
                "snow" -> R.drawable.ic_snowy
                "thunderstorm" -> R.drawable.ic_thunderstorm
                else -> {
                    Log.w("WeatherActivity", "Unknown weather condition: ${trip.weatherCondition}, defaulting to ic_sunny")
                    R.drawable.ic_sunny // Fallback to sunny instead of unknown
                }
            }
            Log.d("WeatherActivity", "Setting icon for condition: ${trip.weatherCondition}, resource: $iconResId")
            weatherIcon.setImageResource(iconResId)
        } else {
            Log.e("WeatherActivity", "Invalid trip index: $tripIndex, trips size: ${TripRepository.trips.size}")
            tvWeatherCondition.text = "Weather: No trip available"
            weatherIcon.setImageResource(R.drawable.ic_sunny) // Avoid ic_unknown
            Toast.makeText(this, "No trips available. Create a trip first.", Toast.LENGTH_LONG).show()
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}