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
    private val _result = Channel<String>()
    val result = _result.receiveAsFlow()
    private var requestText: String = ""
    fun checkSearchRequest(text: String) {
        viewModelScope.launch {
            if (text.length < 3) {
                _state.value = State.Error
            } else {
                requestText = text
                _state.value = State.Success
            }

        }
    }

    fun getResultRequest() {
        if (requestText.isNotEmpty()) {
            viewModelScope.launch {
                _state.value = State.Loading
                val request = mainRepository.getData(requestText)
                _state.value = State.Success
                _result.send(request)
            }
        }
    }

}