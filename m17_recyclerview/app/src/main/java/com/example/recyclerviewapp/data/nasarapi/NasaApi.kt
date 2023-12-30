package com.example.recyclerviewapp.data.nasarapi

import com.example.recyclerviewapp.entity.PhotosDTO
import retrofit2.http.GET
import retrofit2.http.Query

//TODO(attention!!!)
//todo apy key 4aMTO078yB3MRzNfnR8eipUtGMoHZKV3SEQx2UFb delete before push

interface NasaApi {

    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    fun getPhotos(
        @Query("earth_date") date: String,
        @Query("api_key") apiKey: String
    ): List<PhotosDTO>
}