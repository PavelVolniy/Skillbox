package com.example.architectureapp.data.taskapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton


private const val BASE_URL = "https://www.boredapi.com"

@Singleton
class RetrofitRepo @Inject constructor(){
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val taskApi = retrofit.create(TaskApi::class.java)
    fun getRetrofitInstance(): TaskApi = taskApi
}