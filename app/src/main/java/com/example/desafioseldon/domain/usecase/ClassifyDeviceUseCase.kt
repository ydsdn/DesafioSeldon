package com.example.desafioseldon.domain.usecase

import com.example.desafioseldon.domain.model.ClassifiedDevice
import com.example.desafioseldon.domain.model.Device
import com.example.desafioseldon.domain.model.DeviceStatus

class ClassifyDeviceUseCase {
    operator fun invoke(device: Device, isFavorite: Boolean): ClassifiedDevice {
        val status = when {
            device.energyConsumedMah <= 0.0 || device.batteryCapacityMah <= 0 -> DeviceStatus.INDETERMINADO
            device.failures7d >= 3 || device.droppedFramesPercentage >= 15.0 -> DeviceStatus.CRITICO
            device.droppedFramesPercentage >= 5.0 || device.failures7d in 1..2 -> DeviceStatus.ATENCAO
            else -> DeviceStatus.OK
        }
        return ClassifiedDevice(
            device = device,
            status = status,
            isFavorite = isFavorite,
            relativeDrainPercentagePerHour = TODO(),
            absoluteDrainMahPerHour = TODO(),
            justification = TODO()
        )
    }
}