package com.hicham.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hicham.navigation.NavigationItem.Settings
import com.hicham.settings.ui.SettingsScreen


fun NavGraphBuilder.addSettingsNavGraph(){
    composable(Settings.route) {
        SettingsScreen()
    }
}