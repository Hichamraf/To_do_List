package com.hicham.inbox.ui

import com.hicham.core.model.ViewAction
import com.hicham.core.model.ViewEvent
import com.hicham.core.model.ViewState
import com.hicham.data.persistence.model.Task


data class InBoxUiState(val tasks : List<Task> = emptyList()) : ViewState

sealed interface InBoxAction : ViewAction{
    data class OnTaskCheckChanged(val isChecked: Boolean, val task: Task) : InBoxAction
    data class OnTaskFavoriteClicked(val isFavourite : Boolean, val task: Task) : InBoxAction
    class OnTaskSelected(val task: Task) : InBoxAction
}

sealed interface InboxEvent : ViewEvent