package com.example.architectureapp.data

import com.example.architectureapp.entity.Tasks
import kotlinx.coroutines.delay
import javax.inject.Inject

class TaskDataSource @Inject constructor() {
    private val task = TaskDayDto(
        "asd",
        "asd",
        "asd",
        "asd",
        "asd",
        "asd",
        "asd"
    )

    suspend fun getTask(): Tasks {
        delay(1_000)
        return task
    }
}