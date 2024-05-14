package com.hicham.home.domain.usecase

import com.hicham.data.persistence.model.Task
import com.hicham.data.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test


class GetTasksUseCaseTest {
    private val taskRepository: TaskRepository = mockk()
    private val useCase = GetTasksUseCase(taskRepository)

    @Test
    fun `test useCase returns all task returned by task repository when invoked`() = runTest {
        val task = Task(id = 1, "name", "desc", isDone = false, false, null, 3)
        coEvery { taskRepository.getAllTasks() } returns flowOf(listOf(task))

        val result = useCase.invoke(Unit).first()

        coVerify(exactly = 1) { taskRepository.getAllTasks() }
        Assert.assertEquals(listOf(task), result)
    }
}