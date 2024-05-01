package com.hicham.data.repository

import com.hicham.data.persistence.model.Task

interface TaskModRepo {
    fun getSelectedTask() : Task
    fun setSelectTask(task: Task)
}