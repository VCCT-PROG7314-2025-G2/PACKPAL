package com.example.prog7314poepart2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ContactUsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)

        val contactInfoText = findViewById<TextView>(R.id.contactInfo)
        val addressText = findViewById<TextView>(R.id.address)
        val emailContactText = findViewById<TextView>(R.id.emailContact)
        val phoneContactText = findViewById<TextView>(R.id.phoneContact)
        val socialLinksText = findViewById<TextView>(R.id.socialLinks)

        contactInfoText.text = "We'd love to hear from you! Whether you've got feedback, questions, or just want to say hi—reach out anytime."
        addressText.text = "Address: 123 Walsh Street, Cape Town, South Africa"
        emailContactText.text = "Email: support@packpals.com"
        phoneContactText.text = "Phone: +27 21 855 1234"
        socialLinksText.text = "Social: Twitter | Instagram | Facebook"
    }
}
// (Android Knowledge, 2023)
