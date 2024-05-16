package com.hicham.home.domain.usecase

import com.hicham.data.persistence.model.Task
import com.hicham.data.repository.TaskRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test


class UpdateTaskUseCaseTest{
    private val taskRepository: TaskRepository = mockk(relaxUnitFun = true)
    private val useCase = com.hicham.shared.domain.usecase.UpdateTaskUseCase(taskRepository)

    @Test
    fun `test useCase update task in taskRepo when invoked`() = runTest{
        val task = Task(id = 1, "name", "desc", isDone = false, false, null, 3)

        useCase.invoke(task)

        coVerify(exactly = 1) { taskRepository.updateTask(task) }
    }

}