package com.hicham.todolist.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hicham.home.ui.HomeScreen
import com.hicham.inbox.ui.InboxScreen
import com.hicham.task.addtask.ui.AddTaskScreen
import com.hicham.task.taskdetails.ui.TaskDetailsScreen
import com.hicham.todolist.model.NavigationItem


@Composable
fun AppNavHost(navController: NavHostController, startDestination: String = NavigationItem.Home.route) {

    androidx.navigation.compose.NavHost(navController = navController, startDestination = startDestination) {

        composable(NavigationItem.Home.route) {
            HomeScreen(
                onItemClicked = {
                    navController.navigate(
                        NavigationItem.UpdateTask.route
                    )
                })
        }
        composable(NavigationItem.AddTask.route) {
            AddTaskScreen {
                navController.popBackStack()
            }
        }

        composable(
            NavigationItem.UpdateTask.route,
        ) {
            TaskDetailsScreen {
                navController.popBackStack()
            }
        }

        composable(NavigationItem.Inbox.route) {
            InboxScreen()
        }

    }

}