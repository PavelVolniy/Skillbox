package com.example.recyclerviewapp.di

import com.example.recyclerviewapp.presentation.MainViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {

    fun mainViewModelFactory(): MainViewModelFactory
}