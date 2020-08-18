package com.heaps.android.pinview.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.heaps.android.pinview.PinView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pinView = findViewById<PinView>(
            R.id.pin_view
        )
        pinView.listener = { pinCode ->
            Toast.makeText(this, pinCode, Toast.LENGTH_SHORT).show()
            pinView.resetFields()
        }
    }
}
