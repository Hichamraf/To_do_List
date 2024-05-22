package com.hicham.home.ui

import com.hicham.data.persistence.model.Task
import com.hicham.shared.domain.usecase.GetTaskByDateUseCase
import com.hicham.shared.domain.usecase.SetSelectedTaskUseCase
import com.hicham.shared.domain.usecase.UpdateTaskUseCase
import com.hicham.home.ui.HomeAction.OnTaskCheckChanged
import com.hicham.home.ui.HomeAction.OnTaskSelected
import com.hicham.navigation.NavigationItem
import com.hicham.navigation.Navigator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
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
    private val navigator : Navigator = mockk(relaxUnitFun = true)
    private val task = Task(id = 1, "name", "desc", isDone = false, false, null, 3)
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        coEvery { getTaskByDateUseCase.invoke(any()) } returns flowOf(listOf(task))
    }

    @Test
    fun `test viewmodel on init loads tasks of the day`() = runTest {
        viewModel = HomeViewModel(getTaskByDateUseCase, updateTaskUseCase, setSelectedTaskUseCase,navigator)
        runCurrent()

        coVerify(exactly = 1) { getTaskByDateUseCase(any()) }
        Assert.assertEquals(listOf(task), viewModel.currentViewState().taskList)

    }

    @Test
    fun `test OnTaskCHeckChanged update task status in database`() = runTest {
        viewModel = HomeViewModel(getTaskByDateUseCase, updateTaskUseCase, setSelectedTaskUseCase,navigator)
        coEvery { updateTaskUseCase.invoke(any()) } just runs

        viewModel.processViewActions(OnTaskCheckChanged(true, task))
        runCurrent()

        coVerify { updateTaskUseCase.invoke(task.copy(isDone = true)) }
    }

    @Test
    fun `test OnTaskSelected updates selected task in the repo and navigates to next screen`() = runTest {
        viewModel = HomeViewModel(getTaskByDateUseCase, updateTaskUseCase, setSelectedTaskUseCase, navigator)
        coEvery { setSelectedTaskUseCase.invoke(task) } just runs

        viewModel.processViewActions(OnTaskSelected(task))
        runCurrent()

        coVerify { setSelectedTaskUseCase.invoke(task) }
        verify { navigator.navigateTo(NavigationItem.UpdateTask) }
    }

    @Test
    fun `test OnTaskFavoriteClicked updates task status`() = runTest {
        viewModel = HomeViewModel(getTaskByDateUseCase, updateTaskUseCase, setSelectedTaskUseCase,navigator)
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