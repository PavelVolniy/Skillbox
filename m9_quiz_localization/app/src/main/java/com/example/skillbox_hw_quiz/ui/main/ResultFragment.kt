package com.example.skillbox_hw_quiz.ui.main

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.widget.Button
import android.widget.TextView
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
    ): View {
        _binding = ResultFragmentBinding.inflate(inflater)
        val startOverButton = binding.startOverButton
        startAnimation(startOverButton, binding.resultQuizText)

        startOverButton.setOnClickListener {
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

    private fun startAnimation(buttonView: Button, textView: TextView) {
        val rotation = PropertyValuesHolder.ofFloat(View.ROTATION, -10f, 10f)
        val translation = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, -30f, 30f)
        val textColor = PropertyValuesHolder.ofInt(
            "textColor",
            Color.parseColor("#FF018786"),
            Color.parseColor("#657985")
        ).apply {
            setEvaluator(ArgbEvaluator())
        }
        ObjectAnimator.ofPropertyValuesHolder(textView, textColor).apply {
            duration = 1000
            interpolator = AccelerateInterpolator()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            start()
        }

        ObjectAnimator.ofPropertyValuesHolder(buttonView, rotation, translation).apply {
            duration = 1000
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            start()
        }
    }

}