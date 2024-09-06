package com.example.mobilt_java23_adam_barnell_lifecycle_v4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        //Loading the saved data when creating the page again
        loadSavedData()

        loginButton.setOnClickListener {
            val enteredUsername = usernameEditText.text.toString()
            val enteredPassword = passwordEditText.text.toString()

            val sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE)
            val savedUsername = sharedPreferences.getString("username", "")
            val savedPassword = sharedPreferences.getString("password", "")
            //Validates the user credentials
            if (enteredUsername == savedUsername && enteredPassword == savedPassword) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
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

    private fun saveData() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        val sharedPreferences = getSharedPreferences("loginData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("enteredUsername", username)
        editor.putString("enteredPassword", password)
        editor.apply()
    }

    private fun loadSavedData() {
        val sharedPreferences = getSharedPreferences("loginData", Context.MODE_PRIVATE)
        usernameEditText.setText(sharedPreferences.getString("enteredUsername", ""))
        passwordEditText.setText(sharedPreferences.getString("enteredPassword", ""))
    }
}
