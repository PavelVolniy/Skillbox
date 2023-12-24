package com.example.architectureapp.data

import com.example.architectureapp.entity.Tasks
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDataSource: TaskDataSource
) {
    suspend fun getTask(): Tasks {
        return taskDataSource.getTask()
    }
}