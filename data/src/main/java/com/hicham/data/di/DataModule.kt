package com.hicham.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hicham.data.persistence.AppDatabase
import com.hicham.data.persistence.dao.TaskDao
import com.hicham.data.repository.TaskModRepo
import com.hicham.data.repository.TaskModRepoImpl
import com.hicham.data.repository.TaskRepository
import com.hicham.data.repository.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
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

    @Singleton
    @Provides
    fun provideTaskRepo(taskDao: TaskDao): TaskRepository = TaskRepositoryImpl(taskDao)

    @Singleton
    @Provides
    fun provideTaskModRepo() : TaskModRepo=TaskModRepoImpl()
}