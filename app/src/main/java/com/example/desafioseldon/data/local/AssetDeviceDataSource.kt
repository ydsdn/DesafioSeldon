package com.example.desafioseldon.data.local

import android.content.Context
import com.example.desafioseldon.data.mapper.toDomain
import com.example.desafioseldon.data.model.FleetJsonResponse
import com.example.desafioseldon.domain.model.Device
import kotlinx.serialization.json.Json
import com.example.desafioseldon.data.mapper.toDomain

class AssetDeviceDataSource(private val context: Context) {
    private val json = Json { ignoreUnknownKeys = true }

    fun readDevicesFromAssets(): List<Device> {
        return runCatching {
            val jsonString = context.assets.open("frota.json").bufferedReader().use { it.readText() }
            val response = json.decodeFromString<FleetJsonResponse>(jsonString)
            response.devices.mapNotNull { it.toDomain() }
        }.getOrDefault(emptyList())
    }
}