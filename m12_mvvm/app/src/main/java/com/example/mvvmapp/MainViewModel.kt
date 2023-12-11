package com.example.mvvmapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Error)
    val state = _state.asStateFlow()
    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()
    fun checkSearchRequest(it: String) {
        viewModelScope.launch {
            if (it.length < 3) {
                _state.value = State.Error
            } else {
                _state.value = State.Loading
                val request = mainRepository.getData(it)
                _state.value = State.Success(request)
            }
        }
    }

}