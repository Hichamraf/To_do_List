package com.hicham.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hicham.components.TaskItem
import com.hicham.home.ui.HomeAction.OnTaskCheckChanged


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onItemClicked: () -> Unit
) {

    val state by viewModel.viewState.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        LazyColumn {
            items(state.taskList, key = { it.id!! }) {
                TaskItem(it, onItemClicked = {
                    viewModel.processViewActions(HomeAction.OnTaskSelected(it))
                    onItemClicked.invoke()
                },
                    onFavoriteClicked = { isFav ->
                        viewModel.processViewActions(HomeAction.OnTaskFavoriteClicked(isFav, it))
                    },
                    onChecked = { isChecked ->
                        viewModel.processViewActions(OnTaskCheckChanged(isChecked, it))
                    })
            }
        }
    }


}


@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    title: String,
    badgeAmount: Int? = null
) {
    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            imageVector = if (isSelected) {
                selectedIcon
            } else {
                unselectedIcon
            },
            contentDescription = title
        )
    }
}

@Composable
fun TabBarBadgeView(count: Int? = null) {
    if (count != null) {
        Badge {
            Text(count.toString())
        }
    }
}

@Preview
@Composable
fun Preview() {

}
