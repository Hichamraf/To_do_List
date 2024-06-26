package com.hicham.task.addtask.ui

import com.hicham.core.model.Event
import com.hicham.core.model.ViewAction
import com.hicham.core.model.ViewEvent
import com.hicham.core.model.ViewState


data class AddTaskState(val sendButtonEnabled: Boolean = false, val dateButtonName: String? = null) : ViewState

sealed interface AddTaskAction : ViewAction {
    data class OnAddTask(
        val name: String, val description: String, val date: Long?,
        val priority: Int
    ) : AddTaskAction

    data class OnDateChanged(val newDate: Long) : AddTaskAction
    data class OnNameTextChanged(val newText: String) : AddTaskAction
}


sealed interface NavigationEvent : Event {
    data object OnTaskSaved : NavigationEvent
}