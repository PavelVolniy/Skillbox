package com.example.architectureapp.data

import com.example.architectureapp.data.taskapi.RetrofitRepo
import com.example.architectureapp.entity.Tasks
import javax.inject.Inject

class TaskDataSource @Inject constructor(
    private val taskApi: RetrofitRepo
) {

    suspend fun getTask(): Tasks {
        return taskApi.getRetrofitInstance().getNewTask()
    }
}