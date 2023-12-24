package com.example.architectureapp.domain

import com.example.architectureapp.data.TaskRepository
import com.example.architectureapp.entity.Tasks
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {

    suspend fun getTask(): Tasks{
        return taskRepository.getTask()
    }
}