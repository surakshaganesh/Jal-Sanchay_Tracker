package com.jalsanchay.tracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jalsanchay.tracker.model.AlertLevel
import com.jalsanchay.tracker.ui.components.GradientBackground
import com.jalsanchay.tracker.ui.components.JalTopBar
import com.jalsanchay.tracker.ui.theme.WaterDeep
import com.jalsanchay.tracker.viewmodel.JalSanchayViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AlertsScreen(viewModel: JalSanchayViewModel, onBack: () -> Unit) {
    val tank        by viewModel.tank.collectAsState()
    val alertHistory by viewModel.alertHistory.collectAsState()
    val currentAlert = tank.alertLevel
    val currentColor = Color(android.graphics.Color.parseColor(currentAlert.colorHex))

    Scaffold(topBar = { JalTopBar("Alerts & Notifications", onBack = onBack) }) { padding ->
        GradientBackground {
            Column(Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Spacer(Modifier.height(8.dp))

                // Current status hero
                Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = currentColor.copy(.13f))) {
                    Column(Modifier.padding(20.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(currentAlert.emoji, fontSize = 48.sp)
                        Spacer(Modifier.height(4.dp))
                        Text("Current: ${currentAlert.label}", fontWeight = FontWeight.Bold,
                            fontSize = 20.sp, color = currentColor)
                        Text("Tank at %.0f%%".format(tank.fillPercent), fontSize = 13.sp, color = Color.Gray)
                        Spacer(Modifier.height(8.dp))
                        Text(currentAlert.message, fontSize = 13.sp, color = WaterDeep, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                        Spacer(Modifier.height(8.dp))
                        Card(colors = CardDefaults.cardColors(containerColor = currentColor.copy(.1f)),
                            shape = RoundedCornerShape(8.dp)) {
                            Text("💡 ${currentAlert.action}", Modifier.padding(10.dp),
                                fontSize = 12.sp, color = currentColor)
                        }
                    }
                }

                // All 4 levels
                Text("All Alert Levels", fontWeight = FontWeight.Bold, color = WaterDeep, fontSize = 15.sp)
                AlertLevel.entries.forEach { lvl ->
                    val c = Color(android.graphics.Color.parseColor(lvl.colorHex))
                    val active = lvl == currentAlert
                    Card(Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = if (active) c.copy(.1f) else Color.White),
                        shape = RoundedCornerShape(14.dp),
                        elevation = CardDefaults.cardElevation(if (active) 4.dp else 1.dp)) {
                        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(lvl.emoji, fontSize = 26.sp)
                            Spacer(Modifier.width(10.dp))
                            Column(Modifier.weight(1f)) {
                                Row(horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()) {
                                    Text(lvl.label, fontWeight = FontWeight.Bold, color = c, fontSize = 14.sp)
                                    Text(lvl.threshold, color = Color.Gray, fontSize = 11.sp)
                                }
                                Text(lvl.message, fontSize = 11.sp, color = Color.DarkGray)
                            }
                            if (active) {
                                Spacer(Modifier.width(6.dp))
                                Card(colors = CardDefaults.cardColors(containerColor = c),
                                    shape = RoundedCornerShape(6.dp)) {
                                    Text("ACTIVE", Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                // Alert history from Room
                if (alertHistory.isNotEmpty()) {
                    Text("Recent Alert History", fontWeight = FontWeight.Bold, color = WaterDeep, fontSize = 15.sp)
                    val sdf = remember { SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault()) }
                    alertHistory.take(10).forEach { hist ->
                        val lvl = runCatching { AlertLevel.valueOf(hist.alertLevel) }.getOrDefault(AlertLevel.NORMAL)
                        val c = Color(android.graphics.Color.parseColor(lvl.colorHex))
                        Card(Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(10.dp)) {
                            Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(lvl.emoji, fontSize = 20.sp)
                                Spacer(Modifier.width(8.dp))
                                Column(Modifier.weight(1f)) {
                                    Text(lvl.label, fontWeight = FontWeight.SemiBold, color = c, fontSize = 12.sp)
                                    Text(hist.message, fontSize = 11.sp, color = Color.Gray)
                                }
                                Text(sdf.format(Date(hist.timestamp)), fontSize = 10.sp, color = Color.LightGray)
                            }
                        }
                    }
                } else {
                    Card(Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                        shape = RoundedCornerShape(12.dp)) {
                        Column(Modifier.padding(20.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("📋", fontSize = 32.sp)
                            Text("No alerts logged yet", color = Color.Gray, fontSize = 13.sp)
                            Text("Alerts are saved when tank reaches 50%, 80%, or 100%.", color = Color.Gray, fontSize = 11.sp)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}
