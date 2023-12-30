package com.example.recyclerviewapp.data

import com.example.recyclerviewapp.data.nasarapi.RetrofitApi
import com.example.recyclerviewapp.entity.Photos
import javax.inject.Inject

private const val API_KEY = "4aMTO078yB3MRzNfnR8eipUtGMoHZKV3SEQx2UFb"

class PhotosDataSource @Inject constructor(
    private val nasaApi: RetrofitApi
) {
    suspend fun getPhotos(): List<Photos> {
        return nasaApi.getNasaApiInstance().getPhotos("2020-1-1", API_KEY)
    }
}