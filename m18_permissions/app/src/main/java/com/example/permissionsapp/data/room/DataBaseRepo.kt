package com.example.permissionsapp.data.room

import com.example.permissionsapp.domain.entity.ImageItemDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class DataBaseRepo @Inject constructor(private val imagesDao: ImagesDao) {

    fun getAll(): Flow<List<ImageItemDTO>> {
        return imagesDao.getAll()
    }

    suspend fun addPhotoItem(item: ImageItemDTO) {
        imagesDao.addPhotoItem(item)
    }
}