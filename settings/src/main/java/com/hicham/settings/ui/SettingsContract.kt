package com.hicham.settings.ui

import com.hicham.core.model.ViewAction
import com.hicham.core.model.ViewEvent
import com.hicham.core.model.ViewState

data class SettingsUiState(val isDarkModeEnabled: Boolean = true) : ViewState

sealed interface SettingsAction : ViewAction {
    data class OnDarkModeCheckChange(val isChecked: Boolean) : SettingsAction
}

sealed interface SettingsEvent : ViewEvent