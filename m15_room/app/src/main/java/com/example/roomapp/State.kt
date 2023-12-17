package com.example.roomapp

import com.example.roomapp.room.Word

sealed class State{
    object Created: State()
    object Loading: State()
    class Success(val wordsList: String): State()
    class Error(val message: String): State()
}
