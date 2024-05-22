package com.hicham.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hicham.core.model.Event
import com.hicham.core.model.ViewAction
import com.hicham.core.model.ViewEvent
import com.hicham.core.model.ViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<
        VS : ViewState,
        VA : ViewAction,
        > : ViewModel() {

    private val _initialState: VS by lazy { createInitialState() }
    private val _viewState = MutableStateFlow(_initialState)
    val viewState = _viewState.asStateFlow()

    private val _viewAction: MutableSharedFlow<VA> = MutableSharedFlow()
    val viewAction = _viewAction.asSharedFlow()


    private val _coordinatorEvent: Channel<Event> = Channel()
    val coordinatorEvent = _coordinatorEvent.receiveAsFlow()

    abstract fun createInitialState(): VS
    abstract fun processViewActions(viewAction: VA)

    init {
        subscribeViewEvents()
    }

    fun currentViewState(): VS {
        return _viewState.value
    }

    protected fun updateViewState(newState: VS.() -> VS) {
        _viewState.value = currentViewState().newState()
    }

    fun updateViewAction(action: VA) {
        viewModelScope.launch { _viewAction.emit(action) }
    }


    private fun subscribeViewEvents() {
        viewModelScope.launch {
            _viewAction.collect { processViewActions(it) }
        }
    }

    protected fun <E : Event> sendCoordinatorEvent(event: E) {
        viewModelScope.launch { _coordinatorEvent.trySend(event) }
    }
}