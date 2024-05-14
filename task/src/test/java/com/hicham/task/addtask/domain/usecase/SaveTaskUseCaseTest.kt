package com.hicham.task.addtask.domain.usecase

import com.hicham.data.persistence.model.Task
import com.hicham.data.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Test


class SaveTaskUseCaseTest {

    private val taskRepository: TaskRepository = mockk(relaxUnitFun = true)
    private val useCase = SaveTaskUseCase(taskRepository)

    @Test
    fun `fun test useCase save the task to the task repository`() = runTest {
        val task = Task(id = 1, "name", "desc", isDone = false, false, null, 3)

        useCase.invoke(task)

        coVerify(exactly = 1) { taskRepository.insertTask(task) }
    }

}