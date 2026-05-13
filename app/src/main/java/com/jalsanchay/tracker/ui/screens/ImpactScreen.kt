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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jalsanchay.tracker.model.generateInsights
import com.jalsanchay.tracker.ui.components.GradientBackground
import com.jalsanchay.tracker.ui.components.JalTopBar
import com.jalsanchay.tracker.ui.theme.WaterDeep
import com.jalsanchay.tracker.ui.theme.WaterMid
import com.jalsanchay.tracker.viewmodel.JalSanchayViewModel

@Composable
fun ImpactScreen(viewModel: JalSanchayViewModel, onBack: () -> Unit) {
    val tank    by viewModel.tank.collectAsState()
    val setup   by viewModel.setup.collectAsState()
    val entries by viewModel.rainfallEntries.collectAsState()

    val liters   = tank.currentLiters
    val showers  = (liters / 60).toInt()
    val days     = (liters / 135).toInt()
    val flushes  = (liters / 6).toInt()
    val plants   = (liters / 2).toInt()
    val co2      = liters * 0.001
    val insights = generateInsights(tank, setup, entries)

    Scaffold(topBar = { JalTopBar("Your Impact", onBack = onBack) }) { padding ->
        GradientBackground {
            Column(Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Spacer(Modifier.height(8.dp))

                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = WaterMid),
                    shape = RoundedCornerShape(20.dp)) {
                    Column(Modifier.padding(24.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🌍", fontSize = 48.sp)
                        Text("Total in Tank", color = Color.White.copy(.8f), fontSize = 13.sp)
                        Text("%.1f".format(liters), color = Color.White, fontSize = 52.sp, fontWeight = FontWeight.ExtraBold)
                        Text("Liters", color = Color.White.copy(.8f), fontSize = 16.sp)
                        if (liters > 0) Text("You're making a real difference! 🎉",
                            color = Color.White.copy(.9f), fontSize = 12.sp, textAlign = TextAlign.Center)
                        else Text("Start logging rainfall to see your impact!", color = Color.White.copy(.8f), fontSize = 12.sp)
                    }
                }

                Text("💧 What You've Saved Equals...", fontWeight = FontWeight.Bold, color = WaterDeep, fontSize = 15.sp)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    ImpactCell(Modifier.weight(1f), "🚿", "$showers", "Showers", "@ 60L each", Color(0xFF1565C0))
                    ImpactCell(Modifier.weight(1f), "📅", "$days",    "Water Days", "@ 135L/person", Color(0xFF00838F))
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    ImpactCell(Modifier.weight(1f), "🚽", "$flushes", "Flushes",    "@ 6L per flush", Color(0xFF6A1B9A))
                    ImpactCell(Modifier.weight(1f), "🌱", "$plants",  "Plant Days", "@ 2L per plant", Color(0xFF2E7D32))
                }

                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                    shape = RoundedCornerShape(14.dp)) {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("🌿", fontSize = 32.sp); Spacer(Modifier.width(10.dp))
                        Column {
                            Text("CO₂ Equivalent Saved", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32), fontSize = 14.sp)
                            Text("≈ %.3f kg CO₂".format(co2), color = Color(0xFF388E3C), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text("By reducing municipal water treatment demand", color = Color.Gray, fontSize = 11.sp)
                        }
                    }
                }

                // Personalized insights
                if (insights.isNotEmpty()) {
                    Text("💡 Personalized Insights", fontWeight = FontWeight.Bold, color = WaterDeep, fontSize = 15.sp)
                    insights.forEach { ins ->
                        Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
                            shape = RoundedCornerShape(12.dp)) {
                            Row(Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
                                Text(ins.emoji, fontSize = 22.sp)
                                Spacer(Modifier.width(10.dp))
                                Column {
                                    Text(ins.title, fontWeight = FontWeight.SemiBold, color = Color(0xFF4A148C), fontSize = 13.sp)
                                    Text(ins.body, color = Color(0xFF6A1B9A), fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ImpactCell(modifier: Modifier, emoji: String, value: String, title: String, sub: String, color: Color) {
    Card(modifier, colors = CardDefaults.cardColors(containerColor = color.copy(.1f)), shape = RoundedCornerShape(14.dp)) {
        Column(Modifier.padding(14.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(emoji, fontSize = 28.sp)
            Text(value, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = color)
            Text(title, fontWeight = FontWeight.SemiBold, color = WaterDeep, fontSize = 12.sp)
            Text(sub, fontSize = 10.sp, color = Color.Gray)
        }
    }
}
