package com.example.roomapp

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomapp.room.AppDataBase
import com.example.roomapp.room.Word
import com.example.roomapp.room.WordRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    context: Context
) : ViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Created)
    val state = _state.asStateFlow()
    private var currentWord: String = ""

    private val repository: WordRepo

    init {
        val wordsDao = AppDataBase.getDataBase(context).wordsDao()
        repository = WordRepo(wordsDao)
    }

    fun checkEnteredWord(word: String) {
        try {
            if (wordIsValid(word)) {
                _state.value = State.Error("Word contains invalid characters")
            } else {
                _state.value = State.Error(null)
                currentWord = word
                val result = repository.getAllWords()
                _state.value = State.Success(result.toString())
            }
        } catch (e: Exception) {
            _state.value = State.Error(e.message.toString())
        }
    }

    fun clearList(){
        try {
            viewModelScope.launch {
                repository.clearWordsList()
            }
        } catch (e: Exception){
            Log.e("error", e.message.toString())
        }
    }

    fun addWord() {
        viewModelScope.launch {
            if (wordIsValid(currentWord)) {
                _state.value = State.Loading
                repository.addWord(Word(currentWord, 1))
            }
        }
    }

    private fun wordIsValid(word: String): Boolean {
        val regex = Regex("\\W+")
        return !(word.isEmpty() && !regex.matches(word))
    }
}