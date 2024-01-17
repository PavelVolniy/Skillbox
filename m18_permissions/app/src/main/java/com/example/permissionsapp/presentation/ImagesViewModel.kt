package com.example.permissionsapp.presentation

import androidx.lifecycle.ViewModel
import com.example.permissionsapp.data.room.DataBaseRepo
import com.example.permissionsapp.domain.entity.ImageItemDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class ImagesViewModel @Inject constructor(
    private val dataBaseRepo: DataBaseRepo
) : ViewModel() {
    private val _listImages = MutableStateFlow<List<ImageItemDTO>?>(null)
    val listImages = _listImages.asStateFlow()
    val loading = MutableStateFlow(false)

    suspend fun updateData() {
        loading.value = true
        _listImages.value = dataBaseRepo.getDb()?.getAll()
        loading.value = false
    }

    suspend fun addPhoto(nameFile: String) {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            .format(System.currentTimeMillis())
        dataBaseRepo.getDb()?.addPhotoItem(ImageItemDTO(0, nameFile, date))
    }
}