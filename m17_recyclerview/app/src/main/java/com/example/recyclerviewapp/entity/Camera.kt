package com.example.recyclerviewapp.entity

import javax.inject.Inject

data class Camera @Inject constructor(
    val full_name: String,
    val id: Int,
    val name: String,
    val rover_id: Int
)