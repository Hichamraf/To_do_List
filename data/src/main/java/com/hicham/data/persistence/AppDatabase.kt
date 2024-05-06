package com.hicham.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hicham.data.persistence.dao.TaskDao
import com.hicham.data.persistence.model.Converters
import com.hicham.data.persistence.model.Task

@Database(entities = [Task::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}