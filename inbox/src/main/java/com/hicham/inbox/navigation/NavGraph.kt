package com.hicham.inbox.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hicham.inbox.ui.InboxScreen


fun NavGraphBuilder.addInboxGraph(){
    composable(com.hicham.navigation.NavigationItem.Inbox.route) {
        InboxScreen()
    }
}