package com.example.retrofitapp

sealed class State{
    object Loading: State()
    object Success: State()

    object Created: State()
}
