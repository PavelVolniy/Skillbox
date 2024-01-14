package com.example.recyclerviewapp.entity

data class Rover(
    val cameras: List<CameraX>,
    val id: String,
    val landing_date: String,
    val launch_date: String,
    val max_date: String,
    val max_sol: String,
    val name: String,
    val status: String,
    val total_photos: String
)