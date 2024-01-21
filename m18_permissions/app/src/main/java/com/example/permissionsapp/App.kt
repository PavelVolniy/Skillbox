package com.example.permissionsapp

import android.app.Application
import androidx.room.Room
import com.example.permissionsapp.data.room.AppDataBase
import com.example.permissionsapp.di.AppComponent

class App : Application() {
    lateinit var database: AppDataBase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "db"
        ).build()
    }
}