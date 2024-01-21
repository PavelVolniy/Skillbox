package com.example.permissionsapp.presentation

import android.util.Log
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
        val list = dataBaseRepo.getAllImages()
        list?.forEach { Log.e("mlist", it.imagePath) }
        loading.value = true
        _listImages.value = list
        loading.value = false
    }

    suspend fun addPhoto(nameFile: String) {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            .format(System.currentTimeMillis())
        Log.e("test", nameFile)
        dataBaseRepo.addImages(ImageItemDTO(1, nameFile, date))
    }
}