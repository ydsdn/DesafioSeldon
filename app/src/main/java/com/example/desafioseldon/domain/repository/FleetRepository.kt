package com.example.desafioseldon.domain.repository

import com.example.desafioseldon.domain.model.Device
import kotlinx.coroutines.flow.Flow

interface FleetRepository {
    fun getDevices(): List<Device>
    fun getFavoriteIds(): Flow<Set<String>>
    suspend fun toggleFavorite(deviceId: String)
}