package com.hicham.core.utils

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsUtilsImpl @Inject constructor(private val sharedPreferences: SharedPreferences) : SharedPrefsUtils {

    override fun save(name: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(name, value)
        editor.apply()
    }

    override fun getBoolean(name: String): Boolean {
        return sharedPreferences.getBoolean(name, true)
    }
}