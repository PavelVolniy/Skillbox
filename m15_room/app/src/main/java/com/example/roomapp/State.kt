package com.example.roomapp

sealed class State {
    object Loading : State()
    object Success : State()
    class Error(val errorMessage: String) : State()

}
