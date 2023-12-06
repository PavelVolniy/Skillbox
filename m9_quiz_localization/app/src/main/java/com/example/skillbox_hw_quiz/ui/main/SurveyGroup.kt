package com.example.skillbox_hw_quiz.ui.main

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RadioButton
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.databinding.SurveyGroupBinding

class SurveyGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val binding: SurveyGroupBinding
    private val listRadioButtons : MutableList<RadioButton>

    init {
        val view = inflate(context, R.layout.survey_group, this)
        binding = SurveyGroupBinding.bind(view)
        listRadioButtons = mutableListOf()
    }

    fun setQuestion(question: String) {
        if (question.isNotEmpty()) binding.question.text = question
        else throw IllegalArgumentException("Question is $question")
    }

    fun addAnswer(answer: String, id: Int) {
        if (answer.isNotEmpty()) {
            val radioButton = RadioButton(context)
            radioButton.text = answer
            radioButton.id = id
            binding.answerGroup.addView(radioButton)
            listRadioButtons.add(radioButton)
        }
    }

    fun getListButtons() = listRadioButtons

    fun getAnswer(): Int {
        return binding.answerGroup.checkedRadioButtonId
    }
}