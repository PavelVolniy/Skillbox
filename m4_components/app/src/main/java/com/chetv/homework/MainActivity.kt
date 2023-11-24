package com.chetv.homework

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.chetv.homework.databinding.ActivityMainBinding
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val progress by lazy { binding.progressLine }
    private val progressNum by lazy { binding.tvBallsNum }
    private val notification by lazy { binding.smNotification }
    private val firstCheckbox by lazy { binding.firstCheckBox }
    private val secondCheckbox by lazy { binding.secondCheckBox }
    private val nameField by lazy { binding.etName }
    private val numberField by lazy { binding.etNumber }
    private val saveButton by lazy { binding.btSaveButton }
    private val radioGroupSex by lazy { binding.rgSex }
    private var isCheckedSex = false
    private var isCheckedNotification = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        saveButton.isEnabled = false

        nameField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                activateSaveButton()
            }
        })
        numberField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                activateSaveButton()
            }
        })

        radioGroupSex.setOnCheckedChangeListener { _, numRadioButton ->
            activateSaveButton()
            isCheckedSex = numRadioButton > 0
        }


        setBallsValue(progress, progressNum)

        notification.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                firstCheckbox.isEnabled = true
                firstCheckbox.isChecked = true
                secondCheckbox.isEnabled = true
                secondCheckbox.isChecked = true
                activateSaveButton()
            } else {
                firstCheckbox.isEnabled = false
                firstCheckbox.isChecked = false
                secondCheckbox.isEnabled = false
                secondCheckbox.isChecked = false
                activateSaveButton()
            }
        }

        firstCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                isCheckedNotification = true
                activateSaveButton()
            } else activateSaveButton()
        }

        secondCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                isCheckedNotification = true
                activateSaveButton()
            } else activateSaveButton()
        }

        saveButton.setOnClickListener {
            Snackbar.make(binding.root, "Изминения сохранены", Snackbar.LENGTH_SHORT).show()
        }


    }

    private fun activateSaveButton() {
        saveButton.isEnabled = checkedFields()
    }

    private fun checkedFields(): Boolean {
        val firstGroupFieldsIsCorrect = !nameField.text.isNullOrBlank() &&
                nameField.text?.length!! < 40 &&
                !numberField.text.isNullOrBlank() &&
                isCheckedSex
        return if (firstGroupFieldsIsCorrect && !notification.isChecked
        ) true
        else firstGroupFieldsIsCorrect && (firstCheckbox.isChecked || secondCheckbox.isChecked)
    }

    @SuppressLint("SetTextI18n")
    private fun setBallsValue(
        progress: LinearProgressIndicator,
        progressNum: TextView
    ) {
        val progressValue = (0..100).random()
        progress.progress = progressValue
        progressNum.text = "$progressValue/100"
    }
}