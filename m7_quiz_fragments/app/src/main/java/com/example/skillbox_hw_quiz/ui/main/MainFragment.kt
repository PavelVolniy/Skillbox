package com.example.skillbox_hw_quiz.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.SurveyGroup
import com.example.skillbox_hw_quiz.databinding.MainFragmentBinding
import com.example.skillbox_hw_quiz.quiz.QuizStorage
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val listViews by lazy { mutableListOf<SurveyGroup>() }
    private val listIdCheckedButton by lazy { mutableListOf<Int>() }


    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater)
        setData()

        binding.sendButton.setOnClickListener {
            if (checkAnswers()) {
                val bundle = Bundle()
                bundle.putIntArray("listId", listIdCheckedButton.toIntArray())
                findNavController().navigate(R.id.fromQuizToResult)
            } else {
                Snackbar.make(binding.root, "Пожалуйста выберите ответы", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }


        return binding.root
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}