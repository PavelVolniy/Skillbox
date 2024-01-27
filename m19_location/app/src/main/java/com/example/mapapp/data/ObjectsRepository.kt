package com.example.mapapp.data

import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class ObjectsRepository @Inject constructor(){
    private val listObjects = listOf(
        InterestingObjects(
            title = "Fish point",
            descriptions = "They catch a lot of different fish here",
            latitude = 47.206224,
            longitude = 39.780654
        ),
        InterestingObjects(
            title = "Sandy beach",
            descriptions = "You can sunbathe here in summer",
            latitude = 47.213385,
            longitude = 39.731049
        ),
        InterestingObjects(
            title = "Rostov arena",
            descriptions = "Sport complex",
            latitude = 47.209020,
            longitude = 39.737662
        ),
        InterestingObjects(
            title = "Restaurant",
            descriptions = "You can have a delicious meal here",
            latitude = 47.204663,
            longitude = 39.765014
        ),
        InterestingObjects(
            title = "Treasure",
            descriptions = "There's a treasure buried here, but that's not for sure",
            latitude = 47.214591,
            longitude = 39.756463
        )
    )

    fun getObjects() = listObjects.asFlow()
}