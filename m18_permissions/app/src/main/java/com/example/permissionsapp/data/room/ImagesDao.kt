package com.example.permissionsapp.data.room

import androidx.room.Dao
import androidx.room.Query
import com.example.permissionsapp.entity.ImageItemDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagesDao {

    @Query("SELECT * FROM images")
    fun getAll(): Flow<List<ImageItemDTO>>
}