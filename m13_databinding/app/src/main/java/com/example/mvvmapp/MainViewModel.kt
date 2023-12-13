package com.example.mvvmapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Success)
    val state = _state.asStateFlow()
    private val _result = MutableStateFlow("The result of the query will be displayed here")
    val result = _result.asStateFlow()
    private var requestText = ""
    private var progressState: Boolean = false
    private var _errorMsg: MutableStateFlow<String> = MutableStateFlow("")
    val errorMsg = _errorMsg.asStateFlow()


    fun checkSearchRequest(text: String) {
        viewModelScope.launch {
            if (text.length < 3) {
                _state.value = State.Error
                _errorMsg.value = "Min length 3!"
            } else {
                _errorMsg.value = ""
                requestText = text
                _state.value = State.Success
            }

        }
    }

    fun getProgressBurState() = progressState

    fun getResultRequest() {
        if (requestText.isNotEmpty()) {
            progressState = true
            viewModelScope.launch {
                _state.value = State.Loading
                val request = mainRepository.getData(requestText)
                _result.value = request
                progressState = false
                _state.value = State.Success
            }
        }
    }


}