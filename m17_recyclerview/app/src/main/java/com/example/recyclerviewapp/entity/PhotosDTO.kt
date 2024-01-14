package com.example.recyclerviewapp.entity

import com.google.gson.annotations.SerializedName

data class PhotosDTO(
    @SerializedName("photos") val photos: List<Photo>
)