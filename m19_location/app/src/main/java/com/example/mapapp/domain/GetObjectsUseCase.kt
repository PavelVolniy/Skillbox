package com.example.mapapp.domain

import com.example.mapapp.data.InterestingObjects
import com.example.mapapp.data.ObjectsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetObjectsUseCase @Inject constructor(private val objectsRepository: ObjectsRepository) {
    fun getObjectList(): Flow<InterestingObjects> = objectsRepository.getObjects()
}