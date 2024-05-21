package com.hicham.navigation

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Singleton
class Navigator @Inject constructor() {

    private val _sharedFlow = MutableSharedFlow<NavigationItem>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun navigateTo(navTarget: NavigationItem) {
        _sharedFlow.tryEmit(navTarget)
    }
}