package com.hicham.todolist

import android.content.Context
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hicham.core.R
import com.hicham.core.theme.ToDoListTheme
import com.hicham.home.ui.TabBarIconView
import com.hicham.home.ui.TabBarItem
import com.hicham.todolist.model.Screen
import com.hicham.todolist.ui.AppNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        TabView(tabBarItems = getTabBarItems(LocalContext.current), navController)
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            shape = CircleShape,
                            onClick = { navController.navigate(Screen.ADD_TASK.name) }) {
                            Icon(painter = painterResource(com.hicham.home.R.drawable.baseline_add_24), null)
                        }
                    }
                ) { _ ->

                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        AppNavHost(navController = navController)
                    }
                }

            }
        }
    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        return super.getOnBackInvokedDispatcher()
    }
}


private fun getTabBarItems(context: Context): List<TabBarItem> {
    val homeTab = TabBarItem(
        title = context.resources.getString(R.string.bottom_bar_tome),
        selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home
    )
    val inboxTab = TabBarItem(
        title = context.resources.getString(R.string.bottom_bar_inbox),
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.MailOutline,
    )
    val favoriteTab = TabBarItem(
        title = context.resources.getString(R.string.bottom_bar_favorite),
        selectedIcon = Icons.Filled.Favorite, unselectedIcon = Icons.Default.FavoriteBorder
    )
    val settingsTab = TabBarItem(
        title = context.resources.getString(R.string.bottom_bar_settings),
        selectedIcon = Icons.Filled.Settings, unselectedIcon = Icons.Outlined.Settings
    )
    return listOf(homeTab, inboxTab, favoriteTab, settingsTab)
}


@Composable
fun TabView(tabBarItems: List<TabBarItem>, navController: NavController) {
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    when (selectedTabIndex) {
                        0 -> {
                            navController.navigate(Screen.HOME.name)
                        }

                        1 -> {
                            navController.navigate(Screen.INBOX.name)
                        }

                        2 -> {
                            navController.navigate(Screen.FAVORITE.name)
                        }

                        3 -> {
                            navController.navigate(Screen.SETTINGS.name)
                        }
                    }
                },
                icon = {
                    TabBarIconView(
                        isSelected = selectedTabIndex == index,
                        selectedIcon = tabBarItem.selectedIcon,
                        unselectedIcon = tabBarItem.unselectedIcon,
                        title = tabBarItem.title,
                        badgeAmount = tabBarItem.badgeAmount
                    )
                },
                label = { Text(tabBarItem.title) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoListTheme {

    }
}