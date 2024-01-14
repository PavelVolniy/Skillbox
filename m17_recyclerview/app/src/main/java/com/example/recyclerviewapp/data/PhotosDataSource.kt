package com.example.recyclerviewapp.data

import com.example.recyclerviewapp.data.nasarapi.RetrofitApi
import com.example.recyclerviewapp.entity.Photo
import javax.inject.Inject

private const val API_KEY = "Hn3Rovy9d9DFJyWoW3j8RIachpV6cuYOJxCW6REX" //TODO replace to file

class PhotosDataSource @Inject constructor(
    private val nasaApi: RetrofitApi
) {
    suspend fun getPhotos(page: Int): List<Photo> {
        val result = nasaApi.getNasaApiInstance().getPhotos(1000, page, API_KEY)
        return result.photos
    }
}