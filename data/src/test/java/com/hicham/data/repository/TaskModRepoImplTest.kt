package com.hicham.data.repository

import com.hicham.data.persistence.model.Task
import org.junit.Assert
import org.junit.Test


class TaskModRepoImplTest {
    private val repo = TaskModRepoImpl()

    @Test
    fun `test setSelectedTask sets the task and getSelectedTask returns the right task`() {
        val task = Task(id = 1, "name", "desc", isDone = false, false, null, 3)
        repo.setSelectTask(task)
        val result = repo.getSelectedTask()
        Assert.assertEquals(task, result)
    }

}