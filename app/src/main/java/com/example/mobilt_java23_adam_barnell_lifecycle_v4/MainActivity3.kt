package com.example.mobilt_java23_adam_barnell_lifecycle_v4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity3 : AppCompatActivity() {

    private lateinit var registerUsernameEditText: EditText
    private lateinit var registerPasswordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)

        registerUsernameEditText = findViewById(R.id.registerUsernameEditText)
        registerPasswordEditText = findViewById(R.id.registerPasswordEditText)
        val registerButton = findViewById<Button>(R.id.button2)

        loadSavedData()
        //Saves the data and clears the fields when it was successful
        registerButton.setOnClickListener {
            val username = registerUsernameEditText.text.toString()
            val password = registerPasswordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                saveData(username, password)

                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                Log.d("adam", "Register successful: Username - $username, Password - $password")

                registerPasswordEditText.text.clear()
                registerUsernameEditText.text.clear()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Enter the correct info", Toast.LENGTH_SHORT).show()
                Log.d("adam", "Register unsuccessful")
            }
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
        loadSavedData()
    }

    private fun saveData(username: String, password: String) {
        val sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }

    private fun loadSavedData() {
        val sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE)
        registerUsernameEditText.setText(sharedPreferences.getString("username", ""))
        registerPasswordEditText.setText(sharedPreferences.getString("password", ""))
    }
}
