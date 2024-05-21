package com.hicham.home.ui

import androidx.lifecycle.viewModelScope
import com.hicham.core.ui.BaseViewModel
import com.hicham.core.utils.getTodayStartOfDayMillis
import com.hicham.data.persistence.model.Task
import com.hicham.home.ui.HomeAction.OnTaskCheckChanged
import com.hicham.home.ui.HomeAction.OnTaskFavoriteClicked
import com.hicham.home.ui.HomeAction.OnTaskSelected
import com.hicham.navigation.NavigationItem
import com.hicham.navigation.Navigator
import com.hicham.navigation.Screen
import com.hicham.shared.domain.usecase.GetTaskByDateUseCase
import com.hicham.shared.domain.usecase.SetSelectedTaskUseCase
import com.hicham.shared.domain.usecase.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTaskByDateUseCase: GetTaskByDateUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val setSelectedTaskUseCase: SetSelectedTaskUseCase,
    private val navigator: Navigator
) : BaseViewModel<HomeScreenState, HomeAction, HomeEvent>() {



    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            getTaskByDateUseCase(getTodayStartOfDayMillis()).collect {
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