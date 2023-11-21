package com.chetv.homework

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chetv.homework.databinding.MainActivityBinding
import com.chetv.homework.ui.theme.HomeWorkTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val customView = binding.customView

        customView.setFirstMessage("Верхняя строчка")
        customView.setSecondMessage("Нижняя строчка")
        customView.setBackgroundColorFrame(Color.RED)
    }

}