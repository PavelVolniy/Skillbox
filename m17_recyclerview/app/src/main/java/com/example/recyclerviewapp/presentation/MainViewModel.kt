package com.example.recyclerviewapp.presentation

import androidx.lifecycle.ViewModel
import com.example.recyclerviewapp.domain.GetPhotosUseCase
import com.example.recyclerviewapp.entity.Photos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val useCase: GetPhotosUseCase
) : ViewModel() {
    private val _listPhotos = MutableStateFlow<Photos?>(null)
    val listPhotos = _listPhotos.asStateFlow()

    suspend fun updateData() {
        _listPhotos.value = useCase.getPhotos()[0]
    }
}