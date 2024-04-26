package com.hicham.home.ui

import androidx.lifecycle.viewModelScope
import com.hicham.core.ui.BaseViewModel
import com.hicham.home.domain.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(private val getTasksUseCase: GetTasksUseCase) : BaseViewModel<HomeScreenState, HomeAction, HomeEvent>() {



    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            val tasks = getTasksUseCase.invoke(Unit)
            updateViewState {
                currentViewState().copy(taskList = tasks)
            }
        }
    }

    override fun createInitialState(): HomeScreenState {
        return HomeScreenState()
    }

    override fun processViewActions(viewEvent: HomeAction) {
        when (viewEvent) {
            is HomeAction.OnTaskCheckChanged -> TODO()
        }
    }
}