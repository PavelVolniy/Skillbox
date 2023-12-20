package com.example.roomapp

sealed class State {
    object Loading : State()
    data class Success(val listWords: String) : State()
    class Error(val errorMessage: String) : State()
    object Wait : State()

}
