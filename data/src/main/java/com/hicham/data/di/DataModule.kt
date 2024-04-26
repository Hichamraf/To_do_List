package com.hicham.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hicham.data.persistence.AppDatabase
import com.hicham.data.persistence.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideTaskDb(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "taskDatabase").build()

    @Singleton
    @Provides
    fun provideTaskDao(appDatabase: AppDatabase): TaskDao =
        appDatabase.taskDao()
}