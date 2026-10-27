package com.example.desafioseldon.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.desafioseldon.presentation.list.StatusChip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    device: ClassifiedDevice?,
    onBackClick: () -> Unit,
    onFavoriteClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(device?.device?.id ?: "Detalhes do Dispositivo") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    device?.let { item ->
                        IconButton(onClick = { onFavoriteClick(item.device.id) }) {
                            Icon(
                                imageVector = if (item.isFavorite) Icons.Default.Star else Icons.Outlined.Star,
                                contentDescription = "Favorito",
                                tint = if (item.isFavorite) Color(0xFFFFB300) else Color.Gray
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (device == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Dispositivo não encontrado.")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = device.device.model,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            StatusChip(status = device.status)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "SoC: ${device.device.soc}", color = Color.Gray)
                    }
                }

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Métricas operacionais", fontWeight = FontWeight.Bold)
                        HorizontalDivider()
                        DetailRow("Bateria Nominal", "${device.device.batteryCapacityMah} mAh")
                        DetailRow("Consumo Estimado", "%.2f mAh".format(device.device.energyConsumedMah))
                        DetailRow("Tempo de Tela", "${device.device.screenOnMinutes} min")
                        DetailRow("Taxa de Engasgo (Jank)", "%.1f%%".format(device.device.droppedFramesPercentage))
                        DetailRow("Falhas em 7d (Crashes)", "${device.device.failures7d}")
                        DetailRow("Nível de API", "Android API ${device.device.apiLevel}")
                        DetailRow("Último Contato", device.device.lastContactUtc)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray)
        Text(value, fontWeight = FontWeight.Medium)
    }
}