package com.hicham.task.taskdetails.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.hicham.core.ui.BaseViewModel
import com.hicham.core.utils.createDateFromMillis
import com.hicham.core.utils.getTodayStartOfDayMillis
import com.hicham.core.utils.millisToDate
import com.hicham.data.persistence.model.Task
import com.hicham.navigation.NavigationItem
import com.hicham.navigation.NavigationItem.GoBack
import com.hicham.navigation.Navigator
import com.hicham.task.taskdetails.domain.usecase.GetSelectedTaskUseCase
import com.hicham.task.taskdetails.domain.usecase.UpdateTaskUseCase
import com.hicham.task.taskdetails.ui.TaskDetailAction.OnDateChanged
import com.hicham.task.taskdetails.ui.TaskDetailAction.OnNameTextChanged
import com.hicham.task.taskdetails.ui.TaskDetailAction.OnTaskCheckBoxChanged
import com.hicham.task.taskdetails.ui.TaskDetailAction.UpdateTask
import com.hicham.task.utils.isTaskValid
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val getSelectedTaskUseCase: GetSelectedTaskUseCase,
    private val navigator: Navigator
) : BaseViewModel<TaskDetailsUiState, TaskDetailAction, TaskDetailsEvent>() {

    @VisibleForTesting
    lateinit var selectedTask: Task

    init {
        viewModelScope.launch {
            selectedTask = getSelectedTaskUseCase(Unit)
            updateViewState {
                currentViewState().copy(task = selectedTask, date = getDate(selectedTask.date))
            }
            checkSendButtonStatus(selectedTask.name)
        }
    }

    private fun getDate(date: Date?): String? {
        if (date?.time != getTodayStartOfDayMillis() && date != null)
            return millisToDate(date.time)
        return null
    }

    override fun createInitialState(): TaskDetailsUiState {
        return TaskDetailsUiState()
    }

    override fun processViewActions(viewAction: TaskDetailAction) {
        when (viewAction) {
            is UpdateTask -> processUpdateTask(viewAction.name, viewAction.descriptor, viewAction.isDone, viewAction.date, viewAction.priority)
            is OnNameTextChanged -> checkSendButtonStatus(viewAction.newText)
            is OnTaskCheckBoxChanged -> updateTaskState(viewAction.isDone)
            is OnDateChanged -> updateDate(viewAction.newDate)
            TaskDetailAction.OnGoBack -> navigator.navigateTo(GoBack)
        }
    }

    private fun updateDate(newDate: Long) {
        updateViewState {
            copy(date = millisToDate(newDate))
        }
    }

    private fun updateTaskState(done: Boolean) {
        updateViewState {
            copy(task = selectedTask.copy(isDone = done))
        }
    }

    private fun processUpdateTask(name: String, descriptor: String, done: Boolean, date: Long?, priority: Int) {
        val newDate = if (date != null) createDateFromMillis(date) else selectedTask.date
        val task = selectedTask.copy(
            name = name, description = descriptor, isDone = done,
            date = newDate, priority = priority
        )
        if (task.isTaskValid()) {
            updateTask(task)
            navigator.navigateTo(GoBack)
        } else {
            updateViewState {
                currentViewState().copy(sendButtonEnabled = true)
            }
        }
    }


    private fun checkSendButtonStatus(newText: String) {
        currentViewState().apply {
            updateViewState { this.copy(sendButtonEnabled = newText.isNotEmpty()) }
        }
    }

    private fun updateTask(task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task)
            sendCoordinatorEvent(DetailEvent.GoBack)
        }
    }
}