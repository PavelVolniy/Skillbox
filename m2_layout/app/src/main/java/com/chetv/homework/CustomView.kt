package com.chetv.homework

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import com.chetv.homework.databinding.CustomViewBinding

class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val binding: CustomViewBinding
    private val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView, 0, 0)
    private val firstRowText = typedArray.getString(R.styleable.CustomView_first_row_text)
    private val secondRowText = typedArray.getString(R.styleable.CustomView_second_row_text)
    private val frameBackgroundColor =
        typedArray.getColor(R.styleable.CustomView_frame_background_color, Color.WHITE)


    init {
        val view = inflate(context, R.layout.custom_view, this)
        binding = CustomViewBinding.bind(view)
        if (!firstRowText.isNullOrEmpty()) setFirstMessage(firstRowText)
        if (!secondRowText.isNullOrEmpty()) setFirstMessage(secondRowText)
        if (frameBackgroundColor != 0) setBackgroundColorFrame(frameBackgroundColor)

        orientation = HORIZONTAL

        typedArray.recycle()
    }


    fun setFirstMessage(message: String) {
        if (message.isNotEmpty()) binding.tvFirstRow.text = message
    }

    fun setSecondMessage(message: String) {
        if (message.isNotEmpty()) binding.tvSecondRow.text = message
    }

    fun setBackgroundColorFrame(color: Int) {
        if (color > 0) binding.frame.rootView.setBackgroundColor(color)
    }
}