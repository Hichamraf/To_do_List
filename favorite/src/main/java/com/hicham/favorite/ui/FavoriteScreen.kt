package com.hicham.favorite.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.hicham.components.TaskItem
import com.hicham.favorite.ui.FavoriteAction.OnTaskFavoriteClicked
import com.hicham.favorite.ui.FavoriteAction.OnTaskSelected

@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel = hiltViewModel()) {

    val state = viewModel.viewState.collectAsState()

    LazyColumn {
        items(state.value.tasks, key = { it.id!! }) {
            TaskItem(task = it, onItemClicked = {
                viewModel.processViewActions(OnTaskSelected(it))
            }, onChecked = { isFavorite ->
                viewModel.processViewActions(OnTaskFavoriteClicked(isFavorite, it))
            }) { isDone ->
                viewModel.processViewActions(FavoriteAction.OnTaskCheckChanged(isDone, it))
            }
        }
    }

}