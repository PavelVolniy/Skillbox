package com.example.permissionsapp.data.room

import com.example.permissionsapp.App
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DataBaseRepo @Inject constructor() {
    private val db = App().database?.imageDAO()

    fun getDb() = db
}