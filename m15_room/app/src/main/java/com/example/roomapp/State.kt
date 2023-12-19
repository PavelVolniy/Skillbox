package com.example.roomapp

sealed class State {
    object Created : State()
    object Loading : State()
    data class Success(val wordsList: String?) : State()
    data class Error(val message: String?) : State()

    fun toData(): String? {
        when (this) {
            is Success -> return this.wordsList
            is Error -> return this.message
            is Created -> return ""
            is Loading -> return ""
        }
    }
}
