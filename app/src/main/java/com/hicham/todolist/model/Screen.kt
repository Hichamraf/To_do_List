package com.hicham.todolist.model

enum class Screen {
    HOME,
    ADD_TASK,
    UPDATE_TASK,
}

sealed class NavigationItem(val route: String) {
    data object AddTask : NavigationItem(Screen.ADD_TASK.name)
    data object Home : NavigationItem(Screen.HOME.name)

    data object UpdateTask : NavigationItem(Screen.UPDATE_TASK.name)
}