package com.example.hw3

import android.content.Intent
import android.os.Bundle
import android.graphics.Color
import android.view.WindowManager
import androidx.activity.ComponentActivity
import android.widget.Button
import android.widget.TextView

class ActivityB : ComponentActivity() {
    private var backgroundColor: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE)

        setContentView(R.layout.activity_b)

        backgroundColor = intent.getStringExtra("BACKGROUND_COLOR")

        applyBackgroundColor()

        val buttonOpenC = findViewById<Button>(R.id.open_c)
        val textColor = findViewById<TextView>(R.id.color)

        if (backgroundColor != null && backgroundColor != "Enter color") {
            textColor.text = "Background color: $backgroundColor"
        } else {
            textColor.text = "Background color unset"
        }

        buttonOpenC.setOnClickListener {
            val intent = Intent(this, ActivityC::class.java)
            intent.putExtra("BACKGROUND_COLOR", backgroundColor)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("BACKGROUND_COLOR", backgroundColor)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        backgroundColor = savedInstanceState.getString("BACKGROUND_COLOR")
        applyBackgroundColor()
    }

    private fun applyBackgroundColor() {
        backgroundColor?.let { colorHex ->
            try {
                val color = Color.parseColor(colorHex)
                window.decorView.rootView.setBackgroundColor(color)
            } catch (_: IllegalArgumentException) {

            }
        }
    }
}