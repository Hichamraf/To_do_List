package com.hicham.core.utils

interface SharedPrefsUtils {
    fun save(name: String, value: Boolean)

    fun getBoolean(name: String): Boolean
}