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
        VE : ViewEvent,
        > : ViewModel() {

    // Using stateFlow instead of LiveData with initial state
    private val _initialState: VS by lazy { createInitialState() }
    private val _viewState = MutableStateFlow(_initialState)
    val viewState = _viewState.asStateFlow()

    // View actions as sharedFlow to broadcast state changes to an unknown number of subscribers
    // Events should processed immediately or not at all.
    private val _viewAction: MutableSharedFlow<VA> = MutableSharedFlow()
    val viewAction = _viewAction.asSharedFlow()

    // View effect to deliver events to a single subscriber, replacement for SingleLiveEvent
    // Usage for displaying toast, and navigation
    private val _viewEvent: Channel<VE> = Channel()
    val viewEvent = _viewEvent.receiveAsFlow()

    // Navigation coordinators to deliver coordinator events to a single subscriber
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

    protected fun updateViewEvent(event: VE) {
        viewModelScope.launch { _viewEvent.send(event) }
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