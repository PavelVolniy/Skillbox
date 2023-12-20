package com.example.roomapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @PrimaryKey()
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "count")
    var count: Int
)
