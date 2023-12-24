package com.example.architectureapp.data

import com.example.architectureapp.entity.Tasks
import javax.inject.Inject

data class TaskDayDto @Inject constructor(
    override val accessibility: String,
    override val activity: String,
    override val key: String,
    override val link: String,
    override val participants: String,
    override val price: String,
    override val type: String
) : Tasks