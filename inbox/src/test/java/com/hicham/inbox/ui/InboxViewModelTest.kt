package com.hicham.inbox.ui

import com.hicham.data.persistence.model.Task
import com.hicham.inbox.domain.GetAllTasksUseCas
import com.hicham.shared.domain.usecase.SetSelectedTaskUseCase
import com.hicham.shared.domain.usecase.UpdateTaskUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InboxViewModelTest {
    private val getAllTasksUseCas: GetAllTasksUseCas = mockk()
    private val updateTaskUseCase: UpdateTaskUseCase = mockk()
    private val setSelectedTaskUseCase: SetSelectedTaskUseCase = mockk()
    private lateinit var viewModel: InboxViewModel
    private val tasks = listOf(Task(name = "name", description = "description"))


    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `test on viewModel init loads all tasks and assign them to state`() {
        coEvery { getAllTasksUseCas.invoke(Unit) } returns flowOf(tasks)
        viewModel = InboxViewModel(getAllTasksUseCas, updateTaskUseCase, setSelectedTaskUseCase)

        coVerify(exactly = 1) { getAllTasksUseCas.invoke(Unit) }
        assertEquals(tasks, viewModel.viewState.value.tasks)
    }

    @Test
    fun `test OnTaskCheckChanged updates task status`() {
        coEvery { getAllTasksUseCas.invoke(Unit) } returns flowOf(tasks)
        coEvery { updateTaskUseCase.invoke(any()) } just runs
        viewModel = InboxViewModel(getAllTasksUseCas, updateTaskUseCase, setSelectedTaskUseCase)

        viewModel.processViewActions(InBoxAction.OnTaskCheckChanged(true, tasks.first()))

        coVerify { updateTaskUseCase.invoke(tasks.first().copy(isDone = true)) }
    }

    @Test
    fun `test OnTaskSelected updates selected task`(){
        coEvery { getAllTasksUseCas.invoke(Unit) } returns flowOf(tasks)
        coEvery { setSelectedTaskUseCase.invoke(any()) } just runs
        viewModel = InboxViewModel(getAllTasksUseCas, updateTaskUseCase, setSelectedTaskUseCase)


    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}