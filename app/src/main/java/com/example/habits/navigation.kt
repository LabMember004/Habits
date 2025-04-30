package com.example.habits

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val taskViewModel: TaskViewModel = viewModel()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->



    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.padding(innerPadding)

    ) {
        composable(route = Screens.Home.route) {
            Home(
                navController = navController,
                onNavigateToAddTask = { navController.navigate(Screens.addTask.route) },
                viewModel = taskViewModel
            )

        }
        composable(route = Screens.addTask.route) {

        AddHabits(navController = navController , onNavigateToHome = {navController.navigate(Screens.Home.route)} , viewModel = taskViewModel)
        }
        composable(route = Screens.profile.route) {
            Profile()
        }
    }
}
}