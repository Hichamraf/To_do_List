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


class GetTaskByDateUseCaseTest {

    private val taskRepository: TaskRepository = mockk()
    private val useCase = GetTaskByDateUseCase(taskRepository)

    @Test
    fun `test useCase returns tasks by date from repository when invoked`() = runTest {
        val task = Task(id = 1, "name", "desc", isDone = false, false, null, 3)
        val date = 12345567889
        coEvery { taskRepository.getTasksByDate(date) } returns flowOf(listOf(task))

        val result = useCase.invoke(date).first()

        coVerify(exactly = 1) { taskRepository.getTasksByDate(date) }
        Assert.assertEquals(listOf(task), result)
    }
}