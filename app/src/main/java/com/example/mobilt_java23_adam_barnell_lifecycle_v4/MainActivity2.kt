package com.example.mobilt_java23_adam_barnell_lifecycle_v4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var lastnameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var driverLicenseBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        nameEditText = findViewById(R.id.nameEditText)
        lastnameEditText = findViewById(R.id.lastnameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        driverLicenseBox = findViewById(R.id.driverLicenseBox)
        val sendButton = findViewById<Button>(R.id.button)

        loadSavedData()

        sendButton.setOnClickListener {
            val email = emailEditText.text.toString()
            /* Regex on the email so it is an actual email
             and clears the form when clicked enter
             */
            if (isValidEmail(email)) {
                saveData()
                Toast.makeText(this, "Information saved successfully!", Toast.LENGTH_SHORT).show()

                phoneEditText.text.clear()
                nameEditText.text.clear()
                lastnameEditText.text.clear()
                emailEditText.text.clear()
                driverLicenseBox.isChecked = false
            } else {
                Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show()
            }
        }

        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }

        val mainPageButton = findViewById<Button>(R.id.mainPageButton)
        mainPageButton.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        saveData()
    }

    override fun onPause() {
        super.onPause()
        saveData()
    }

    private fun saveData() {
        val email = emailEditText.text.toString()
        val name = nameEditText.text.toString()
        val lastname = lastnameEditText.text.toString()
        val phonenumber = phoneEditText.text.toString()
        val hasDrivingLicense = driverLicenseBox.isChecked

        val sharedPreferences = getSharedPreferences("userdetails", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("name", name)
        editor.putString("lastname", lastname)
        editor.putString("phonenumber", phonenumber)
        editor.putBoolean("hasDrivingLicense", hasDrivingLicense)
        editor.apply()
    }

    private fun loadSavedData() {
        val sharedPreferences = getSharedPreferences("userdetails", Context.MODE_PRIVATE)
        emailEditText.setText(sharedPreferences.getString("email", ""))
        nameEditText.setText(sharedPreferences.getString("name", ""))
        lastnameEditText.setText(sharedPreferences.getString("lastname", ""))
        phoneEditText.setText(sharedPreferences.getString("phonenumber", ""))
        driverLicenseBox.isChecked = sharedPreferences.getBoolean("hasDrivingLicense", false)
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
