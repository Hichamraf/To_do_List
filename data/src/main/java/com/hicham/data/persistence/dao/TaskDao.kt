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

    @Query("SELECT * FROM task WHERE isDone=:isDone ORDER by priority DESC")
    fun getAll(isDone: Boolean = false): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE date = :date ORDER by priority DESC")
    fun getTaskByDate(date: Long): Flow<List<Task>>

    @Query("SELECT * FROM task where name LIKE :query OR description LIKE :query")
    suspend fun searchTask(query: String): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(vararg task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)

    @Query("SELECT * FROM task WHERE id = :taskId")
    suspend fun findTaskById(taskId: Int): Task

    @Query("SELECT * FROM task WHERE isFavorite = :isFavorite AND isDone=:isDone")
    fun getFavoriteTasks(isFavorite: Boolean = true, isDone: Boolean = false): Flow<List<Task>>
}