package com.cuyelist.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cuyelist.ui.screens.home.HomeScreen
import com.cuyelist.ui.screens.recado.RecadoScreen
import com.cuyelist.ui.screens.recado.RecadoViewModel

private object Routes {
    const val HOME = "home"
    const val RECADOS = "recados"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Escopo da Activity: Home e lista de recados compartilham o mesmo estado.
    val viewModel: RecadoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                pendingCount = viewModel.recados.size - viewModel.doneCount,
                doneCount = viewModel.doneCount,
                onOpenRecados = { navController.navigate(Routes.RECADOS) },
            )
        }
        composable(Routes.RECADOS) {
            RecadoScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
            )
        }
    }
}
