package com.example.recyclerviewapp.domain

import com.example.recyclerviewapp.data.PhotosRepository
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val repository: PhotosRepository
) {
    suspend fun getPhotos() = repository.getPhotos()
}