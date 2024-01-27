package com.example.mapapp

import android.app.Application
import com.example.mapapp.di.AppComponent
import com.example.mapapp.di.DaggerAppComponent


class App : Application(){
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create()
    }
}