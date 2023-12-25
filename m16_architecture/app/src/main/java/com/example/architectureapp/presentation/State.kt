package com.example.architectureapp.presentation

sealed class State {
    object Loading : State()
    data class Success(val result: String) : State()
}
