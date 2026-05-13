package com.jalsanchay.tracker.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jalsanchay.tracker.model.AlertLevel
import com.jalsanchay.tracker.ui.theme.WaterDeep
import com.jalsanchay.tracker.ui.theme.WaterMid
import com.jalsanchay.tracker.viewmodel.JalSanchayViewModel
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: JalSanchayViewModel,
    onEditInputs: () -> Unit,
    modifier: Modifier = Modifier
) {
    val setup   by viewModel.setup.collectAsState()
    val tank    by viewModel.tank.collectAsState()
    val entries by viewModel.rainfallEntries.collectAsState()
    val todayMm by viewModel.todayRainfallMm.collectAsState()

    // ── State must be INSIDE the composable function ──────────────────────
    var showResetDialog by remember { mutableStateOf(false) }

    val alert       = tank.alertLevel
    val alertColor  = Color(android.graphics.Color.parseColor(alert.colorHex))
    val todayLiters = entries.firstOrNull()?.litersCollected ?: 0.0
    val totalLiters = tank.currentLiters
    val waterDays   = (totalLiters / 135).toInt()

    // ── Reset confirmation dialog — placed at composable root, not inside TopAppBar ──
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title   = { Text("Reset App Data?") },
            text    = { Text("This will clear all rainfall entries and setup. You'll return to the input screen.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.resetData()
                    showResetDialog = false
                    onEditInputs()
                }) {
                    Text("Reset", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Jal-Sanchay Tracker",
                            fontWeight = FontWeight.Bold,
                            color      = Color.White,
                            fontSize   = 18.sp
                        )
                        Text(
                            "Water Wealth Dashboard",
                            color    = Color.White.copy(.8f),
                            fontSize = 11.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onEditInputs) {
                        Icon(
                            imageVector        = Icons.Default.ArrowBack,
                            contentDescription = "Edit Inputs",
                            tint               = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = WaterMid),
                actions = {
                    TextButton(onClick = onEditInputs) {
                        Text("Edit Inputs", color = Color.White, fontSize = 12.sp)
                    }
                    IconButton(onClick = { showResetDialog = true }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // ── Alert banner ──────────────────────────────────────────────
            if (alert != AlertLevel.NORMAL) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors   = CardDefaults.cardColors(containerColor = alertColor.copy(.13f)),
                    shape    = RoundedCornerShape(12.dp)
                ) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(alert.emoji, fontSize = 20.sp)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "${alert.label}: ${alert.message}",
                            color      = alertColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize   = 13.sp
                        )
                    }
                }
            }

            // ── Water tank animation ──────────────────────────────────────
            WaterTankCard(
                fillPercent    = tank.fillPercent,
                currentLiters  = tank.currentLiters,
                capacityLiters = tank.capacityLiters
            )

            // ── Key metrics row ───────────────────────────────────────────
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MetricCard(
                    modifier = Modifier.weight(1f),
                    emoji    = "🌧️",
                    label    = "Today's Rain",
                    value    = "%.1f mm".format(todayMm),
                    color    = Color(0xFF1565C0)
                )
                MetricCard(
                    modifier = Modifier.weight(1f),
                    emoji    = "💧",
                    label    = "Saved Today",
                    value    = "%.0f L".format(todayLiters),
                    color    = Color(0xFF00838F)
                )
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MetricCard(
                    modifier = Modifier.weight(1f),
                    emoji    = "📦",
                    label    = "Total Savings",
                    value    = "%.0f L".format(totalLiters),
                    color    = Color(0xFF2E7D32)
                )
                MetricCard(
                    modifier = Modifier.weight(1f),
                    emoji    = "🏠",
                    label    = "Water Days",
                    value    = "$waterDays days",
                    color    = Color(0xFF6A1B9A)
                )
            }

            // ── Impact Score card ─────────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors   = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                shape    = RoundedCornerShape(16.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "🌍 Impact Score",
                        fontWeight = FontWeight.Bold,
                        color      = Color(0xFF1B5E20),
                        fontSize   = 15.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        ImpactStat("🚿", "${(totalLiters / 60).toInt()}", "Showers")
                        ImpactStat("📅", "$waterDays", "Water Days")
                        ImpactStat("🌱", "${(totalLiters / 2).toInt()}", "Plant Days")
                        ImpactStat("🌿", "%.2f kg".format(totalLiters * 0.001), "CO₂ Saved")
                    }
                }
            }

            // ── Setup summary ─────────────────────────────────────────────
            if (setup.isComplete) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors   = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
                    shape    = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🧮", fontSize = 20.sp)
                        Spacer(Modifier.width(8.dp))
                        Column(Modifier.weight(1f)) {
                            Text(
                                "${setup.rooftopType.name} · ${setup.roofAreaSqFt.toInt()} sq ft · Cap ${setup.tankCapacityLiters.toInt()} L",
                                fontSize   = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color      = Color(0xFF4A148C)
                            )
                            Text(
                                "Runoff ${(setup.rooftopType.runoffCoefficient * 100).toInt()}% · ${entries.size} entries logged",
                                fontSize = 11.sp,
                                color    = Color(0xFF7B1FA2)
                            )
                        }
                        TextButton(onClick = onEditInputs) {
                            Text("Edit", color = WaterMid, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // ── Overflow warning ──────────────────────────────────────────
            if (tank.isOverflowing) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors   = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                    shape    = RoundedCornerShape(12.dp)
                ) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("💦", fontSize = 20.sp)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Tank is FULL — overflow occurring! Use stored water immediately.",
                            color      = Color(0xFFC62828),
                            fontSize   = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

// ── Water tank Canvas ─────────────────────────────────────────────────────────

@Composable
private fun WaterTankCard(fillPercent: Double, currentLiters: Double, capacityLiters: Double) {
    val animFill by animateFloatAsState(
        targetValue   = (fillPercent / 100f).toFloat(),
        animationSpec = tween(1500, easing = EaseOutCubic),
        label         = "fill"
    )
    val waveOffset by rememberInfiniteTransition(label = "w").animateFloat(
        initialValue  = 0f,
        targetValue   = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing)),
        label         = "wave"
    )
    val waterColor = when {
        fillPercent >= 100 -> Color(0xFFC62828)
        fillPercent >= 80  -> Color(0xFFE65100)
        fillPercent >= 50  -> Color(0xFFF9A825)
        else               -> Color(0xFF1565C0)
    }

    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(20.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Water Tank Status", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = WaterDeep)
            Spacer(Modifier.height(12.dp))

            Box(Modifier.width(160.dp).height(200.dp), contentAlignment = Alignment.Center) {
                Canvas(Modifier.fillMaxSize()) {
                    drawRoundRect(
                        color        = Color(0xFFE3F2FD),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(20f)
                    )
                    if (animFill > 0f) {
                        val h   = size.height * animFill
                        val top = size.height - h
                        val path = Path().apply {
                            moveTo(0f, top + 12f)
                            var x = 0f
                            while (x <= size.width) {
                                lineTo(
                                    x,
                                    top + (sin((x / size.width * 4 * Math.PI + waveOffset).toDouble()) * 8).toFloat()
                                )
                                x += 4f
                            }
                            lineTo(size.width, size.height)
                            lineTo(0f, size.height)
                            close()
                        }
                        drawPath(
                            path  = path,
                            brush = Brush.verticalGradient(
                                colors = listOf(waterColor.copy(.75f), waterColor),
                                startY = top,
                                endY   = size.height
                            )
                        )
                    }
                    drawRoundRect(
                        color        = Color(0xFF1565C0).copy(.25f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(20f),
                        style        = Stroke(3f)
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "%.0f%%".format(fillPercent),
                        fontSize   = 34.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color      = if (fillPercent > 55) Color.White else WaterDeep
                    )
                    Text(
                        "%.0f / %.0f L".format(currentLiters, capacityLiters),
                        fontSize = 11.sp,
                        color    = if (fillPercent > 55) Color.White.copy(.9f) else Color.Gray
                    )
                }
            }

            Spacer(Modifier.height(10.dp))
            LinearProgressIndicator(
                progress      = { animFill },
                modifier      = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                color         = waterColor,
                trackColor    = Color(0xFFEEEEEE)
            )
            Spacer(Modifier.height(6.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                listOf(
                    "0%"   to Color.Gray,
                    "50%"  to Color(0xFFF9A825),
                    "80%"  to Color(0xFFE65100),
                    "100%" to Color(0xFFC62828)
                ).forEach { (t, c) -> Text(t, fontSize = 9.sp, color = c) }
            }
        }
    }
}

// ── Small composables ─────────────────────────────────────────────────────────

@Composable
private fun MetricCard(modifier: Modifier, emoji: String, label: String, value: String, color: Color) {
    Card(
        modifier = modifier,
        colors   = CardDefaults.cardColors(containerColor = color.copy(.1f)),
        shape    = RoundedCornerShape(14.dp)
    ) {
        Column(
            Modifier.padding(14.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(emoji, fontSize = 22.sp)
            Spacer(Modifier.height(4.dp))
            Text(value, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = color, textAlign = TextAlign.Center)
            Text(label, fontSize = 10.sp, color = Color.Gray, textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun ImpactStat(emoji: String, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(emoji, fontSize = 20.sp)
        Text(value, fontWeight = FontWeight.Bold, color = WaterDeep, fontSize = 13.sp)
        Text(label, fontSize = 9.sp, color = Color.Gray)
    }
}