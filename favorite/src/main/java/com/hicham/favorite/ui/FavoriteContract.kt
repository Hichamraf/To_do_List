package com.hicham.favorite.ui

import com.hicham.core.model.ViewAction
import com.hicham.core.model.ViewEvent
import com.hicham.core.model.ViewState
import com.hicham.data.persistence.model.Task


data class FavoriteUiState(val tasks:List<Task> = emptyList()) : ViewState

sealed interface FavoriteAction : ViewAction{
    data class OnTaskCheckChanged(val isChecked: Boolean, val task: Task) : FavoriteAction
    data class OnTaskFavoriteClicked(val isFavourite : Boolean, val task: Task) : FavoriteAction
    class OnTaskSelected(val task: Task) : FavoriteAction
}