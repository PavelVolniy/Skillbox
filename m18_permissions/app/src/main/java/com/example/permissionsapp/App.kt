package com.example.permissionsapp

import android.app.Application
import androidx.room.Room
import com.example.permissionsapp.data.room.AppDataBase

class App : Application() {
    var database: AppDataBase? = null

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "images"
        ).build()
    }
}