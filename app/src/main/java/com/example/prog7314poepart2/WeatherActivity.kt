package com.example.prog7314poepart2

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import com.example.prog7314poepart2.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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
        val btnPackingList = findViewById<Button>(R.id.btnPackingList)

        var tripIndex = intent.getIntExtra("tripIndex", -1)
        Log.d("WeatherActivity", "Received tripIndex: $tripIndex, trips size: ${TripRepository.trips.size}, trips: ${TripRepository.trips}")

        if (tripIndex == -1 && TripRepository.trips.isNotEmpty()) {
            tripIndex = 0
        }

        if (tripIndex != -1 && tripIndex < TripRepository.trips.size) {
            val trip = TripRepository.trips[tripIndex]

            tvTripName.text = "Trip: ${trip.tripName}"
            tvCountry.text = "Country: ${trip.country}"
            tvStartDate.text = "Start Date: ${trip.startDate}"
            tvEndDate.text = "End Date: ${trip.endDate}"
            tvNotes.text = "Notes: ${trip.notes ?: "None"}"
            tvTripTypes.text = "Trip Types: ${if (trip.tripTypes.isNotEmpty()) trip.tripTypes.joinToString(", ") else "None"}"
            tvWeatherCondition.text = "Weather: ${trip.weatherCondition}"

            val iconResId = when (trip.weatherCondition.lowercase()) {
                "clear" -> R.drawable.ic_sunny
                "clouds" -> R.drawable.ic_cloudy
                "rain" -> R.drawable.ic_rainy
                "snow" -> R.drawable.ic_snowy
                "thunderstorm" -> R.drawable.ic_thunderstorm
                else -> R.drawable.ic_sunny
            }
            weatherIcon.setImageResource(iconResId)

            val tripDays = calculateTripDays(trip.startDate, trip.endDate)
            Log.d("WeatherActivity", "Trip duration: $tripDays days")

            btnPackingList.setOnClickListener {
                showPackingListDialog(trip.weatherCondition, tripDays)
            }
        } else {
            tvWeatherCondition.text = "Weather: No trip available"
            weatherIcon.setImageResource(R.drawable.ic_sunny)
            btnPackingList.isEnabled = false
            Toast.makeText(this, "No trips available. Create a trip first.", Toast.LENGTH_LONG).show()
        }

        btnBack.setOnClickListener { finish() }
    }

    private fun calculateTripDays(startDate: String, endDate: String): Int {
        Log.d("WeatherActivity", "Raw dates -> start: '$startDate', end: '$endDate'")

        val patterns = arrayOf("yyyy-MM-dd", "yyyy/MM/dd", "dd-MM-yyyy", "dd/MM/yyyy")
        for (pattern in patterns) {
            try {
                val sdf = SimpleDateFormat(pattern, Locale.getDefault())
                val start = sdf.parse(startDate)
                val end = sdf.parse(endDate)
                if (start != null && end != null) {
                    val diff = end.time - start.time
                    val days = TimeUnit.MILLISECONDS.toDays(diff).toInt() + 1
                    val result = if (days < 1) 1 else days
                    Log.d("WeatherActivity", "Parsed with pattern '$pattern' -> days = $result")
                    return result
                }
            } catch (e: Exception) {
                // try next pattern
            }
        }

        Log.e("WeatherActivity", "Failed to parse dates (tried multiple patterns). Falling back to 1 day.")
        return 1
    }

    private fun showPackingListDialog(weatherCondition: String, days: Int) {
        val packingList = generatePackingList(weatherCondition, days)
        val packingListText = packingList.joinToString("\n") { "• $it" }

        AlertDialog.Builder(this)
            .setTitle("Packing List for $days-Day Trip (${weatherCondition.replaceFirstChar { it.uppercase() }})")
            .setMessage(packingListText)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun generatePackingList(weatherCondition: String, days: Int): List<String> {
        val baseList = when (weatherCondition.lowercase()) {
            "clear" -> listOf("Sunglasses", "Light clothing", "Sunscreen", "Hat", "Walking shoes")
            "clouds" -> listOf("Light jacket", "Comfortable clothes", "Umbrella", "Walking shoes")
            "rain" -> listOf("Rain jacket", "Umbrella", "Waterproof shoes", "Quick-dry clothes")
            "snow" -> listOf("Winter coat", "Gloves", "Scarf", "Thermal wear", "Boots")
            "thunderstorm" -> listOf("Waterproof jacket", "Umbrella", "Quick-dry clothing", "Power bank")
            else -> listOf("Comfortable clothing", "Shoes", "Umbrella", "Water bottle")
        }

        val essentials = listOf(
            "Underwear x$days",
            "Socks x$days",
            "T-shirts x$days",
            "Pants/Shorts x${(days / 2) + 1}",
            "Toiletries",
            "Phone charger",
            "Travel documents"
        )

        return essentials + baseList
    }
}
// (Andy's Tech Tutorials, 2022)
