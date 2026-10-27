package com.example.desafioseldon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.desafioseldon.data.local.AssetDeviceDataSource
import com.example.desafioseldon.data.local.FavoritesDataStore
import com.example.desafioseldon.data.repository.FleetRepositoryImpl
import com.example.desafioseldon.presentation.FleetViewModel
import com.example.desafioseldon.presentation.navigation.NavGraph
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val assetDataSource = AssetDeviceDataSource(applicationContext)
        val favoritesDataStore = FavoritesDataStore(applicationContext)
        val repository = FleetRepositoryImpl(assetDataSource, favoritesDataStore)
        val viewModel = FleetViewModel(repository)

        setContent{
            val navController = rememberNavController()
            NavGraph(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}