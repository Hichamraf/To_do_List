package com.hicham.task.addtask.ui

import androidx.lifecycle.viewModelScope
import com.hicham.core.ui.BaseViewModel
import com.hicham.core.utils.createDateFromMillis
import com.hicham.core.utils.millisToDate
import com.hicham.data.persistence.model.Task
import com.hicham.task.addtask.domain.usecase.SaveTaskUseCase
import com.hicham.task.addtask.ui.AddTaskAction.OnAddTask
import com.hicham.task.addtask.ui.AddTaskAction.OnDateChanged
import com.hicham.task.addtask.ui.AddTaskAction.OnNameTextChanged
import com.hicham.task.addtask.ui.NavigationEvent.OnTaskSaved
import com.hicham.task.utils.isTaskValid
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AddTaskViewModel @Inject constructor(private val saveTaskUseCase: SaveTaskUseCase) : BaseViewModel<AddTaskState, AddTaskAction, AddTaskEvent>() {
    override fun createInitialState(): AddTaskState {
        return AddTaskState()
    }

    override fun processViewActions(viewAction: AddTaskAction) {
        when (viewAction) {
            is OnAddTask -> processAddTask(viewAction)
            OnNameTextChanged -> checkNameError()
            is OnDateChanged -> updateDateButtonName(viewAction.newDate)
        }
    }

    private fun updateDateButtonName(newDate: Long) {
        updateViewState {
            copy(dateButtonName = millisToDate(newDate))
        }
    }

    private fun checkNameError() {
        currentViewState().apply {
            if (nameError)
                updateViewState { this.copy(nameError = false) }
        }
    }

    private fun processAddTask(viewAction: OnAddTask) {
        val date = createDateFromMillis(viewAction.date)
        val task = Task(name = viewAction.name, description = viewAction.description, date = date, priority = viewAction.priority)
        if (task.isTaskValid()) {
            saveTask(task)
        } else {
            updateViewState {
                currentViewState().copy(nameError = true)
            }
        }
    }

    private fun saveTask(task: Task) {
        viewModelScope.launch {
            saveTaskUseCase(task)
            sendCoordinatorEvent(OnTaskSaved)
        }
    }


}