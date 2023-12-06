package com.example.skillbox_hw_quiz.ui.main

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.databinding.StartFragmentBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar

class StartFragment : Fragment() {
    private var _binding: StartFragmentBinding? = null
    private val binding get() = _binding!!
    private val calendar by lazy { Calendar.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.my_transition)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StartFragmentBinding.inflate(inflater)
        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.fromStartPageToQuiz)
        }

        binding.getDateButton.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(resources.getString(R.string.title_date_picker))
                .build()

            datePicker.addOnPositiveButtonClickListener { timeInMillisSeconds ->
                calendar.timeInMillis = timeInMillisSeconds

                showSelectedDate(calendar)
            }
            datePicker.show(parentFragmentManager, "DatePicker")
        }

        parentFragmentManager.popBackStack()
        return binding.root
    }

    private fun showSelectedDate(calendar: Calendar?) {
        val date = SimpleDateFormat("dd:MM:yy").format(calendar?.time)
        Snackbar.make(
            binding.root,
            "Выбранная дата $date",
            Snackbar.LENGTH_SHORT
            ).show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}