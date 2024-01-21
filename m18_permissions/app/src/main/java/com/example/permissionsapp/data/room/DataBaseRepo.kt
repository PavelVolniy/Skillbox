package com.example.permissionsapp.data.room

import android.content.Context
import com.example.permissionsapp.App
import com.example.permissionsapp.domain.entity.ImageItemDTO
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DataBaseRepo @Inject constructor(
     private val context: Context
) {
    private var imagesDao: ImagesDao? = null

    init {
        imagesDao = (context.applicationContext as App).database.imageDAO()
    }
    suspend fun getAllImages() = imagesDao?.getAll()

    suspend fun addImages(imageItemDTO: ImageItemDTO) = imagesDao?.addPhotoItem(imageItemDTO)
}