package com.example.hw3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.widget.EditText
import android.widget.Button
import android.graphics.Color
import android.view.WindowManager
import android.widget.Toast
import java.util.Random

class ActivityA : ComponentActivity() {
    private var backgroundColor: String? = null
    private var lastInput: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.activity_a)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setShowWhenLocked(true)
        setTurnScreenOn(true)

        val editText = findViewById<EditText>(R.id.enter_color)
        val buttonGenColor = findViewById<Button>(R.id.gen_color)
        val buttonOpenB = findViewById<Button>(R.id.open_b)

        lastInput = savedInstanceState?.getString("LAST_INPUT")
        if (lastInput != null) {
            editText.setText(lastInput)
        }

        buttonGenColor.setOnClickListener {
            val random = Random()
            val color = Color.argb(
                255,
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
            )

            val hexColor = String.format("#%06X", 0xFFFFFF and color)
            editText.setText(hexColor)
        }

        buttonOpenB.setOnClickListener {
            backgroundColor = editText.text.toString().trim()

            if (backgroundColor?.isEmpty() == true || backgroundColor == "Enter color") {
                backgroundColor = null
            } else if (!validateColor(backgroundColor)) {
                showColorError()
                return@setOnClickListener
            }

            val intent = Intent(this, ActivityB::class.java)
            intent.putExtra("BACKGROUND_COLOR", backgroundColor)
            startActivity(intent)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        lastInput = savedInstanceState.getString("LAST_INPUT")
        val editText = findViewById<EditText>(R.id.enter_color)
        editText.setText(lastInput)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val editText = findViewById<EditText>(R.id.enter_color)

        outState.putString("BACKGROUND_COLOR", backgroundColor)

        lastInput = editText.text.toString()
        outState.putString("LAST_INPUT", lastInput)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    fun validateColor(hexColor: String?): Boolean {
        if (hexColor.isNullOrEmpty()) {
            return false
        }

        val hexColorPattern = Regex("^#[A-Fa-f0-9]{6}$")

        return hexColorPattern.matches(hexColor)
    }

    private fun showColorError() {
        Toast.makeText(
            this,
            "Incorrect color! Use format #RRGGBB",
            Toast.LENGTH_LONG
        ).show()
    }
}