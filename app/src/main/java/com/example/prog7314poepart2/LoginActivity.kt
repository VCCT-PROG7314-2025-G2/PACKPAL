package com.example.prog7314poepart2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private val enforceVerification = false // optional email verification toggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        val emailField = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val passwordField = findViewById<EditText>(R.id.editTextTextPassword)
        val loginButton = findViewById<Button>(R.id.btnlogin)
        val signupButton = findViewById<Button>(R.id.btnsignup)
        val biometricButton = findViewById<Button>(R.id.btnBiometric)
        val googleButton = findViewById<Button>(R.id.btnGoogleLogin)

        auth = FirebaseAuth.getInstance()

        // 🔹 Auto-login if user already authenticated
        val currentUser = auth.currentUser
        val lastSignedIn = GoogleSignIn.getLastSignedInAccount(this)
        if (currentUser != null || lastSignedIn != null) {
            Log.d("AutoLogin", "User already signed in, redirecting to MainActivity")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        // 🔹 Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // 🔹 Handle Google Sign-In result
        val googleSignInLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    if (account != null) {
                        Log.d("GoogleSignIn", "firebaseAuthWithGoogle: ${account.email}")
                        firebaseAuthWithGoogle(account)
                    }
                } catch (e: ApiException) {
                    Log.e("GoogleSignIn", "Google sign in failed", e)
                    Toast.makeText(
                        this,
                        "Google sign-in failed: ${e.statusCode}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Google sign-in cancelled", Toast.LENGTH_SHORT).show()
            }
        }

        // 🔹 Google sign-in button click
        googleButton.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener {
                val signInIntent = googleSignInClient.signInIntent
                googleSignInLauncher.launch(signInIntent)
            }
        }

        // 🔹 Manual Email/Password Login (fixed)
        loginButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null) {
                            if (user.isEmailVerified || !enforceVerification) {
                                Toast.makeText(
                                    this,
                                    "Welcome back, ${user.email}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Please verify your email before logging in.",
                                    Toast.LENGTH_LONG
                                ).show()
                                auth.signOut()
                            }
                        }
                    } else {
                        val error = task.exception?.message ?: "Unknown error"
                        Log.e("EmailLogin", "Login failed: $error")
                        Toast.makeText(this, "Login failed: $error", Toast.LENGTH_LONG).show()
                    }
                }
        }

        // 🔹 Go to signup
        signupButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        // 🔹 Biometric setup
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(applicationContext, "Biometric login successful!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(applicationContext, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Login")
            .setSubtitle("Use your fingerprint to login")
            .setNegativeButtonText("Cancel")
            .build()

        val biometricManager = BiometricManager.from(this)
        if (biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
            biometricButton.isEnabled = true
        }

        biometricButton.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    // 🔹 Authenticate Google account with Firebase
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val idToken = account.idToken
        if (idToken == null) {
            Toast.makeText(this, "Google Sign-In token missing", Toast.LENGTH_SHORT).show()
            return
        }

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("GoogleSignIn", "signInWithCredential:success")
                    Toast.makeText(this, "Welcome ${auth.currentUser?.email}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Log.e("GoogleSignIn", "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Google Sign-In failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
