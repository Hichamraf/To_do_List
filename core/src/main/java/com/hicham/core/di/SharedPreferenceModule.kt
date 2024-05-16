package com.hicham.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.hicham.core.utils.SharedPrefsUtils
import com.hicham.core.utils.SharedPrefsUtilsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val SHARED_PREFS_NAME = "todo_shared_prefs"

@InstallIn(SingletonComponent::class)
@Module
object SharedPreferenceModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Application): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPrefUtils(sharedPreferences: SharedPreferences): SharedPrefsUtils = SharedPrefsUtilsImpl(sharedPreferences)
}