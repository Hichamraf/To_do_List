package com.hicham.inbox.ui

import androidx.lifecycle.viewModelScope
import com.hicham.core.ui.BaseViewModel
import com.hicham.data.persistence.model.Task
import com.hicham.inbox.domain.GetAllTasksUseCas
import com.hicham.inbox.ui.InBoxAction.OnTaskCheckChanged
import com.hicham.inbox.ui.InBoxAction.OnTaskFavoriteClicked
import com.hicham.inbox.ui.InBoxAction.OnTaskSelected
import com.hicham.navigation.NavigationItem
import com.hicham.navigation.Navigator
import com.hicham.shared.domain.usecase.SetSelectedTaskUseCase
import com.hicham.shared.domain.usecase.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class InboxViewModel @Inject constructor(
    private val getAllTasksUseCas: GetAllTasksUseCas,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val setSelectedTaskUseCase: SetSelectedTaskUseCase,
    private val navigator: Navigator
) : BaseViewModel<InBoxUiState, InBoxAction>() {

    init {
        viewModelScope.launch {
            getAllTasksUseCas(Unit).collect {
                updateViewState { copy(tasks = it) }
            }
        }
    }

    override fun createInitialState(): InBoxUiState {
        return InBoxUiState()
    }

    override fun processViewActions(viewAction: InBoxAction) {
        when (viewAction) {
            is OnTaskCheckChanged -> processTaskCheckChange(viewAction.isChecked, viewAction.task)
            is OnTaskSelected -> processTaskSelected(viewAction)
            is OnTaskFavoriteClicked -> viewModelScope.launch { updateTaskUseCase(viewAction.task.copy(isFavorite = viewAction.isFavourite)) }
        }
    }

    private fun processTaskSelected(viewAction: OnTaskSelected) {
        viewModelScope.launch { setSelectedTaskUseCase(viewAction.task) }
        navigator.navigateTo(NavigationItem.UpdateTask)
    }

    private fun processTaskCheckChange(isCheck: Boolean, task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task.copy(isDone = isCheck))
        }
    }
}