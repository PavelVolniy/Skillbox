package com.example.countdowntimer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.countdowntimer.databinding.MainFragmentBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val timeText by lazy { binding.countTime }
    private val timeProgress by lazy { binding.circularProgress }
    private val timeSlider by lazy { binding.countTimeSlider }
    private val button by lazy { binding.startButton }
    private var timer: Int = 10
    private var buttonCondition: Boolean = false
    private var buttonTextOnStart: String? = null
    private var buttonTextOnStop: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buttonTextOnStart = resources.getString(R.string.start_button)
        buttonTextOnStop = resources.getString(R.string.stop_button)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(layoutInflater)


        timeSlider.isEnabled = true

        timeSlider.addOnChangeListener { slider, value, fromUser ->
            setValue(timer)
            timer = value.toInt()
        }

        button.setOnClickListener {
            timeSlider.isEnabled = !timeSlider.isEnabled
            Toast.makeText(context, "in developer", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun startTimer() {
        buttonCondition = true
        timeSlider.isEnabled = false
        while (timer > 0) {
            setValue(timer--)
            timeSlider.value = timer.toFloat()
        }
    }

    private fun setValue(value: Int) {
        timeText.text = value.toString()
        timeProgress.progress = value
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}