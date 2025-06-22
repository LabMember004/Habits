package com.example.habits

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Navigation() {
    val navController = rememberNavController()

    val taskViewModel: TaskViewModel = viewModel()


    val context = LocalContext.current
    val prefs = remember {OnboardingPreferences(context)}



    val isCompleted by prefs.isOnboardingCompleted.collectAsState(initial = false)

    val scope = rememberCoroutineScope()

    if (!isCompleted) {
        OnboardingScreen(
            onFinish = {
                scope.launch {
                    prefs.setOnboardingCompleted(true)
                }
            },
            prefs = prefs
        )
    } else {



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
                viewModel = taskViewModel
            )

        }
        composable(route = Screens.addTask.route) {

        AddHabits(navController = navController , onNavigateToHome = {navController.navigate(Screens.Home.route)} , viewModel = taskViewModel)
        }
        composable(route = Screens.profile.route) {
            Profile()
        }
        composable(route = "taskDetail/{taskId}" ) { backStackEntry ->
            val taskId= backStackEntry.arguments?.getString("taskId")
            val task = taskViewModel.tasks.value.find { it.id == taskId }

            if(task != null) {
                TaskDetailScreen(task = task , viewModel(), navController = navController , onNavigateBackToHome = {navController.navigate(Screens.Home.route)})

            }

        }
    }
}
}
}