package com.example.habits

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        NavDestination.Home,
        NavDestination.AddHabits,
        NavDestination.Profile
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach {screen ->
            NavigationBarItem(
                icon = {Icon(imageVector = screen.icon , contentDescription = null)},
                label = {Text(screen.title)},
                selected = currentDestination?.route == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

sealed class NavDestination(val title:String , val route: String , val icon:ImageVector) {
    object Home: NavDestination(title = "Home" , route = "home" , icon= Icons.Filled.Home)
    object AddHabits: NavDestination(title = "AddHabits" , "addTask" , icon = Icons.Filled.Add)
    object Profile: NavDestination(title= "Profile" , route = "profile" , icon= Icons.Filled.Person)
}