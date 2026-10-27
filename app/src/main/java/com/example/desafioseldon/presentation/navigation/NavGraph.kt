package com.example.desafioseldon.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.desafioseldon.presentation.FleetViewModel
import com.example.desafioseldon.presentation.detail.DetailScreen
import com.example.desafioseldon.presentation.list.ListScreen

sealed class Screen(val route: String) {
    object List : Screen("list")
    object Detail : Screen("detail/{deviceId}") {
        fun createRoute(deviceId: String) = "detail/$deviceId"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: FleetViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.List.route
    ) {
        composable(Screen.List.route) {
            ListScreen(
                state = uiState,
                onFilterSelected = { filter -> viewModel.onFilterSelected(filter) },
                onDeviceClick = { deviceId ->
                    navController.navigate(Screen.Detail.createRoute(deviceId))
                },
                onFavoriteClick = { deviceId -> viewModel.onFavoriteToggle(deviceId) }
            )
        }

        composable(Screen.Detail.route) { backStackEntry ->
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            val device = uiState.allDevices.find { it.device.id == deviceId }

            DetailScreen(
                device = device,
                onBackClick = { navController.popBackStack() },
                onFavoriteClick = { id -> viewModel.onFavoriteToggle(id) }
            )
        }
    }
}