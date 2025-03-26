package com.example.habits

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val taskViewModel: TaskViewModel = viewModel()



    NavHost(
        navController = navController,
        startDestination = "home"

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
    }
}