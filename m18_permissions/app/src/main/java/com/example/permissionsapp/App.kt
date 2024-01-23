package com.example.permissionsapp

import android.app.Application
import com.example.permissionsapp.di.AppComponent
import com.example.permissionsapp.di.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

}