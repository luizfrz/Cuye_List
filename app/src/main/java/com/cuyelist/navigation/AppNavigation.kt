package com.cuyelist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cuyelist.ui.screens.home.HomeScreen
import com.cuyelist.ui.screens.task.NewTaskScreen

private object Routes {
    const val HOME = "home"
    const val NEW_TASK = "new_task"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
    ) {
        composable(Routes.HOME) {
            HomeScreen(onCreateTask = { navController.navigate(Routes.NEW_TASK) })
        }
        composable(Routes.NEW_TASK) {
            NewTaskScreen(onBack = { navController.popBackStack() })
        }
    }
}