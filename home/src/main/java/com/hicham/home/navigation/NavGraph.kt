package com.hicham.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hicham.home.ui.HomeScreen


fun NavGraphBuilder.addHomeGraph() {
    composable(com.hicham.navigation.NavigationItem.Home.route) {
        HomeScreen()
    }
}