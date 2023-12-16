package com.example.retrofitapp

import com.example.retrofitapp.json.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://randomuser.me/"

object RetrofitRepo {
    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val randomUserAPi = retrofit.create(RandomUserAPi::class.java)

    suspend fun getData(): User {
        return randomUserAPi.getUser()
    }
}