package com.example.desafioseldon.data.mapper

import com.example.desafioseldon.data.model.DeviceDto
import com.example.desafioseldon.domain.model.Device

fun DeviceDto.toDomain(): Device? {
    val deviceId = id ?: return null

    // Tratamento da capacidade da bateria (mWh para mAh se necessário)
    val finalBatteryMah = when {
        batteryCapacityMah != null && batteryCapacityMah > 0 -> batteryCapacityMah
        batteryCapacityMwh != null && nominalVoltage != null && nominalVoltage > 0.0 -> {
            ((batteryCapacityMwh.toDouble() / nominalVoltage) * 1000.0).toInt()
        }
        else -> 0
    }

    val screenOnMins = screenOnMinutes ?: 0
    val consumedMah = energyConsumedMah ?: 0.0


    val absoluteDrain = if (screenOnMins > 0) {
        (consumedMah / screenOnMins) * 60.0
    } else {
        0.0
    }


    val relativeDrain = if (finalBatteryMah > 0) {
        (absoluteDrain / finalBatteryMah.toDouble()) * 100.0
    } else {
        0.0
    }

    // Justificativa para estados sem consumo ou sem dados de bateria
    val justificationText = when {
        finalBatteryMah <= 0 -> "Capacidade de bateria não informada ou inválida."
        consumedMah <= 0.0 || screenOnMins <= 0 -> "Sem dados de consumo de energia ou tempo de tela zerado."
        else -> "Dados operacionais consolidados com sucesso."
    }

        id = deviceId,
    model = model ?: "Desconhecido",
    soc = soc ?: "N/A",
    apiLevel = apiLevel ?: 0,
    return Device(
        batteryCapacityMah = finalBatteryMah,
        energyConsumedMah = consumedMah,
        screenOnMinutes = screenOnMins,
        droppedFramesPercentage = droppedFramesPercentage ?: 0.0,
        failures7d = failures7d ?: 0,
        lastContactUtc = lastContactUtc ?: "",
        relativeDrainPercentagePerHour = relativeDrain,
        absoluteDrainMahPerHour = absoluteDrain,
        justification = justificationText
    )
}