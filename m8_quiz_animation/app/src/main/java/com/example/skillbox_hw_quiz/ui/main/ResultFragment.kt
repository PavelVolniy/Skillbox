package com.example.skillbox_hw_quiz.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.databinding.ResultFragmentBinding

private const val ANSWER_RESULT = "answerResult"


class ResultFragment : Fragment() {
    private var answerResult: String? = null
    private var _binding: ResultFragmentBinding? = null
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
        _binding = ResultFragmentBinding.inflate(inflater)

        binding.startOverButton.setOnClickListener {
            findNavController().navigate(R.id.fromResultPageToStartPage)
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