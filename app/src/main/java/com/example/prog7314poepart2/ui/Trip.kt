package com.example.prog7314poepart2

import java.text.SimpleDateFormat
import java.util.*

data class Trip(
    val tripName: String,
    val country: String,
    val startDate: String, // YYYY-MM-DD
    val endDate: String,
    val notes: String?,
    val tripTypes: List<String> // NEW: multiple trip type selection
) {
    fun getStartDateAsDate(): Date {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return sdf.parse(startDate)!!
    }

    override fun toString(): String {
        val typeString = if (tripTypes.isNotEmpty()) "Type: ${tripTypes.joinToString(", ")}" else "Type: N/A"
        return if (notes.isNullOrEmpty()) {
            "$tripName - $country ($startDate to $endDate)\n$typeString"
        } else {
            "$tripName - $country ($startDate to $endDate)\n$typeString\nNotes: $notes"
        }
    }
}
