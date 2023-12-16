package com.example.retrofitapp

import com.example.retrofitapp.json.User
import retrofit2.http.GET

interface RandomUserAPi {
    @GET("/api/")
    suspend fun getUser(): User
}