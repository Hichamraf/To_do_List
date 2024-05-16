package com.hicham.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hicham.settings.R
import com.hicham.settings.ui.SettingsAction.OnDarkModeCheckChange

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val state = viewModel.viewState.collectAsState()
    Column(modifier = Modifier.fillMaxSize().padding(32.dp)) {
        Row {
            Text(stringResource(R.string.settings_theme))
            Spacer(modifier = Modifier.weight(1f))
            Switch(checked = state.value.isDarkModeEnabled, onCheckedChange = {
                viewModel.processViewActions(OnDarkModeCheckChange(it))
            })
        }
    }
}