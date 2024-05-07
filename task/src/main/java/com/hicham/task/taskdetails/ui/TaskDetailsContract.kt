package com.hicham.task.taskdetails.ui

import com.hicham.core.model.Event
import com.hicham.core.model.ViewAction
import com.hicham.core.model.ViewEvent
import com.hicham.core.model.ViewState
import com.hicham.data.persistence.model.Task


data class TaskDetailsUiState(val task: Task? = null, val nameError: Boolean = false, val date: String? = null) : ViewState

sealed interface TaskDetailAction : ViewAction {
    data object OnNameTextChanged : TaskDetailAction

    data class UpdateTask(
        val name: String, val descriptor: String, val isDone: Boolean,
        val date: Long?, val priority: Int
    ) : TaskDetailAction

    data class OnTaskCheckBoxChanged(val isDone: Boolean) : TaskDetailAction

    data class OnDateChanged(val newDate: Long) : TaskDetailAction
}

sealed interface TaskDetailsEvent : ViewEvent

sealed interface DetailEvent : Event {
    data object GoBack : DetailEvent
}