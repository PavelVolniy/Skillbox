package com.example.recyclerviewapp.data

import javax.inject.Inject

class PhotosRepository @Inject constructor(
    private val photosDataSource: PhotosDataSource
) {

    suspend fun getPhotos() = photosDataSource.getPhotos()
}