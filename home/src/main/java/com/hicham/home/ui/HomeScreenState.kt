package com.hicham.home.ui

import com.hicham.core.model.ViewAction
import com.hicham.core.model.ViewEvent
import com.hicham.core.model.ViewState
import com.hicham.data.persistence.model.Task

data class HomeScreenState(val taskList: List<Task> = emptyList()) : ViewState

sealed interface HomeAction : ViewAction {
    data class OnTaskCheckChanged(val isChecked: Boolean, val task: Task) : HomeAction
    data class OnTaskFavoriteClicked(val isFavourite : Boolean, val task: Task) : HomeAction
    class OnTaskSelected(val task: Task) : HomeAction
}

sealed interface HomeEvent : ViewEvent