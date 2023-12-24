package com.example.architectureapp.presentation

import androidx.lifecycle.ViewModel
import com.example.architectureapp.domain.GetTaskUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val useCase: GetTaskUseCase
): ViewModel() {
}