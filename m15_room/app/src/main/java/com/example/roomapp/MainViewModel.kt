package com.example.roomapp

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomapp.room.AppDataBase
import com.example.roomapp.room.Word
import com.example.roomapp.room.WordRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    context: Context
) : ViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Success)
    val state = _state.asStateFlow()
    private var currentWord: String = ""
    private val repository: WordRepo
    var listWords : Flow<List<Word>>


    init {
        val wordsDao = AppDataBase.getDataBase(context).wordsDao()
        repository = WordRepo(wordsDao)
        listWords = repository.getAllWords()
    }

    fun checkEnteredWord(word: String) {
        try {
            currentWord = word
            if (!wordIsValid(currentWord)) {
                _state.value = State.Error("Invalid symbol!")
            }else _state.value = State.Success
        } catch (e: Exception) {
            Log.e("errorFromDatabase", e.message.toString())
        }
    }

    fun clearList() {
        try {
            viewModelScope.launch {
                repository.clearWordsList()
            }
        } catch (e: Exception) {
            Log.e("error", e.message.toString())
        }
    }

    suspend fun addWord() {
        if (wordIsValid(currentWord)) {
            _state.value = State.Loading
            val newWord = Word(currentWord, 1)
            val wordFromDataBase = repository.getWord(newWord)
            if (wordFromDataBase==null){
                repository.addWord(newWord)
            } else {
                newWord.count = wordFromDataBase.count+1
                repository.addWord(newWord)
            }
            _state.value = State.Success
        }
    }

    private fun wordIsValid(word: String): Boolean {
        val symbols = Regex("(\\W+)")
        val numbers = Regex("\\d+")
        if (symbols.containsMatchIn(word)) return false
        if (numbers.containsMatchIn(word)) return false
        return true
    }
}