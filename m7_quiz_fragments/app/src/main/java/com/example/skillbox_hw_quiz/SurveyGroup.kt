package com.example.skillbox_hw_quiz

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RadioButton
import com.example.skillbox_hw_quiz.databinding.SurveyGroupBinding

class SurveyGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val binding: SurveyGroupBinding
    private val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SurveyGroup, 0, 0)

    init {
        val view = inflate(context, R.layout.survey_group, this)
        binding = SurveyGroupBinding.bind(view)
    }

    fun setQuestion(question: String) {
        if (question.isNotEmpty()) binding.question.text = question
        else throw IllegalArgumentException("Question is $question")
    }

    fun addAnswer(answer: String, id: Int) {
        if (answer.isNotEmpty()) {
            val checkBox = RadioButton(context)
            checkBox.text = answer
            checkBox.id = id
            binding.answerGroup.addView(checkBox)
        }
    }

    fun getAnswer(): Int {
        return binding.answerGroup.checkedRadioButtonId
    }
}