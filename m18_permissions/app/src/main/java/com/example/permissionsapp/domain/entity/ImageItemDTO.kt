package com.example.permissionsapp.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageItemDTO(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "image_path")
    val imagePath: String,
    @ColumnInfo(name = "date")
    val date: String
)