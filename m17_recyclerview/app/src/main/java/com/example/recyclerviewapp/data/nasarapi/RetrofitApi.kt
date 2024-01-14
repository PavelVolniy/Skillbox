package com.example.recyclerviewapp.data.nasarapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

private const val BASE_URL = "https://api.nasa.gov"

@Singleton
class RetrofitApi @Inject constructor() {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val nasaApi: NasaApi = retrofit.create(NasaApi::class.java)

    fun getNasaApiInstance(): NasaApi = nasaApi
}