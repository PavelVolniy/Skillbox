package com.example.countdowntimer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.countdowntimer.databinding.MainFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TIMER_VALUE = "timerValue"
private const val BUTTON_CONDITION = "condition"

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val job = Job()
    private val scope by lazy { CoroutineScope(job + Dispatchers.Main) }
    private val timeText by lazy { binding.countTime }
    private val timeProgress by lazy { binding.circularProgress }
    private val timeSlider by lazy { binding.countTimeSlider }
    private val button by lazy { binding.startButton }
    private var timer: Int = 5
    private var buttonCondition: Boolean = false
    private var buttonTextStart: String? = null
    private var buttonTextStop: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buttonTextStart = resources.getString(R.string.start_button)
        buttonTextStop = resources.getString(R.string.stop_button)
        if (savedInstanceState != null) {
            timer = savedInstanceState.getInt(TIMER_VALUE)
            buttonCondition = savedInstanceState.getBoolean(BUTTON_CONDITION)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(layoutInflater)

        setValues(timer)
        timeSlider.value = timer.toFloat()
        timeSlider.isEnabled = true
        if (buttonCondition) button.text = buttonTextStop
        else button.text = buttonTextStart

        if (buttonCondition) scope.launch { startTimer() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timeSlider.addOnChangeListener { _, value, _ ->
            setValues(timer)
            timer = value.toInt()
        }

        button.setOnClickListener {
            buttonCondition = !buttonCondition
            if (buttonCondition) {
                scope.launch {
                    timeSlider.isEnabled = !timeSlider.isEnabled
                    button.text = buttonTextStop
                    startTimer()
                }
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(TIMER_VALUE, timer)
        outState.putBoolean(BUTTON_CONDITION, buttonCondition)
        super.onSaveInstanceState(outState)
    }

    private suspend fun startTimer() {
        timeSlider.isEnabled = false
        while (timer > 0 && buttonCondition) {
            setValues(timer--)
            timeSlider.value = timer.toFloat()
            delay(1000)
        }
        buttonCondition = false
        timeSlider.isEnabled = true
        button.text = buttonTextStart
    }

    private fun setValues(value: Int) {
        timeText.text = value.toString()
        timeProgress.progress = value
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}