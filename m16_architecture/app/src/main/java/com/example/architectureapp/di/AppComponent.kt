package com.example.architectureapp.di

import com.example.architectureapp.presentation.MainViewModelFactory
import dagger.Component

@Component
interface AppComponent {

    fun mainViewModelFactory(): MainViewModelFactory

}