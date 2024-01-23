package com.example.permissionsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.permissionsapp.data.room.DataBaseRepo
import com.example.permissionsapp.domain.entity.ImageItemDTO
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class ImagesViewModel @Inject constructor(
    private val dataBaseRepo: DataBaseRepo
) : ViewModel(), ViewModelProvider.Factory {
    var listImages: Flow<List<ImageItemDTO>>

    init {
        listImages = dataBaseRepo.getAll()
    }


    suspend fun addPhoto(nameFile: String) {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            .format(System.currentTimeMillis())
        dataBaseRepo.addPhotoItem(ImageItemDTO(imagePath = nameFile, date = date))
    }
}