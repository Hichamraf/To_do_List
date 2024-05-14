package com.hicham.task.taskdetails.domain.usecase

import com.hicham.data.persistence.model.Task
import com.hicham.data.repository.TaskModRepo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test


class GetSelectedTaskUseCaseTest {
    private val taskModRepo: TaskModRepo = mockk()
    private val useCase = GetSelectedTaskUseCase(taskModRepo)

    @Test
    fun `test useCase returns selected task from repository`() = runTest {
        val task = Task(id = 1, "name", "desc", isDone = false, false, null, 3)
        every { taskModRepo.getSelectedTask() } returns task

        val result = useCase.invoke(Unit)

        verify(exactly = 1) { taskModRepo.getSelectedTask() }
        assertEquals(task, result)
    }
}