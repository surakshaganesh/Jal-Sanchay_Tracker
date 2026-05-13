package com.jalsanchay.tracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jalsanchay.tracker.ui.screens.MainShell
import com.jalsanchay.tracker.ui.screens.SetupScreen
import com.jalsanchay.tracker.ui.screens.WelcomeScreen
import com.jalsanchay.tracker.viewmodel.JalSanchayViewModel

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Setup   : Screen("setup")
    object Main    : Screen("main")   // hosts bottom nav
}

@Composable
fun JalSanchayNavGraph(
    navController: NavHostController,
    viewModel: JalSanchayViewModel
) {
    val setup by viewModel.setup.collectAsState()
    val start = if (setup.isComplete) Screen.Main.route else Screen.Welcome.route

    NavHost(navController = navController, startDestination = start) {

        composable(Screen.Welcome.route) {
            WelcomeScreen(onGetStarted = {
                navController.navigate(Screen.Setup.route)
            })
        }

        composable(Screen.Setup.route) {
            SetupScreen(
                viewModel = viewModel,
                onCalculate = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Main.route) {
            MainShell(
                viewModel = viewModel,
                onEditInputs = {
                    navController.navigate(Screen.Setup.route)
                }
            )
        }
    }
}