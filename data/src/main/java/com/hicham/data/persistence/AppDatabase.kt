package com.hicham.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hicham.data.persistence.dao.TaskDao
import com.hicham.data.persistence.model.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}