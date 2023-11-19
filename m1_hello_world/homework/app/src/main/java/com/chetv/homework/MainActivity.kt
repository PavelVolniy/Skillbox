package com.chetv.homework

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.chetv.homework.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var countPassenger = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textPassenger = binding.tvCountPassenger
        val count = binding.tvCount
        val buttonPlus = binding.ibButtonPlus
        val buttonMinus = binding.ibButtonMinus
        val buttonReset = binding.btResetButton




        setChanged(buttonMinus, textPassenger, buttonPlus, buttonReset, count)

        buttonMinus.setOnClickListener {
            if (countPassenger > 0) countPassenger--
            setChanged(buttonMinus, textPassenger, buttonPlus, buttonReset, count)
        }
        buttonPlus.setOnClickListener {
            if (countPassenger < 50) countPassenger++
            setChanged(buttonMinus, textPassenger, buttonPlus, buttonReset, count)
        }
        buttonReset.setOnClickListener {
            countPassenger = 0
            setChanged(buttonMinus, textPassenger, buttonPlus, buttonReset, count)
        }
    }

    private fun setChanged(
        buttonMinus: ImageButton,
        textPassenger: AppCompatTextView,
        buttonPlus: ImageButton,
        buttonReset: TextView,
        count: TextView
    ) {
        buttonReset.visibility = View.INVISIBLE
        count.text = countPassenger.toString()
        if (countPassenger == 0) {
            buttonMinus.isEnabled = false
            textPassenger.text = getText(R.string.allSeatsFree)
            textPassenger.setTextColor(getColor(R.color.green))
        } else if (countPassenger < 50) {
            textPassenger.text = "Осталось мест: ${50 - countPassenger}"
            textPassenger.setTextColor(getColor(R.color.blue))
            buttonMinus.isEnabled = true
            buttonPlus.isEnabled = true
        } else {
            textPassenger.setTextColor(getColor(R.color.red))
            textPassenger.text = getText(R.string.allSeatsFull)
            buttonPlus.isEnabled = false
            buttonReset.visibility = View.VISIBLE
        }
    }


}