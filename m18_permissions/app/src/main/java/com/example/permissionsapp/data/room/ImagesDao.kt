package com.example.permissionsapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.permissionsapp.domain.entity.ImageItemDTO

@Dao
interface ImagesDao {

    @Query("SELECT * FROM images")
    fun getAll(): List<ImageItemDTO>

    @Insert
    fun addPhotoItem(item: ImageItemDTO)
}