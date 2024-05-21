package com.hicham.todolist.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.hicham.favorite.ui.FavoriteScreen
import com.hicham.home.ui.HomeScreen
import com.hicham.inbox.ui.InboxScreen
import com.hicham.settings.ui.SettingsScreen
import com.hicham.task.addtask.ui.AddTaskScreen
import com.hicham.task.taskdetails.ui.TaskDetailsScreen
import com.hicham.navigation.NavigationItem
import com.hicham.navigation.Screen

/*

@Composable
fun AppNavHost(navController: NavHostController, startDestination: String = com.hicham.navigation.NavigationItem.Home.route) {

    androidx.navigation.compose.NavHost(navController = navController, startDestination = startDestination) {

        composable(com.hicham.navigation.NavigationItem.Home.route) {
            HomeScreen(
                onItemClicked = {
                    navController.navigate(
                        com.hicham.navigation.NavigationItem.UpdateTask.route
                    )
                })
        }
        composable(com.hicham.navigation.NavigationItem.AddTask.route) {
            AddTaskScreen {
                navController.popBackStack()
            }
        }
        composable(
            com.hicham.navigation.NavigationItem.UpdateTask.route,
        ) {
            TaskDetailsScreen {
                navController.popBackStack()
            }
        }

        composable(com.hicham.navigation.NavigationItem.Inbox.route) {
            InboxScreen{
                navController.navigate(com.hicham.navigation.Screen.UPDATE_TASK.name)
            }
        }

        composable(com.hicham.navigation.NavigationItem.Favorite.route) {
            FavoriteScreen{
                navController.navigate(com.hicham.navigation.Screen.UPDATE_TASK.name)
            }
        }

        composable(com.hicham.navigation.NavigationItem.Settings.route) {
            SettingsScreen()
        }

    }

}*/
