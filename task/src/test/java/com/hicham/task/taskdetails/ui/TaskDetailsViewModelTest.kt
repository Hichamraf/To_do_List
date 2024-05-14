package com.hicham.task.taskdetails.ui

import com.hicham.core.utils.millisToDate
import com.hicham.data.persistence.model.Task
import com.hicham.task.taskdetails.domain.usecase.GetSelectedTaskUseCase
import com.hicham.task.taskdetails.domain.usecase.UpdateTaskUseCase
import com.hicham.task.taskdetails.ui.TaskDetailAction.OnNameTextChanged
import com.hicham.task.taskdetails.ui.TaskDetailAction.OnTaskCheckBoxChanged
import com.hicham.task.taskdetails.ui.TaskDetailAction.UpdateTask
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import java.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class TaskDetailsViewModelTest {

    private val updateTaskUseCase: UpdateTaskUseCase = mockk()
    private val getSelectedTaskUseCase: GetSelectedTaskUseCase = mockk()
    private val task = Task(id = 1, "name", "desc", isDone = false, false, Date(123456789), 3)

    private lateinit var viewModel: TaskDetailsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `test on viewModel init selected task is assigned to current state`() = runTest {
        coEvery { getSelectedTaskUseCase.invoke(Unit) } returns task

        viewModel = TaskDetailsViewModel(updateTaskUseCase, getSelectedTaskUseCase)

        coVerify(exactly = 1) { getSelectedTaskUseCase.invoke(Unit) }
        assertEquals(task, viewModel.currentViewState().task)
        assertEquals(millisToDate(task.date?.time!!), viewModel.currentViewState().date)
    }

    @Test
    fun `test getDate returns null when selected task's date is null`() = runTest {
        coEvery { getSelectedTaskUseCase.invoke(Unit) } returns task.copy(date = null)

        viewModel = TaskDetailsViewModel(updateTaskUseCase, getSelectedTaskUseCase)

        coVerify(exactly = 1) { getSelectedTaskUseCase.invoke(Unit) }
        assertEquals(null, viewModel.currentViewState().date)
    }

    @Test
    fun `test UpdateTask event calls useCase when task is valid`() = runTest(UnconfinedTestDispatcher()) {
        coEvery { getSelectedTaskUseCase.invoke(Unit) } returns task
        coEvery { updateTaskUseCase.invoke(task) } just runs

        viewModel = TaskDetailsViewModel(updateTaskUseCase, getSelectedTaskUseCase)
        viewModel.selectedTask = task
        task.run {
            viewModel.processViewActions(UpdateTask(name, description, isDone, date?.time, priority))
        }

        coVerify(exactly = 1) { updateTaskUseCase.invoke(task) }
    }

    @Test
    fun `test UpdateTask event does not call useCase and update state when task is not valid`() {
        coEvery { getSelectedTaskUseCase.invoke(Unit) } returns task
        coEvery { updateTaskUseCase.invoke(task) } just runs

        viewModel = TaskDetailsViewModel(updateTaskUseCase, getSelectedTaskUseCase)
        viewModel.selectedTask = task

        task.run {
            viewModel.processViewActions(UpdateTask("", description, isDone, date?.time, priority))
        }

        coVerify(exactly = 0) { updateTaskUseCase.invoke(any()) }
        assertTrue(viewModel.currentViewState().sendButtonEnabled)
    }

    @Test
    fun `test OnNameTextChanged update send button visibility state to true when text is not empty`() {
        coEvery { getSelectedTaskUseCase.invoke(Unit) } returns task

        viewModel = TaskDetailsViewModel(updateTaskUseCase, getSelectedTaskUseCase)
        viewModel.selectedTask = task
        viewModel.processViewActions(UpdateTask(name = "", descriptor = "", false, null, 1))
        viewModel.processViewActions(OnNameTextChanged("text"))

        assertTrue(viewModel.currentViewState().sendButtonEnabled)
    }

    @Test
    fun `test OnNameTextChanged update send button visibility state to false when text is empty`() {
        coEvery { getSelectedTaskUseCase.invoke(Unit) } returns task

        viewModel = TaskDetailsViewModel(updateTaskUseCase, getSelectedTaskUseCase)
        viewModel.selectedTask = task
        viewModel.processViewActions(UpdateTask(name = "", descriptor = "", false, null, 1))
        viewModel.processViewActions(OnNameTextChanged(""))

        assertFalse(viewModel.currentViewState().sendButtonEnabled)
    }

    @Test
    fun `test OnTaskCheckBoxChanged updates ui state to done when true`() {
        coEvery { getSelectedTaskUseCase.invoke(Unit) } returns task

        viewModel = TaskDetailsViewModel(updateTaskUseCase, getSelectedTaskUseCase)
        viewModel.selectedTask = task

        viewModel.processViewActions(OnTaskCheckBoxChanged(true))

        assertTrue(viewModel.currentViewState().task!!.isDone)
    }

    @Test
    fun `test OnTaskCheckBoxChanged updates ui state to not done when false`() {
        coEvery { getSelectedTaskUseCase.invoke(Unit) } returns task

        viewModel = TaskDetailsViewModel(updateTaskUseCase, getSelectedTaskUseCase)
        viewModel.selectedTask = task

        viewModel.processViewActions(OnTaskCheckBoxChanged(false))

        assertFalse(viewModel.currentViewState().task!!.isDone)
    }

    @Test
    fun `test OnDateChanged updates ui state date`() {
        val date: Long = 123456789
        coEvery { getSelectedTaskUseCase.invoke(Unit) } returns task

        viewModel = TaskDetailsViewModel(updateTaskUseCase, getSelectedTaskUseCase)
        viewModel.selectedTask = task

        viewModel.processViewActions(TaskDetailAction.OnDateChanged(date))

        assertEquals(millisToDate(date),viewModel.currentViewState().date)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}