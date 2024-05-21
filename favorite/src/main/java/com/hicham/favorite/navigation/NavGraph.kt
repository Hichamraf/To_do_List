package com.hicham.favorite.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hicham.favorite.ui.FavoriteScreen


fun NavGraphBuilder.addFavoriteGraph(){
    composable(com.hicham.navigation.NavigationItem.Favorite.route) {
        FavoriteScreen()
    }
}