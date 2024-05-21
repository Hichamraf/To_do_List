package com.hicham.todolist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hicham.favorite.navigation.addFavoriteGraph
import com.hicham.home.navigation.addHomeGraph
import com.hicham.inbox.navigation.addInboxGraph
import com.hicham.navigation.NavigationItem
import com.hicham.navigation.NavigationItem.Home
import com.hicham.navigation.Navigator
import com.hicham.settings.navigation.addSettingsNavGraph
import com.hicham.task.navigation.addTaskNavGraph
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun NavigationComponent(
    navController: NavHostController,
    navigator: Navigator
) {

    val launchedEffectNavigationLabel = "navigation"

    LaunchedEffect(key1 = launchedEffectNavigationLabel) {
        navigator.sharedFlow.onEach {
            if (it == NavigationItem.GoBack)
                navController.popBackStack()
            else
                navController.navigate(it.route)


        }.launchIn(this)
    }

    NavHost(navController = navController, startDestination = Home.route) {
        addHomeGraph()
        addInboxGraph()
        addFavoriteGraph()
        addSettingsNavGraph()
        addTaskNavGraph()
    }

}