package com.hicham.data.repository

import com.hicham.data.persistence.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getAllTasks(): Flow<List<Task>>
    suspend fun searchTask(query: String): List<Task>
    suspend fun insertTask(vararg task: Task)
    suspend fun removeTask(task: Task)

    suspend fun updateTask(task: Task)
}