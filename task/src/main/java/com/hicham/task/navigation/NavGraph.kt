package com.hicham.task.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hicham.navigation.NavigationItem.AddTask
import com.hicham.navigation.NavigationItem.UpdateTask
import com.hicham.task.addtask.ui.AddTaskScreen
import com.hicham.task.taskdetails.ui.TaskDetailsScreen


fun NavGraphBuilder.addTaskNavGraph(){
    composable(AddTask.route) {
        AddTaskScreen()
    }
    composable(
        UpdateTask.route,
    ) {
        TaskDetailsScreen()
    }

}