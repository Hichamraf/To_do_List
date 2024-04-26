package com.hicham.todolist.model

enum class Screen {
    HOME,
    ADD_TASK
}

sealed class NavigationItem(val route: String) {
    data object addTask : NavigationItem(Screen.ADD_TASK.name)
    data object home : NavigationItem(Screen.HOME.name)
}