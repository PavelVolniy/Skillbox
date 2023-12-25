package com.example.architectureapp.data.taskapi

import com.example.architectureapp.data.TaskDayDto
import retrofit2.http.GET

interface TaskApi {
    @GET("/API/activity")
    suspend fun getNewTask(): TaskDayDto
}