package com.example.retrofitapp

import com.example.retrofitapp.json.Coordinates
import com.example.retrofitapp.json.Dob
import com.example.retrofitapp.json.Id
import com.example.retrofitapp.json.Info
import com.example.retrofitapp.json.Location
import com.example.retrofitapp.json.Login
import com.example.retrofitapp.json.Name
import com.example.retrofitapp.json.Picture
import com.example.retrofitapp.json.Registered
import com.example.retrofitapp.json.Result
import com.example.retrofitapp.json.Street
import com.example.retrofitapp.json.Timezone
import com.example.retrofitapp.json.User
import kotlinx.coroutines.delay

class RetrofitRepo {

    suspend fun getData(text: String): User {
        delay(2_000)
        return User(
            Info(22, 10, "seed", "01.1"),
            listOf(
                Result(
                    "cell",
                    Dob(23, "10.22.2023"),
                    "aaaa@aa.com",
                    "man",
                    Id("idName", "Any"),
                    Location(
                        "Moscow",
                        Coordinates("00000000", "00000000"),
                        "Russia",
                        354,
                        "state street",
                        Street("nameStreet", 120),
                        Timezone("some zone", "+3"),
                    ),
                    Login(
                        "3a54a3s54d",
                        "password",
                        "salt",
                        "asdas",
                        "sha256",
                        "userName",
                        "uuid"
                    ),
                    Name("firstName", "lastName", "Title"),
                    "nat?",
                    "+7664 535 44 45",
                    Picture(
                        "/a/as/das/21/asd.jpg",
                        medium = "some path",
                        thumbnail = "some path"
                    ),
                    Registered(22, "20.12.2023")
                )
            )
        )
    }
}