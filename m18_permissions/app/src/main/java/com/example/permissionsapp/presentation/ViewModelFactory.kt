package com.example.permissionsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val viewModel: ImagesViewModel,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(ImagesViewModel::class.java)) {
            return viewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}