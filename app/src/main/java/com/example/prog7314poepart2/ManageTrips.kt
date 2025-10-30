package com.example.prog7314poepart2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ManageTrips : AppCompatActivity() {

    private lateinit var adapter: TripAdapter

    private val editTripLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            adapter.notifyDataSetChanged()
            Log.d("ManageTrips", "Edit trip result OK, updated adapter")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_trips)

        val listView = findViewById<ListView>(R.id.listViewTrips)
        adapter = TripAdapter()
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            if (position >= 0 && position < TripRepository.trips.size) {
                val trip = TripRepository.trips[position]

                AlertDialog.Builder(this)
                    .setTitle("Delete Trip")
                    .setMessage("Do you want to delete ${trip.tripName}?")
                    .setPositiveButton("Yes") { _, _ ->
                        TripRepository.trips.removeAt(position)
                        adapter.notifyDataSetChanged()
                        Toast.makeText(this, "Trip deleted", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else {
                Toast.makeText(this, "Invalid trip selected", Toast.LENGTH_SHORT).show()
            }
        }


        val backButton = findViewById<Button>(R.id.btnBack)
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun editTrip(position: Int) {
        Log.d("ManageTrips", "Launching CreateTrips with tripIndex: $position")
        val intent = Intent(this, CreateTrips::class.java).apply {
            putExtra("tripIndex", position)
        }
        editTripLauncher.launch(intent)
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
        Log.d("ManageTrips", "Resumed, trips size: ${TripRepository.trips.size}")
    }

    inner class TripAdapter : ArrayAdapter<Trip>(this, R.layout.trip_item, TripRepository.trips) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.trip_item, parent, false)
            val trip = getItem(position)!!

            val tripNameText = view.findViewById<TextView>(R.id.tripNameText)
            val weatherIcon = view.findViewById<ImageView>(R.id.weatherIcon)
            val weatherText = view.findViewById<TextView>(R.id.weatherText)

            tripNameText.text = "${trip.tripName} (${trip.country})"
            weatherText.text = trip.weatherCondition

            // weather icons
            val iconResId = when (trip.weatherCondition.lowercase()) {
                "clear" -> R.drawable.ic_sunny
                "clouds" -> R.drawable.ic_cloudy
                "rain" -> R.drawable.ic_rainy
                "snow" -> R.drawable.ic_snowy
                "thunderstorm" -> R.drawable.ic_thunderstorm
                else -> R.drawable.ic_unknown
            }
            Log.d("TripAdapter", "Setting icon for trip: ${trip.tripName}, condition: ${trip.weatherCondition}, resource: $iconResId")
            weatherIcon.setImageResource(iconResId)

            return view
        }
    }
}
