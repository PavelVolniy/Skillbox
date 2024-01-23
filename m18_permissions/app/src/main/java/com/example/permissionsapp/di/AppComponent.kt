package com.example.permissionsapp.di

import android.content.Context
import com.example.permissionsapp.presentation.ImagesFragment
import com.example.permissionsapp.presentation.PhotoFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ImagesModule::class])
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: ImagesFragment)
    fun inject(fragment: PhotoFragment)

}