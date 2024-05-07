package com.hicham.task.addtask.ui

import com.hicham.core.model.Event
import com.hicham.core.model.ViewAction
import com.hicham.core.model.ViewEvent
import com.hicham.core.model.ViewState


data class AddTaskState(val nameError: Boolean = false,val dateButtonName: String? = null) : ViewState

sealed interface AddTaskAction : ViewAction {
    data class OnAddTask(
        val name: String, val description: String, val date: Long?,
        val priority: Int
    ) : AddTaskAction
    data class OnDateChanged(val newDate : Long):AddTaskAction
    data object OnNameTextChanged : AddTaskAction
}

sealed interface AddTaskEvent : ViewEvent

sealed interface NavigationEvent : Event {
    data object OnTaskSaved : NavigationEvent
}