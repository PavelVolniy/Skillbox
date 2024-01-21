package com.example.permissionsapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.permissionsapp.domain.entity.ImageItemDTO

@Database(entities = [ImageItemDTO::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun imageDAO(): ImagesDao

}