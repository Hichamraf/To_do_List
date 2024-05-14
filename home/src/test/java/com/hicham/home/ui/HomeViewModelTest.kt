package com.hicham.home.ui

import com.hicham.data.persistence.model.Task
import com.hicham.home.domain.usecase.GetTaskByDateUseCase
import com.hicham.home.domain.usecase.SetSelectedTaskUseCase
import com.hicham.home.domain.usecase.UpdateTaskUseCase
import com.hicham.home.ui.HomeAction.OnTaskCheckChanged
import com.hicham.home.ui.HomeAction.OnTaskSelected
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
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val getTaskByDateUseCase: GetTaskByDateUseCase = mockk()
    private val updateTaskUseCase: UpdateTaskUseCase = mockk(relaxUnitFun = true)
    private val setSelectedTaskUseCase: SetSelectedTaskUseCase = mockk(relaxUnitFun = true)
    private val task = Task(id = 1, "name", "desc", isDone = false, false, null, 3)
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        coEvery { getTaskByDateUseCase.invoke(any()) } returns flowOf(listOf(task))
    }

    @Test
    fun `test viewmodel on init loads tasks of the day`() = runTest {
        viewModel = HomeViewModel(getTaskByDateUseCase, updateTaskUseCase, setSelectedTaskUseCase)
        runCurrent()

        coVerify(exactly = 1) { getTaskByDateUseCase(any()) }
        Assert.assertEquals(listOf(task), viewModel.currentViewState().taskList)

    }

    @Test
    fun `test OnTaskCHeckChanged update task status in database`() = runTest {
        viewModel = HomeViewModel(getTaskByDateUseCase, updateTaskUseCase, setSelectedTaskUseCase)
        coEvery { updateTaskUseCase.invoke(any()) } just runs

        viewModel.processViewActions(OnTaskCheckChanged(true, task))
        runCurrent()

        coVerify { updateTaskUseCase.invoke(task.copy(isDone = true)) }
    }

    @Test
    fun `test OnTaskSelected updates selected task in the repo`() = runTest {
        viewModel = HomeViewModel(getTaskByDateUseCase, updateTaskUseCase, setSelectedTaskUseCase)
        coEvery { setSelectedTaskUseCase.invoke(task) } just runs

        viewModel.processViewActions(OnTaskSelected(task))
        runCurrent()

        coVerify { setSelectedTaskUseCase.invoke(task) }
    }

    @Test
    fun `test OnTaskFavoriteClicked updates task status`() = runTest {
        viewModel = HomeViewModel(getTaskByDateUseCase, updateTaskUseCase, setSelectedTaskUseCase)
        coEvery { updateTaskUseCase.invoke(task.copy(isFavorite = true)) } just runs

        viewModel.processViewActions(HomeAction.OnTaskFavoriteClicked(true, task))
        runCurrent()

        coVerify { updateTaskUseCase.invoke(task.copy(isFavorite = true)) }
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }
}