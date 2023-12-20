package com.example.roomapp.room

import kotlinx.coroutines.flow.Flow

class WordRepo(private val wordsDao: WordsDao) {

    fun getAllWords(): Flow<List<Word>> {
        return wordsDao.getAll()
    }

    suspend fun addWord(word: Word) {
        wordsDao.insert(word)
    }

    suspend fun getWord(word: Word): Word {
        return wordsDao.getWordByName(word.name)
    }

    suspend fun clearWordsList() = wordsDao.clearAll()
}