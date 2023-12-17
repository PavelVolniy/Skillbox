package com.example.roomapp

import android.app.Application
import androidx.room.Room
import com.example.roomapp.room.AppDataBase

class App : Application() {
    lateinit var db: AppDataBase

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "dictionary"
        ).build()
    }
}