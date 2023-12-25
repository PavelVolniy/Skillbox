package com.example.architectureapp.presentation

import androidx.lifecycle.ViewModel
import com.example.architectureapp.domain.GetTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val useCase: GetTaskUseCase
) : ViewModel() {
    private var _state = MutableStateFlow<State>(State.Success(""))
    val state get() = _state.asStateFlow()

    suspend fun updateTask() {
        _state.value = State.Loading
        val task = useCase.getTask()
        _state.value = State.Success(task.activity)
    }
}