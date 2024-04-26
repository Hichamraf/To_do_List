package com.hicham.todolist.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.hicham.home.ui.HomeScreen
import com.hicham.task.ui.AddTaskScreen
import com.hicham.todolist.model.NavigationItem


@Composable
fun AppNavHost(navController: NavHostController, startDestination: String = NavigationItem.home.route) {

    androidx.navigation.compose.NavHost(navController = navController, startDestination = startDestination) {

        composable(NavigationItem.home.route) {
            HomeScreen {
                navController.navigate(NavigationItem.addTask.route)
            }
        }
        composable(NavigationItem.addTask.route) {
            AddTaskScreen()
        }

    }

}