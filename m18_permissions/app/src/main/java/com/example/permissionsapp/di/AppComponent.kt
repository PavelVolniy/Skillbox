package com.example.permissionsapp.di

import com.example.permissionsapp.presentation.ViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {

    fun viewModelFactory(): ViewModelFactory
}