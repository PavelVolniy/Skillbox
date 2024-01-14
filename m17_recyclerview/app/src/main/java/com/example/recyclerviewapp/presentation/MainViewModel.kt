package com.example.recyclerviewapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recyclerviewapp.data.nasarapi.RetrofitApi
import com.example.recyclerviewapp.entity.Photo
import com.example.recyclerviewapp.entity.PhotoPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val api: RetrofitApi
) : ViewModel() {
    val listPhotosPaging: Flow<PagingData<Photo>> = Pager(
        config = PagingConfig(8),
        pagingSourceFactory = { PhotoPagingSource(api) }
    ).flow.cachedIn(viewModelScope)
}