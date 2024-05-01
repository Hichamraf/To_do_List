package com.hicham.task.taskdetails.ui

import androidx.lifecycle.viewModelScope
import com.hicham.core.ui.BaseViewModel
import com.hicham.data.persistence.model.Task
import com.hicham.task.taskdetails.domain.usecase.GetSelectedTaskUseCase
import com.hicham.task.taskdetails.domain.usecase.UpdateTaskUseCase
import com.hicham.task.utils.isTaskValid
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val getSelectedTaskUseCase: GetSelectedTaskUseCase,
) : BaseViewModel<TaskDetailsUiState, TaskDetailAction, TaskDetailsEvent>() {

    private lateinit var selectedTask: Task
    init {
        viewModelScope.launch {
             selectedTask = getSelectedTaskUseCase(Unit)
            updateViewState {
                currentViewState().copy(task = selectedTask)
            }
        }
    }
    override fun createInitialState(): TaskDetailsUiState {
        return TaskDetailsUiState()
    }

    override fun processViewActions(viewAction: TaskDetailAction) {
        when (viewAction) {
            is TaskDetailAction.UpdateTask -> processUpdateTask(viewAction.name, viewAction.descriptor, viewAction.isDone)
            TaskDetailAction.OnNameTextChanged -> checkNameError()
        }
    }

    private fun processUpdateTask(name: String, descriptor: String, done: Boolean) {
        val task = selectedTask.copy(name = name, description = descriptor, isDone = done)
        if (task.isTaskValid()) {
            updateTask(task)
        } else {
            updateViewState {
                currentViewState().copy(nameError = true)
            }
        }
    }

    private fun checkNameError() {
        currentViewState().apply {
            if (nameError)
                updateViewState { this.copy(nameError = false) }
        }
    }

    private fun updateTask(task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task)
            sendCoordinatorEvent(DetailEvent.GoBack)
        }
    }
}