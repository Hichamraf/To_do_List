package com.hicham.favorite.ui

import androidx.lifecycle.viewModelScope
import com.hicham.core.ui.BaseViewModel
import com.hicham.data.persistence.model.Task
import com.hicham.favorite.domain.usecase.GetFavoriteTasksUseCase
import com.hicham.favorite.ui.FavoriteAction.OnTaskCheckChanged
import com.hicham.favorite.ui.FavoriteAction.OnTaskFavoriteClicked
import com.hicham.favorite.ui.FavoriteAction.OnTaskSelected
import com.hicham.navigation.NavigationItem
import com.hicham.navigation.NavigationItem.UpdateTask
import com.hicham.navigation.Navigator
import com.hicham.shared.domain.usecase.SetSelectedTaskUseCase
import com.hicham.shared.domain.usecase.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteTasksUseCase: GetFavoriteTasksUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val setSelectedTaskUseCase: SetSelectedTaskUseCase,
    private val navigator: Navigator
) : BaseViewModel<FavoriteUiState, FavoriteAction>() {
    private val job = SupervisorJob()

    init {
        viewModelScope.launch(job) {
            getFavoriteTasksUseCase(Unit).collect {
                updateViewState { copy(tasks = it) }
            }
        }
    }

    override fun createInitialState(): FavoriteUiState {
        return FavoriteUiState()
    }

    override fun processViewActions(viewAction: FavoriteAction) {
        when (viewAction) {
            is OnTaskCheckChanged -> processTaskCheckChange(viewAction.isChecked, viewAction.task)
            is OnTaskSelected -> processTaskSelected(viewAction)
            is OnTaskFavoriteClicked -> viewModelScope.launch { updateTaskUseCase(viewAction.task.copy(isFavorite = viewAction.isFavourite)) }
        }
    }

    private fun processTaskSelected(viewAction: OnTaskSelected) {
        viewModelScope.launch { setSelectedTaskUseCase(viewAction.task) }
        navigator.navigateTo(UpdateTask)
    }

    private fun processTaskCheckChange(isCheck: Boolean, task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task.copy(isDone = isCheck))
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}