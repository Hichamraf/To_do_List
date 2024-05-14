package com.hicham.data.repository

import com.hicham.data.persistence.dao.TaskDao
import com.hicham.data.persistence.model.Task
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test


class TaskRepositoryImplTest {

    private val taskDao: TaskDao = mockk(relaxUnitFun = true)
    private val task = Task(id = 1, "name", "desc", isDone = false, false, null, 3)
    private val taskRepository = TaskRepositoryImpl(taskDao)

    @Test
    fun `test getAll returns tasks from taskDao`() = runTest {
        coEvery { taskDao.getAll() } returns flowOf(listOf(task))

        val result = taskRepository.getAllTasks().first()

        coVerify(exactly = 1) { taskDao.getAll() }
        assertEquals(listOf(task), result)
    }


    @Test
    fun `test getTasksByDate returns task from taskDao`() = runTest{
        val date: Long = 123456789
        coEvery { taskDao.getTaskByDate(date) } returns flowOf(listOf(task))

        val result=taskRepository.getTasksByDate(date).first()

        coVerify(exactly = 1) { taskDao.getTaskByDate(date) }
        assertEquals(listOf(task),result)
    }


    @Test
    fun `test searchTask returns tasks from taskDao`()= runTest {
        val query="query"
        coEvery { taskDao.searchTask(query) } returns listOf(task)

        val result=taskRepository.searchTask(query)

        coVerify(exactly = 1) { taskDao.searchTask(query) }
        assertEquals(listOf(task),result)
    }


    @Test
    fun `test insertTask inserts task in taskDao`() = runTest{
        taskRepository.insertTask(task)

        coVerify(exactly = 1) { taskRepository.insertTask(task) }
    }


    @Test
    fun `test removeTask removes task from taskDao`() = runTest{
        taskRepository.removeTask(task)

        coVerify(exactly = 1) { taskRepository.removeTask(task) }
    }


    @Test
    fun `test updateTask updates task in taskDao`()= runTest {
        taskRepository.updateTask(task)

        coVerify { taskDao.update(task) }
    }
}