package com.example.mvvmapp

sealed class State{
    object Loading : State()
    object Success : State()
    object Error : State()
}
