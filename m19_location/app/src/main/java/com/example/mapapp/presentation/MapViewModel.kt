package com.example.mapapp.presentation

import androidx.lifecycle.ViewModel
import com.example.mapapp.data.InterestingObjects
import com.example.mapapp.domain.GetObjectsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapViewModel @Inject constructor(
    private val getObjectsUseCase: GetObjectsUseCase
) : ViewModel() {
    val listObjets: Flow<InterestingObjects> = getObjectsUseCase.getObjectList()
    var isCreated: Boolean = true


    fun created() {
        isCreated = false
    }
}