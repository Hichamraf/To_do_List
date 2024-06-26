package com.hicham.data.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo val name: String,
    @ColumnInfo val description: String,
    @ColumnInfo val isDone: Boolean = false,
    @ColumnInfo val isFavorite: Boolean = false,
    @ColumnInfo val date: Date? = null,
    @ColumnInfo val priority: Int = 0
)