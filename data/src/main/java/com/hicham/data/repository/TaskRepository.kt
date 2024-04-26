package com.hicham.data.repository

import com.hicham.data.persistence.model.Task

interface TaskRepository {
    suspend fun getAllTasks(): List<Task>
    suspend fun searchTask(query: String): List<Task>
    suspend fun insertTask(vararg task: Task)
    suspend fun removeTask(task: Task)
}