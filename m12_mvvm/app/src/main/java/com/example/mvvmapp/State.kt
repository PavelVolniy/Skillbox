package com.example.mvvmapp

sealed class State{
    object Loading : State()
    class Success(text: String) : State()
    object Error : State()
}
