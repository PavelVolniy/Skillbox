package com.example.homeworck

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homeworck.databinding.MainActivityBinding

private const val FILE_NAME = "fileName"

class MainActivity : AppCompatActivity() {
    private val binding by lazy { MainActivityBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val repository = Repository(this)

        setText(repository.getText())

        binding.saveButton.setOnClickListener {
            val text = binding.editText.text.toString()
            repository.saveText(text)
            setText(repository.getText())
        }

        binding.clearButton.setOnClickListener {
            deleteFile(FILE_NAME)
            repository.clearText()
            setText(repository.getText())
        }
    }

    private fun setText(text: String) {
        binding.textView.text = text
        binding.editText.setText("")
    }
}