package com.hicham.settings.ui

import androidx.lifecycle.viewModelScope
import com.hicham.core.ui.BaseViewModel
import com.hicham.settings.ui.SettingsAction.OnDarkModeCheckChange
import com.hicham.shared.domain.usecase.GetDarKModeUseCase
import com.hicham.shared.domain.usecase.SaveDarkModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getDarKModeUseCase: GetDarKModeUseCase,
    private val saveDarkModeUseCase: SaveDarkModeUseCase
) : BaseViewModel<SettingsUiState, SettingsAction, SettingsEvent>() {
    private val job = SupervisorJob()

    init {
        viewModelScope.launch(job) {
            val isDarkModeEnabled = getDarKModeUseCase(Unit)
            updateViewState {
                copy(isDarkModeEnabled = isDarkModeEnabled)
            }
        }
    }


    override fun createInitialState(): SettingsUiState {
        return SettingsUiState()
    }

    override fun processViewActions(viewAction: SettingsAction) {
        when (viewAction) {
            is OnDarkModeCheckChange -> processDarkModeChange(viewAction.isChecked)
        }
    }

    private fun processDarkModeChange(isChecked: Boolean) {
        updateViewState { copy(isDarkModeEnabled = isChecked) }
        viewModelScope.launch(job) { saveDarkModeUseCase(isChecked) }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}