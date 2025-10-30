package com.example.prog7314poepart2

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.net.URLEncoder
import java.util.*

class CreateTrips : AppCompatActivity() {

    private lateinit var weatherText: TextView
    private val client = OkHttpClient()
    private var weatherCondition: String = ""

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                AlertDialog.Builder(this)
                    .setTitle("Enable Notifications")
                    .setMessage("Please enable notifications to get trip achievements and updates.")
                    .setPositiveButton("Open Settings") { _, _ ->
                        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                            .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                        startActivity(intent)
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_trips)

        val tripName = findViewById<EditText>(R.id.etTripName)
        val country = findViewById<EditText>(R.id.etCountry)
        val startDate = findViewById<EditText>(R.id.etStartDate)
        val endDate = findViewById<EditText>(R.id.etEndDate)
        val notes = findViewById<EditText>(R.id.etNotes)
        val createButton = findViewById<Button>(R.id.btnCreateTrip)
        weatherText = findViewById(R.id.weatherText)

        val cbBusiness = findViewById<CheckBox>(R.id.cbBusiness)
        val cbLeisure = findViewById<CheckBox>(R.id.cbLeisure)
        val cbFamily = findViewById<CheckBox>(R.id.cbFamily)
        val cbAdventure = findViewById<CheckBox>(R.id.cbAdventure)

        country.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && country.text.isNotBlank()) {
                fetchWeather(country.text.toString())
            }
        }

        startDate.setOnClickListener { showDatePicker(startDate) }
        endDate.setOnClickListener { showDatePicker(endDate) }

        val tripIndex = intent.getIntExtra("tripIndex", -1)
        if (tripIndex != -1) {
            val trip = TripRepository.trips[tripIndex]
            tripName.setText(trip.tripName)
            country.setText(trip.country)
            startDate.setText(trip.startDate)
            endDate.setText(trip.endDate)
            notes.setText(trip.notes)
            weatherCondition = trip.weatherCondition
            weatherText.text = "Weather condition: $weatherCondition"

            cbBusiness.isChecked = "Business" in trip.tripTypes
            cbLeisure.isChecked = "Leisure" in trip.tripTypes
            cbFamily.isChecked = "Family" in trip.tripTypes
            cbAdventure.isChecked = "Adventure" in trip.tripTypes
        }

        createButton.setOnClickListener {
            if (tripName.text.isBlank() || country.text.isBlank() ||
                startDate.text.isBlank() || endDate.text.isBlank()
            ) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedTypes = mutableListOf<String>()
            if (cbBusiness.isChecked) selectedTypes.add("Business")
            if (cbLeisure.isChecked) selectedTypes.add("Leisure")
            if (cbFamily.isChecked) selectedTypes.add("Family")
            if (cbAdventure.isChecked) selectedTypes.add("Adventure")

            val trip = Trip(
                tripName = tripName.text.toString(),
                country = country.text.toString(),
                startDate = startDate.text.toString(),
                endDate = endDate.text.toString(),
                notes = notes.text.toString(),
                tripTypes = selectedTypes,
                weatherCondition = weatherCondition
            )

            if (tripIndex != -1) {
                TripRepository.trips[tripIndex] = trip
            } else {
                TripRepository.trips.add(trip)
                if (TripRepository.trips.size >= 5) {
                    showAchievementNotification("🏅 Achievement Unlocked!", "Congratulations! You are now a Master Explorer! 🌍")
                } else if (TripRepository.trips.size == 1) {
                    showAchievementNotification("🏅 Achievement Unlocked!", "Baby explorer 🌍🍼")
                }
            }

            setResult(RESULT_OK)
            startActivity(Intent(this, MainActivity::class.java).apply {
                putExtra("navigateTo", "home")
            })
            finish()
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener { finish() }

        createNotificationChannel()
        askNotificationPermission()
    }

    private fun showDatePicker(editText: EditText) { // (Coding with Dev, 2023)
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                editText.setText(String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
    // (Developer, 2024)

    private fun fetchWeather(city: String) {
        val apiKey = getString(R.string.openweather_api_key)
        if (apiKey.isBlank()) {
            weatherText.text = "Missing API key."
            weatherCondition = "Unknown"
            return
        }

        val encodedCity = URLEncoder.encode(city, "UTF-8")
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$encodedCity&appid=$apiKey&units=metric"

        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    weatherText.text = "Failed: ${e.message}"
                    weatherCondition = "Unknown"
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                if (!response.isSuccessful || body.isNullOrEmpty()) {
                    runOnUiThread {
                        weatherText.text = "Error: ${response.code}"
                    }
                    return
                }

                try {
                    val json = JSONObject(body)
                    val condition = json.getJSONArray("weather").getJSONObject(0).optString("main", "Unknown")
                    weatherCondition = condition
                    runOnUiThread { weatherText.text = "Weather: $condition" }
                } catch (e: Exception) {
                    runOnUiThread { weatherText.text = "Parse error." }
                }
            }
        })
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "trip_rewards",
                "Trip Rewards",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for achievements"
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                        PackageManager.PERMISSION_GRANTED -> {
              
                }

                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    AlertDialog.Builder(this)
                        .setTitle("Enable Notifications")
                        .setMessage("We need permission to notify you about trip achievements.")
                        .setPositiveButton("Allow") { _, _ ->
                            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                        .setNegativeButton("Cancel", null)
                        .show()
                }

                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private fun showAchievementNotification(title: String, message: String) {
        val builder = NotificationCompat.Builder(this, "trip_rewards")
            .setSmallIcon(R.drawable.packpalboxlogo)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED || Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
        ) {
            notificationManager.notify(Random().nextInt(), builder.build())
        } else {
            askNotificationPermission()
        }
    }
}
