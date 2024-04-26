package com.hicham.data.repository

import com.hicham.data.persistence.dao.TaskDao
import com.hicham.data.persistence.model.Task
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao) : TaskRepository {
    override suspend fun getAllTasks(): List<Task> = taskDao.getAll()

    override suspend fun searchTask(query: String): List<Task> = taskDao.searchTask(query)

    override suspend fun insertTask(vararg task: Task) = taskDao.insertTasks(*task)

    override suspend fun removeTask(task: Task) = taskDao.delete(task)
}