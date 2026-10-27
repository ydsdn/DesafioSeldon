package com.example.desafioseldon.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.desafioseldon.domain.model.ClassifiedDevice
import com.example.desafioseldon.domain.model.DeviceStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    state: ListUiState,
    onFilterSelected: (DeviceStatus?) -> Unit,
    onDeviceClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Frota", style = MaterialTheme.typography.titleLarge)
                        Text(
                            "${state.countAll} DISPOSITIVOS · LIDOS DE ASSETS",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = state.selectedFilter == null,
                        onClick = { onFilterSelected(null) },
                        label = { Text("TODOS ${state.countAll}") }
                    )
                }
                item {
                    FilterChip(
                        selected = state.selectedFilter == DeviceStatus.OK,
                        onClick = { onFilterSelected(DeviceStatus.OK) },
                        label = { Text("OK ${state.countOk}") }
                    )
                }
                item {
                    FilterChip(
                        selected = state.selectedFilter == DeviceStatus.ATENCAO,
                        onClick = { onFilterSelected(DeviceStatus.ATENCAO) },
                        label = { Text("ATENÇÃO ${state.countAtencao}") }
                    )
                }
                item {
                    FilterChip(
                        selected = state.selectedFilter == DeviceStatus.CRITICO,
                        onClick = { onFilterSelected(DeviceStatus.CRITICO) },
                        label = { Text("CRÍTICO ${state.countCritico}") }
                    )
                }
                item {
                    FilterChip(
                        selected = state.selectedFilter == DeviceStatus.INDETERMINADO,
                        onClick = { onFilterSelected(DeviceStatus.INDETERMINADO) },
                        label = { Text("SEM DADOS ${state.countSemDados}") }
                    )
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.filteredDevices, key = { it.device.id }) { item ->
                    DeviceRowCard(
                        device = item,
                        onClick = { onDeviceClick(item.device.id) },
                        onFavoriteClick = { onFavoriteClick(item.device.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun DeviceRowCard(
    device: ClassifiedDevice,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(device.device.id, fontWeight = FontWeight.Bold)
                Text(
                    "${device.device.model} · ${device.device.soc}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                StatusChip(status = device.status)

                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (device.isFavorite) Icons.Default.Star else Icons.Outlined.Star,
                        contentDescription = "Favorito",
                        tint = if (device.isFavorite) Color(0xFFFFB300) else Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun StatusChip(status: DeviceStatus) {
    val (text, bgColor, textColor) = when (status) {
        DeviceStatus.OK -> Triple("OK", Color(0xFFE8F5E9), Color(0xFF2E7D32))
        DeviceStatus.ATENCAO -> Triple("Atenção", Color(0xFFFFF8E1), Color(0xFFF57F17))
        DeviceStatus.CRITICO -> Triple("▲ Crítico", Color(0xFFFFEBEE), Color(0xFFC62828))
        DeviceStatus.INDETERMINADO -> Triple("Sem dados", Color(0xFFEEEEEE), Color(0xFF616161))
    }

    Surface(
        color = bgColor,
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}