package com.example.mvvmapp

import kotlinx.coroutines.delay

class MainRepository {

    suspend fun getData(text: String): String {
        delay(2_000)
        return "Nothing was found for the query: $text"
    }
}