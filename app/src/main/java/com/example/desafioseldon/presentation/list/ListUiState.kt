package com.example.desafioseldon.presentation.list

import com.example.desafioseldon.domain.model.ClassifiedDevice
import com.example.desafioseldon.domain.model.DeviceStatus

data class ListUiState(
    val allDevices: List<ClassifiedDevice> = emptyList(),
    val filteredDevices: List<ClassifiedDevice> = emptyList(),
    val selectedFilter: DeviceStatus? = null,
    val countAll: Int = 0,
    val countOk: Int = 0,
    val countAtencao: Int = 0,
    val countCritico: Int = 0,
    val countSemDados: Int = 0
)