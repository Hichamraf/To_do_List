package com.hicham.data.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hicham.data.persistence.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("Select * FROM task ORDER by priority DESC")
    fun getAll(): Flow<List<Task>>

    @Query("Select * FROM task WHERE date = :date ORDER by priority DESC")
    fun getTaskByDate(date: Long): Flow<List<Task>>

    @Query("Select * FROM task where name LIKE :query OR description LIKE :query")
    suspend fun searchTask(query: String): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(vararg task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)

    @Query("Select * FROM task WHERE id = :taskId")
    suspend fun findTaskById(taskId: Int): Task
}