package com.hicham.data.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hicham.data.persistence.model.Task

@Dao
interface TaskDao {

    @Query("Select * FROM task")
    suspend fun getAll(): List<Task>

    @Query("Select * FROM task where name LIKE :query OR description LIKE :query")
    suspend fun searchTask(query: String): List<Task>

    @Insert
    suspend fun insertTasks(vararg task: Task)

    @Delete
    suspend fun delete(task: Task)
}