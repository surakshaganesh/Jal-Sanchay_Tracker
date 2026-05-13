package com.jalsanchay.tracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jalsanchay.tracker.model.RainfallEntry
import com.jalsanchay.tracker.ui.components.GradientBackground
import com.jalsanchay.tracker.ui.components.JalTopBar
import com.jalsanchay.tracker.ui.theme.WaterDeep
import com.jalsanchay.tracker.ui.theme.WaterMid
import com.jalsanchay.tracker.viewmodel.JalSanchayViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ReportsScreen(viewModel: JalSanchayViewModel, onBack: () -> Unit, modifier: Modifier = Modifier) {
    val entries by viewModel.rainfallEntries.collectAsState()
    val tank    by viewModel.tank.collectAsState()

    val totalLiters  = entries.sumOf { it.litersCollected }
    val totalRain    = entries.sumOf { it.rainfallMm }
    val avgRain      = if (entries.isNotEmpty()) totalRain / entries.size else 0.0
    val maxRain      = entries.maxOfOrNull { it.rainfallMm } ?: 0.0
    val totalOverflow= entries.sumOf { it.overflowLiters }

    Scaffold(modifier = modifier, topBar = { JalTopBar("Reports", onBack = onBack) }) { padding ->
        GradientBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                // Summary cards
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SumCard(Modifier.weight(1f), "📋", entries.size.toString(), "Entries", Color(0xFF1565C0))
                    SumCard(Modifier.weight(1f), "🌧️", "%.1f".format(avgRain), "Avg mm", Color(0xFF00838F))
                    SumCard(Modifier.weight(1f), "⛈️", "%.1f".format(maxRain), "Max mm", Color(0xFF6A1B9A))
                }

                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = WaterMid),
                    shape = RoundedCornerShape(14.dp)) {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("💧", fontSize = 28.sp); Spacer(Modifier.width(10.dp))
                        Column {
                            Text("Total Collected", color = Color.White.copy(.8f), fontSize = 12.sp)
                            Text("%.1f Liters".format(totalLiters), color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.weight(1f))
                        if (totalOverflow > 0) Column(horizontalAlignment = Alignment.End) {
                            Text("Overflow", color = Color.White.copy(.7f), fontSize = 11.sp)
                            Text("%.1f L".format(totalOverflow), color = Color(0xFFFFCDD2), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Bar chart (last 7)
                if (entries.size >= 2) {
                    Text("📈 Collection Chart (last 7 entries)", fontWeight = FontWeight.Bold, color = WaterDeep, fontSize = 14.sp)
                    BarChartCard(entries.takeLast(7))
                }

                // Rainfall log table
                Text("📋 Rainfall Log", fontWeight = FontWeight.Bold, color = WaterDeep, fontSize = 14.sp)

                if (entries.isEmpty()) {
                    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(14.dp)) {
                        Column(Modifier.padding(32.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("🌤️", fontSize = 44.sp)
                            Spacer(Modifier.height(8.dp))
                            Text("No entries yet", fontWeight = FontWeight.Bold, color = Color.Gray)
                            Text("Go to Data Entry to log your first rainfall!", color = Color.Gray, fontSize = 13.sp)
                        }
                    }
                } else {
                    // Table header
                    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = WaterMid),
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)) {
                        Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 8.dp)) {
                            TH("Date", Modifier.weight(0.9f))
                            TH("mm", Modifier.weight(0.6f))
                            TH("Collected L", Modifier.weight(1.1f))
                            TH("Overflow L", Modifier.weight(1.0f))
                        }
                    }
                    val sdf = remember { SimpleDateFormat("dd MMM", Locale.getDefault()) }
                    entries.reversed().forEachIndexed { idx, e ->
                        Card(Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(if (idx % 2 == 0) Color.White else Color(0xFFF5F9FF)),
                            shape = RoundedCornerShape(0.dp)) {
                            Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                TD("${e.dayOfMonth} ${e.monthName}", Modifier.weight(0.9f), WaterDeep)
                                TD("%.1f".format(e.rainfallMm), Modifier.weight(0.6f), Color(0xFF1565C0))
                                TD("%.1f".format(e.litersCollected), Modifier.weight(1.1f), Color(0xFF2E7D32))
                                TD(if (e.overflowLiters > 0) "%.1f".format(e.overflowLiters) else "—", Modifier.weight(1.0f), Color(0xFFC62828))
                            }
                        }
                    }
                    // Footer
                    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(Color(0xFFE3F2FD)),
                        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)) {
                        Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 8.dp)) {
                            TD("TOTAL", Modifier.weight(0.9f), WaterDeep, bold = true)
                            TD("%.1f".format(totalRain), Modifier.weight(0.6f), Color(0xFF1565C0), bold = true)
                            TD("%.1f".format(totalLiters), Modifier.weight(1.1f), Color(0xFF2E7D32), bold = true)
                            TD("%.1f".format(totalOverflow), Modifier.weight(1.0f), Color(0xFFC62828), bold = true)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun BarChartCard(entries: List<RainfallEntry>) {
    val max = entries.maxOfOrNull { it.litersCollected } ?: 1.0
    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(Color.White), shape = RoundedCornerShape(14.dp)) {
        Column(Modifier.padding(14.dp)) {
            Row(Modifier.fillMaxWidth().height(110.dp), horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom) {
                entries.forEach { e ->
                    val frac = (e.litersCollected / max).toFloat().coerceIn(0.04f, 1f)
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom, modifier = Modifier.weight(1f)) {
                        Text("%.0f".format(e.litersCollected), fontSize = 7.sp, color = Color.Gray)
                        Spacer(Modifier.height(2.dp))
                        Box(Modifier.fillMaxWidth(0.65f).height((96 * frac).dp)
                            .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                            .background(WaterMid))
                        Spacer(Modifier.height(3.dp))
                        Text("${e.dayOfMonth}", fontSize = 8.sp, color = Color.Gray)
                    }
                }
            }
            Text("Day of month • Liters collected", fontSize = 9.sp, color = Color.LightGray,
                modifier = Modifier.padding(top = 4.dp))
        }
    }
}

@Composable private fun SumCard(m: Modifier, e: String, v: String, l: String, c: Color) {
    Card(m, colors = CardDefaults.cardColors(c.copy(.1f)), shape = RoundedCornerShape(12.dp)) {
        Column(Modifier.padding(10.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(e, fontSize = 18.sp); Text(v, fontWeight = FontWeight.Bold, color = c, fontSize = 14.sp)
            Text(l, fontSize = 9.sp, color = Color.Gray)
        }
    }
}
@Composable private fun TH(t: String, m: Modifier) = Text(t, m, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
@Composable private fun TD(t: String, m: Modifier, c: Color, bold: Boolean = false) =
    Text(t, m, fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal, fontSize = 11.sp, color = c)
