package com.example.desafioseldon.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafioseldon.domain.model.ClassifiedDevice
import com.example.desafioseldon.domain.model.DeviceStatus
import com.example.desafioseldon.domain.repository.FleetRepository
import com.example.desafioseldon.domain.usecase.ClassifyDeviceUseCase
import com.example.desafioseldon.presentation.list.ListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class FleetViewModel(
    private val repository: FleetRepository,
    private val classifyUseCase: ClassifyDeviceUseCase = ClassifyDeviceUseCase()
) : ViewModel() {

    private val _selectedFilter = MutableStateFlow<DeviceStatus?>(null)
    private val _rawDevices = MutableStateFlow(repository.getDevices())

    private val _uiState = MutableStateFlow(ListUiState())
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(_rawDevices, repository.getFavoriteIds(), _selectedFilter) { devices, favorites, filter ->
                val classified = devices.map { device ->
                    val isFav = favorites.contains(device.id)
                    classifyUseCase(device = device, isFavorite = isFav)
                }

                val filtered = if (filter == null) {
                    classified
                } else {
                    classified.filter { it.status == filter }
                }

                ListUiState(
                    allDevices = classified,
                    filteredDevices = filtered,
                    selectedFilter = filter,
                    countAll = classified.size,
                    countOk = classified.count { it.status == DeviceStatus.OK },
                    countAtencao = classified.count { it.status == DeviceStatus.ATENCAO },
                    countCritico = classified.count { it.status == DeviceStatus.CRITICO },
                    countSemDados = classified.count { it.status == DeviceStatus.INDETERMINADO }
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }

    fun onFilterSelected(status: DeviceStatus?) {
        _selectedFilter.value = status
    }

    fun onFavoriteToggle(deviceId: String) {
        viewModelScope.launch {
            repository.toggleFavorite(deviceId)
        }
    }
}