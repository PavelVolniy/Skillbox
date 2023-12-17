package com.example.roomapp

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.roomapp.room.AppDataBase
import com.example.roomapp.room.Word
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first

class MainViewModel(
    private val db: AppDataBase
) : ViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state = _state.asStateFlow()
    private var currentWord: String = ""

    fun checkEnteredWord(word: String) {
        try {
            if (wordIsValid(word)) {
                _state.value = State.Error("Word contains invalid characters")
            } else {
                currentWord = word
                val result = db.dictionaryDao().getAll().toString()
                _state.value = State.Success(result)
            }
        } catch (e: Exception){
            _state.value = State.Error(e.message.toString())
        }
    }

    suspend fun addWord() {
        if (wordIsValid(currentWord)) {
            _state.value = State.Loading
            db.dictionaryDao().insert(Word(currentWord, 1))
        }
    }

    private fun wordIsValid(word: String): Boolean {
        val regex = Regex("\\W+")
        return !(word.isEmpty() && regex.matches(word))
    }
}