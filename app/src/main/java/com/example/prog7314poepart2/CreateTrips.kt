package com.example.prog7314poepart2

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.net.URLEncoder
import java.util.*

class CreateTrips : AppCompatActivity() {

    private lateinit var weatherText: TextView
    private val client = OkHttpClient()
    private var weatherCondition: String = ""

    private val weatherActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            weatherCondition = result.data?.getStringExtra("WEATHER_CONDITION") ?: ""
            weatherText.text = "Weather condition: $weatherCondition"
            Log.d("CreateTrips", "Weather condition from WeatherActivity: $weatherCondition")
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
        weatherText = findViewById<TextView>(R.id.weatherText)

        val cbBusiness = findViewById<CheckBox>(R.id.cbBusiness)
        val cbLeisure = findViewById<CheckBox>(R.id.cbLeisure)
        val cbFamily = findViewById<CheckBox>(R.id.cbFamily)
        val cbAdventure = findViewById<CheckBox>(R.id.cbAdventure)

        country.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && country.text.isNotBlank()) {
                Log.d("CreateTrips", "Country input: ${country.text}")
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
            Log.d("CreateTrips", "Editing trip at index $tripIndex: $trip")

            cbBusiness.isChecked = "Business" in trip.tripTypes
            cbLeisure.isChecked = "Leisure" in trip.tripTypes
            cbFamily.isChecked = "Family" in trip.tripTypes
            cbAdventure.isChecked = "Adventure" in trip.tripTypes
        }

        createButton.setOnClickListener {
            if (tripName.text.isBlank() || country.text.isBlank() || startDate.text.isBlank() || endDate.text.isBlank()) {
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
                Log.d("CreateTrips", "Updated trip at index $tripIndex: $trip")
            } else {
                TripRepository.trips.add(trip) // add new
                Log.d("CreateTrips", "Added new trip: $trip")

                if (TripRepository.trips.size >= 5) {
                    showAchievementNotification()
                }else
                    if (TripRepository.trips.size == 1) {
                        showAchievementNotification2()
                    }
            }

            setResult(RESULT_OK)
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("navigateTo", "home")
            startActivity(intent)
            finish()
        }

        val backButton = findViewById<Button>(R.id.btnBack)
        backButton.setOnClickListener {
            finish()
        }

        createNotificationChannel()
    }

    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val dateStr = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                editText.setText(dateStr)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun fetchWeather(city: String) {
        val apiKey = getString(R.string.openweather_api_key)
        if (apiKey.isBlank() || apiKey == "YOUR_ACTUAL_API_KEY_HERE") {
            Log.e("CreateTrips", "API key missing or invalid")
            runOnUiThread {
                weatherText.text = "OpenWeather API key missing."
                weatherCondition = "Unknown"
                Toast.makeText(this, "Add API key to strings.xml", Toast.LENGTH_LONG).show()
            }
            return
        }

        val encodedCity = URLEncoder.encode(city, "UTF-8")
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$encodedCity&appid=$apiKey&units=metric"
        Log.d("CreateTrips", "Fetching weather for URL: $url")

        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("CreateTrips", "Weather fetch failed: ${e.message}")
                runOnUiThread {
                    weatherText.text = "Failed to fetch weather: ${e.message}"
                    weatherCondition = "Unknown"
                    Toast.makeText(this@CreateTrips, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val code = response.code
                val bodyStr = response.body?.string()
                Log.d("CreateTrips", "Response code: $code, body: $bodyStr")

                if (!response.isSuccessful || bodyStr.isNullOrBlank()) {
                    runOnUiThread {
                        weatherText.text = if (code == 404) "Location not found." else "Failed to fetch weather (code $code)."
                        weatherCondition = "Unknown"
                        Toast.makeText(this@CreateTrips, if (code == 404) "Location not found: $city" else "Weather fetch error (code $code)", Toast.LENGTH_SHORT).show()
                    }
                    return
                }

                try {
                    val json = JSONObject(bodyStr)
                    val weatherArray = json.getJSONArray("weather")
                    val weatherObj = weatherArray.getJSONObject(0)
                    weatherCondition = weatherObj.optString("main", "Unknown")
                    Log.d("CreateTrips", "Weather condition fetched: $weatherCondition")

                    runOnUiThread {
                        weatherText.text = "Weather condition: $weatherCondition"
                        Toast.makeText(this@CreateTrips, "Weather fetched: $weatherCondition", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("CreateTrips", "Error parsing weather data: ${e.message}")
                    runOnUiThread {
                        weatherText.text = "Error parsing weather data."
                        weatherCondition = "Unknown"
                        Toast.makeText(this@CreateTrips, "Error parsing weather: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
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
                description = "Notifications for achievements like Master Explorer"
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun showAchievementNotification() {
        val builder = NotificationCompat.Builder(this, "trip_rewards")
            .setSmallIcon(R.drawable.plane_svgrepo_com)
            .setContentTitle("🏅 Achievement Unlocked!")
            .setContentText("Congratulations! You are now a Master Explorer! 🌍")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(this)

        if (androidx.core.app.ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(1001, builder.build())
        } else {
            androidx.core.app.ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                1001
            )
        }
    }

    private fun showAchievementNotification2() {
        val builder = NotificationCompat.Builder(this, "trip_rewards")
            .setSmallIcon(R.drawable.packpalboxlogo)
            .setContentTitle("🏅 Achievement Unlocked!")
            .setContentText("Baby explorer 🌍🍼")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(this)

        if (androidx.core.app.ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(1002, builder.build())
        } else {
            androidx.core.app.ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                1002
            )
        }
    }


}
