package com.example.desafioseldon.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FleetJsonResponse(
    val devices: List<DeviceDto> = emptyList()
)

@Serializable
data class DeviceDto(
    val id: String? = null,
    val model: String? = null,
    val soc: String? = null,
    @SerialName("api_level") val apiLevel: Int? = null,
    @SerialName("battery_capacity_mah") val batteryCapacityMah: Int? = null,
    @SerialName("battery_capacity_mwh") val batteryCapacityMwh: Int? = null,
    @SerialName("nominal_voltage") val nominalVoltage: Double? = null,
    @SerialName("energy_consumed_mah") val energyConsumedMah: Double? = null,
    @SerialName("screen_on_minutes") val screenOnMinutes: Int? = null,
    @SerialName("dropped_frames_percentage") val droppedFramesPercentage: Double? = null,
    @SerialName("failures_7d") val failures7d: Int? = null,
    @SerialName("last_contact_utc") val lastContactUtc: String? = null
)