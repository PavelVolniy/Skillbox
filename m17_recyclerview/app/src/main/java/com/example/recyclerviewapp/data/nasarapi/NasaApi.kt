package com.example.recyclerviewapp.data.nasarapi

import com.example.recyclerviewapp.entity.PhotosDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getPhotos(
        @Query("sol") sol: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): PhotosDTO
}