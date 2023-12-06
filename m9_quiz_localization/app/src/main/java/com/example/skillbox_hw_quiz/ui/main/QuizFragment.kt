package com.example.skillbox_hw_quiz.ui.main

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.databinding.QuizFragmentBinding
import com.example.skillbox_hw_quiz.quiz.QuizStorage
import com.google.android.material.snackbar.Snackbar

private const val ANSWER_RESULT = "answerResult"

class QuizFragment : Fragment() {
    private var _binding: QuizFragmentBinding? = null
    private val binding get() = _binding!!
    private val listViews by lazy { mutableListOf<SurveyGroup>() }
    private val listIdCheckedButton by lazy { mutableListOf<Int>() }
    private val quiz by lazy { QuizStorage.getQuiz(QuizStorage.Locale.Ru) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.my_transition)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = QuizFragmentBinding.inflate(inflater)
        val buttonBack = binding.backButton
        val buttonSendResult = binding.sendButton



        setData()
        setAnimationSendButton(buttonSendResult)
        setAnimationBackButton(buttonBack)

        listViews.forEach {
            it.getListButtons().forEach {
                it.setOnClickListener {
                    buttonSendResult.visibility = View.VISIBLE
                }
            }
        }

        buttonSendResult.setOnClickListener {
            if (checkAnswers()) {
                val answerResult = QuizStorage.answer(quiz, listIdCheckedButton)
                val bundle = Bundle().apply {
                    putString(ANSWER_RESULT, answerResult)
                }
                findNavController().navigate(R.id.fromQuizToResult, bundle)

            } else {
                Snackbar.make(binding.root, "Пожалуйста выберите ответы", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        buttonBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }

    private fun setAnimationSendButton(buttonSendResult: Button) {
        buttonSendResult.translationX = -350f
        buttonSendResult.animate().apply {
            duration = 3500
            translationX(3f)
            rotation(3f)
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    private fun setAnimationBackButton(buttonBack: Button) {
        buttonBack.translationX = 350f
        buttonBack.animate().apply {
            duration = 1500
            translationX(-3f)
            rotation(-3f)
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    private fun checkAnswers(): Boolean {
        listViews.forEach {
            if (it.getAnswer() == -1) return false
            else listIdCheckedButton.add(it.getAnswer())
        }
        return true
    }

    private fun setData() {
        val quiz = QuizStorage.getQuiz(QuizStorage.Locale.Ru)
        for (i in 0..quiz.questions.lastIndex) {
            val view = context?.let { SurveyGroup(it) }
            view?.setQuestion(quiz.questions[i].question)
            for (j in 0..quiz.questions[i].answers.lastIndex) {
                view?.addAnswer(quiz.questions[i].answers[j], j)
            }
            binding.listSurveyGroup.addView(view)
            view?.let { listViews.add(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}