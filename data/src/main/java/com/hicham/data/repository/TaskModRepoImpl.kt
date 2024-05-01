package com.hicham.data.repository

import com.hicham.data.persistence.model.Task

class TaskModRepoImpl : TaskModRepo {
    private lateinit var task: Task
    override fun getSelectedTask(): Task {
        return task
    }

    override fun setSelectTask(task: Task) {
        this.task = task
    }


}