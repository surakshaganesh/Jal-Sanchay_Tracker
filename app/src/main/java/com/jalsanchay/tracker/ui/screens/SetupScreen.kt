package com.jalsanchay.tracker.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jalsanchay.tracker.model.ROOFTOP_TYPES
import com.jalsanchay.tracker.model.RooftopType
import com.jalsanchay.tracker.model.calculateCollectedLiters
import com.jalsanchay.tracker.ui.components.GradientBackground
import com.jalsanchay.tracker.ui.components.JalTopBar
import com.jalsanchay.tracker.ui.theme.WaterDeep
import com.jalsanchay.tracker.ui.theme.WaterMid
import com.jalsanchay.tracker.viewmodel.JalSanchayViewModel

@Composable
fun SetupScreen(
    viewModel: JalSanchayViewModel,
    onCalculate: () -> Unit
) {
    // Pre-fill from saved state so "Edit Inputs" shows previous values
    val savedSetup by viewModel.setup.collectAsState()
    val savedTank  by viewModel.tank.collectAsState()
    val lastRainMm by viewModel.todayRainfallMm.collectAsState()

    var roofArea     by remember { mutableStateOf(
        if (savedSetup.roofAreaSqFt > 0) savedSetup.roofAreaSqFt.toInt().toString() else ""
    )}
    var tankCapacity by remember { mutableStateOf(
        if (savedSetup.tankCapacityLiters > 0) savedSetup.tankCapacityLiters.toInt().toString() else ""
    )}
    var rainfallMm   by remember { mutableStateOf(
        if (lastRainMm > 0) lastRainMm.toString() else ""
    )}
    var selectedIdx  by remember { mutableIntStateOf(
        ROOFTOP_TYPES.indexOfFirst { it.id == savedSetup.rooftopType.id }.coerceAtLeast(0)
    )}
    var showError    by remember { mutableStateOf(false) }
    var errorMsg     by remember { mutableStateOf("") }

    val selectedRooftop = ROOFTOP_TYPES[selectedIdx]
    val areaVal     = roofArea.toDoubleOrNull()
    val capacityVal = tankCapacity.toDoubleOrNull()
    val rainVal     = rainfallMm.toDoubleOrNull()

    // Live preview while user types
    val previewLiters = if (areaVal != null && rainVal != null && areaVal > 0 && rainVal > 0)
        calculateCollectedLiters(areaVal, rainVal, selectedRooftop.runoffCoefficient)
    else 0.0

    Scaffold(
        topBar = {
            JalTopBar(
                title = if (savedSetup.isComplete) "Edit Inputs" else "Setup",
                onBack = if (savedSetup.isComplete) ({ onCalculate() }) else null
            )
        }
    ) { padding ->
        GradientBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(Modifier.height(8.dp))

                // ── Header card ───────────────────────────────────────────
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors   = CardDefaults.cardColors(containerColor = WaterMid),
                    shape    = RoundedCornerShape(16.dp)
                ) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("🏗️", fontSize = 30.sp)
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(
                                if (savedSetup.isComplete) "Update Your Setup" else "Configure Your System",
                                color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp
                            )
                            Text(
                                "Enter all values then tap Calculate",
                                color = Color.White.copy(.8f), fontSize = 11.sp
                            )
                        }
                    }
                }

                // ── Roof Area ─────────────────────────────────────────────
                FieldLabel("🏠 Roof Area (square feet)")
                OutlinedTextField(
                    value         = roofArea,
                    onValueChange = { roofArea = it; showError = false },
                    modifier      = Modifier.fillMaxWidth(),
                    placeholder   = { Text("e.g. 500") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    shape         = RoundedCornerShape(12.dp),
                    singleLine    = true,
                    isError       = showError && areaVal == null
                )

                // ── Tank Capacity ─────────────────────────────────────────
                FieldLabel("🛢️ Tank Capacity (liters)")
                OutlinedTextField(
                    value         = tankCapacity,
                    onValueChange = { tankCapacity = it; showError = false },
                    modifier      = Modifier.fillMaxWidth(),
                    placeholder   = { Text("e.g. 5000") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    shape         = RoundedCornerShape(12.dp),
                    singleLine    = true,
                    isError       = showError && capacityVal == null
                )

                // ── Rainfall input ────────────────────────────────────────
                FieldLabel("🌧️ Today's Rainfall (mm)")
                OutlinedTextField(
                    value         = rainfallMm,
                    onValueChange = { rainfallMm = it; showError = false },
                    modifier      = Modifier.fillMaxWidth(),
                    placeholder   = { Text("e.g. 25") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    shape         = RoundedCornerShape(12.dp),
                    singleLine    = true,
                    isError       = showError && rainVal == null
                )

                // ── Rooftop selector ──────────────────────────────────────
                FieldLabel("🏘️ Select Rooftop Type")
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ROOFTOP_TYPES.forEachIndexed { idx, rt ->
                        RooftopCard(
                            rt       = rt,
                            selected = idx == selectedIdx,
                            onClick  = { selectedIdx = idx }
                        )
                    }
                }

                // ── Live preview ──────────────────────────────────────────
                AnimatedVisibility(visible = previewLiters > 0) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors   = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                        shape    = RoundedCornerShape(14.dp)
                    ) {
                        Column(
                            Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                "📊 Preview Calculation",
                                fontWeight = FontWeight.Bold,
                                color      = Color(0xFF1B5E20),
                                fontSize   = 14.sp
                            )
                            Text(
                                "%.1f L collected from %.0f mm on ${selectedRooftop.name}"
                                    .format(previewLiters, rainVal ?: 0.0),
                                color    = Color(0xFF2E7D32),
                                fontSize = 13.sp
                            )
                            Text(
                                "Formula: ${areaVal?.toInt()} × ${rainVal} × 0.0929 × ${selectedRooftop.runoffCoefficient}",
                                fontSize = 10.sp,
                                color    = Color(0xFF388E3C)
                            )
                        }
                    }
                }

                // ── Validation error ──────────────────────────────────────
                AnimatedVisibility(visible = showError) {
                    Text(errorMsg, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
                }

                // ── Calculate button ──────────────────────────────────────
                Button(
                    onClick = {
                        when {
                            areaVal == null || areaVal <= 0 -> {
                                errorMsg  = "⚠️ Enter a valid roof area (numbers only)."
                                showError = true
                            }
                            capacityVal == null || capacityVal <= 0 -> {
                                errorMsg  = "⚠️ Enter a valid tank capacity (numbers only)."
                                showError = true
                            }
                            rainVal == null || rainVal <= 0 -> {
                                errorMsg  = "⚠️ Enter a valid rainfall amount (numbers only)."
                                showError = true
                            }
                            else -> {
                                viewModel.calculateAndSave(
                                    roofAreaSqFt       = areaVal,
                                    tankCapacityLiters = capacityVal,
                                    rooftopType        = selectedRooftop,
                                    rainfallMm         = rainVal
                                )
                                onCalculate()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape  = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = WaterMid)
                ) {
                    Text("Calculate & View Dashboard 💧", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

// ── Small helpers ──────────────────────────────────────────────────────────────

@Composable
private fun FieldLabel(text: String) {
    Text(text, fontWeight = FontWeight.SemiBold, color = WaterDeep, fontSize = 14.sp)
}

@Composable
private fun RooftopCard(rt: RooftopType, selected: Boolean, onClick: () -> Unit) {
    val pct = (rt.runoffCoefficient * 100).toInt()
    val badgeColor = when {
        pct >= 90 -> Color(0xFF2E7D32)
        pct >= 80 -> Color(0xFF1565C0)
        else      -> Color(0xFFE65100)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) Color(0xFFE3F2FD) else Color.White)
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) WaterMid else Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(rt.emoji, fontSize = 26.sp)
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Row(
                verticalAlignment      = Alignment.CenterVertically,
                horizontalArrangement  = Arrangement.spacedBy(8.dp)
            ) {
                Text(rt.name, fontWeight = FontWeight.SemiBold, color = WaterDeep, fontSize = 13.sp)
                Box(
                    Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(badgeColor.copy(.12f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        "$pct% efficient",
                        fontSize       = 9.sp,
                        color          = badgeColor,
                        fontWeight     = FontWeight.Bold
                    )
                }
            }
            Text(rt.notes, color = Color.Gray, fontSize = 11.sp)
        }
        if (selected) Text("✅", fontSize = 18.sp)
    }
}