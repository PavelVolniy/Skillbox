package com.example.mapapp.di

import com.example.mapapp.presentation.MapsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(): AppComponent
    }

    fun inject(fragment: MapsFragment)
}