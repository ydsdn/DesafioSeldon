package com.example.desafioseldon.data.repository

import com.example.desafioseldon.data.local.AssetDeviceDataSource
import com.example.desafioseldon.data.local.FavoritesDataStore
import com.example.desafioseldon.domain.model.Device
import com.example.desafioseldon.domain.repository.FleetRepository
import kotlinx.coroutines.flow.Flow

class FleetRepositoryImpl(
    private val assetDataSource: AssetDeviceDataSource,
    private val favoritesDataStore: FavoritesDataStore
) : FleetRepository {

    override fun getDevices(): List<Device> {
        return assetDataSource.readDevicesFromAssets()
    }

    override fun getFavoriteIds(): Flow<Set<String>> {
        return favoritesDataStore.favoriteIds
    }

    override suspend fun toggleFavorite(deviceId: String) {
        favoritesDataStore.toggleFavorite(deviceId)
    }
}