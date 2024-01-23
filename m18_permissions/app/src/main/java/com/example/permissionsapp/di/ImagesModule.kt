package com.example.permissionsapp.di

import android.content.Context
import androidx.room.Room
import com.example.permissionsapp.data.room.AppDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ImagesModule {

    @Singleton
    @Provides
    fun provideDataBase(context: Context) = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        "db"
    ).build()

    @Singleton
    @Provides
    fun provideImagesDao(db: AppDataBase) = db.imageDAO()
}