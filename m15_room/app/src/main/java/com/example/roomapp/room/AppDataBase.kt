package com.example.roomapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun wordsDao(): WordsDao

    companion object {
        private var INSTANCE: AppDataBase? = null

        fun getDataBase(context: Context): AppDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "words"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}