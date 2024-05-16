package com.hicham.home.domain.usecase

import com.hicham.data.persistence.model.Task
import com.hicham.data.repository.TaskModRepo
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Test


class SetSelectedTaskUseCaseTest {

    private val taskRepository: TaskModRepo = mockk(relaxUnitFun = true)
    private val useCase = com.hicham.shared.domain.usecase.SetSelectedTaskUseCase(taskRepository)

    @Test
    fun `test useCase sets selected task to task repository when invoked`()= runTest {
        val task = Task(id = 1, "name", "desc", isDone = false, false, null, 3)

        useCase(task)

        verify(exactly = 1) { taskRepository.setSelectTask(task) }
    }
}