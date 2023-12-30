package com.example.recyclerviewapp.entity

import javax.inject.Inject

data class Photo @Inject constructor(
    val camera: Camera,
    val earth_date: String,
    val id: Int,
    val img_src: String,
    val rover: Rover,
    val sol: Int
)