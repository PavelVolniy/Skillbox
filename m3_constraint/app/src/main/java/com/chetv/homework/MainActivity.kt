package com.chetv.homework

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.chetv.homework.databinding.ActivityMainBinding


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playButton.setOnClickListener {
            showToastMessage("Play button is clicked")
        }

        binding.nextButton.setOnClickListener {
            showToastMessage("Next button is clicked")
        }

        binding.previousButton.setOnClickListener {
            showToastMessage("Previous button is clicked")
        }

    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
