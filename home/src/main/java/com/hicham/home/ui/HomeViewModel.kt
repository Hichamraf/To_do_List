package com.hicham.home.ui

import androidx.lifecycle.viewModelScope
import com.hicham.core.ui.BaseViewModel
import com.hicham.data.persistence.model.Task
import com.hicham.home.domain.usecase.GetTasksUseCase
import com.hicham.home.domain.usecase.SetSelectedTaskUseCase
import com.hicham.home.domain.usecase.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val setSelectedTaskUseCase: SetSelectedTaskUseCase
) : BaseViewModel<HomeScreenState, HomeAction, HomeEvent>() {



    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            getTasksUseCase(Unit).collect {
                updateViewState {
                    currentViewState().copy(taskList = it)
                }
            }
        }
    }

    override fun createInitialState(): HomeScreenState {
        return HomeScreenState()
    }

    override fun processViewActions(viewAction: HomeAction) {
        when (viewAction) {
            is HomeAction.OnTaskCheckChanged -> processTaskCheckChange(viewAction.isChecked, viewAction.task)
            is HomeAction.OnTaskSelected -> viewModelScope.launch { setSelectedTaskUseCase(viewAction.task) }
            is HomeAction.OnTaskFavoriteClicked -> viewModelScope.launch { updateTaskUseCase(viewAction.task.copy(isFavorite = viewAction.isFavourite)) }
        }
    }

    private fun processTaskCheckChange(isCheck: Boolean, task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task.copy(isDone = isCheck))
        }
    }
}