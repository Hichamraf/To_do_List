package com.hicham.task.taskdetails.ui

import com.hicham.core.model.Event
import com.hicham.core.model.ViewAction
import com.hicham.core.model.ViewEvent
import com.hicham.core.model.ViewState
import com.hicham.data.persistence.model.Task


data class TaskDetailsUiState(val task: Task? = null, val nameError: Boolean = false) : ViewState

sealed interface TaskDetailAction : ViewAction {
    data object OnNameTextChanged : TaskDetailAction

    data class UpdateTask(val name: String, val descriptor: String, val isDone: Boolean) : TaskDetailAction
}

sealed interface TaskDetailsEvent : ViewEvent

sealed interface DetailEvent : Event {
    data object GoBack : DetailEvent
}