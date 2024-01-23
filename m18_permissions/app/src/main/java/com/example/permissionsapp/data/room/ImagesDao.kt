package com.example.permissionsapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.permissionsapp.domain.entity.ImageItemDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagesDao {

    @Query("SELECT * FROM images")
    fun getAll(): Flow<List<ImageItemDTO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhotoItem(item: ImageItemDTO)
}