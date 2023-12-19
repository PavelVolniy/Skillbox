package com.example.roomapp.room

class WordRepo(private val wordsDao: WordsDao) {

    fun getAllWords() = wordsDao.getAll()

    suspend fun addWord(word: Word) {
        wordsDao.insert(word)
    }

    suspend fun clearWordsList() = wordsDao.clearAll()
}