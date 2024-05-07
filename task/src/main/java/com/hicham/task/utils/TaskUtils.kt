package com.hicham.task.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.runtime.remember
import com.hicham.core.utils.getTodayStartOfDayMillis
import com.hicham.data.persistence.model.Task
import java.util.*

fun Task.isTaskValid() = name.isNotEmpty()

@OptIn(ExperimentalMaterial3Api::class)
fun createCustomSelectableDates(): SelectableDates {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    return object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis >= getTodayStartOfDayMillis()
        }

        override fun isSelectableYear(year: Int): Boolean {
            return year >= currentYear
        }
    }
}