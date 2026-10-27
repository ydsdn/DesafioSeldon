package com.example.desafioseldon.domain.model

data class Device(
    val id: String,
    val model: String = "",
    val soc: String = "",
    val apiLevel: Int = 0,
    val batteryCapacityMah: Int = 0,
    val energyConsumedMah: Double = 0.0,
    val screenOnMinutes: Int = 0,
    val droppedFramesPercentage: Double = 0.0,
    val failures7d: Int = 0,
    val lastContactUtc: String = "",
    val relativeDrainPercentagePerHour: Double = 0.0,
    val absoluteDrainMahPerHour: Double = 0.0,
    val justification: String = ""
)