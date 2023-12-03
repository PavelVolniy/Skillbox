package com.example.skillbox_hw_quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.databinding.FragmentResultBinding

private const val ANSWER_RESULT = "answerResult"


class FragmentResult : Fragment() {
    private var answerResult: String? = null
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            answerResult = it.getString(ANSWER_RESULT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater)

        binding.startOverButton.setOnClickListener {
            findNavController().navigate(R.id.from_resultPage_to_startPage)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (answerResult != null) binding.resultQuizText.text = answerResult
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}