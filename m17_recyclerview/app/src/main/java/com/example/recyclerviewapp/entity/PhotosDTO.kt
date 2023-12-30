package com.example.recyclerviewapp.entity

import javax.inject.Inject

data class PhotosDTO @Inject constructor(
    val photos: List<Photo>
): Photos