package com.example.architectureapp.di

import com.example.architectureapp.presentation.MainViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {

    fun mainViewModelFactory(): MainViewModelFactory

}