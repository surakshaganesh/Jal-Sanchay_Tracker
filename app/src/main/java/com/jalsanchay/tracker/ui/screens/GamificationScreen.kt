package com.jalsanchay.tracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jalsanchay.tracker.model.computeBadges
import com.jalsanchay.tracker.ui.components.GradientBackground
import com.jalsanchay.tracker.ui.components.JalTopBar
import com.jalsanchay.tracker.ui.theme.WaterDeep
import com.jalsanchay.tracker.ui.theme.WaterMid
import com.jalsanchay.tracker.viewmodel.JalSanchayViewModel

@Composable
fun GamificationScreen(viewModel: JalSanchayViewModel, onBack: () -> Unit, modifier: Modifier = Modifier) {
    val tank    by viewModel.tank.collectAsState()
    val entries by viewModel.rainfallEntries.collectAsState()
    val badges  = computeBadges(entries.size, tank.currentLiters, tank.fillPercent)
    val unlocked = badges.count { it.isUnlocked }

    Scaffold(modifier = modifier, topBar = { JalTopBar("Badges & Achievements", onBack = onBack) }) { padding ->
        GradientBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(WaterMid),
                    shape = RoundedCornerShape(20.dp)) {
                    Column(Modifier.padding(24.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🏆", fontSize = 44.sp)
                        Text("$unlocked / ${badges.size}", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.ExtraBold)
                        Text("Badges Unlocked", color = Color.White.copy(.8f), fontSize = 15.sp)
                        Spacer(Modifier.height(10.dp))
                        LinearProgressIndicator(
                            progress = { unlocked.toFloat() / badges.size },
                            modifier = Modifier.fillMaxWidth().height(8.dp),
                            color = Color.White, trackColor = Color.White.copy(.3f)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(if (unlocked == badges.size) "🎉 All badges unlocked!"
                             else "${badges.size - unlocked} more to go",
                            color = Color.White.copy(.8f), fontSize = 12.sp)
                    }
                }

                Text("Your Badges", fontWeight = FontWeight.Bold, color = WaterDeep, fontSize = 15.sp)

                badges.chunked(2).forEach { row ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        row.forEach { badge ->
                            Card(modifier = Modifier.weight(1f).alpha(if (badge.isUnlocked) 1f else 0.45f),
                                colors = CardDefaults.cardColors(if (badge.isUnlocked) Color(0xFFFFF9C4) else Color(0xFFF5F5F5)),
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(if (badge.isUnlocked) 5.dp else 1.dp)) {
                                Column(Modifier.padding(14.dp).fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(badge.icon, fontSize = 34.sp,
                                        modifier = Modifier.alpha(if (badge.isUnlocked) 1f else 0.3f))
                                    Spacer(Modifier.height(4.dp))
                                    Text(badge.title, fontWeight = FontWeight.Bold, fontSize = 12.sp,
                                        color = if (badge.isUnlocked) Color(0xFF5D4037) else Color.Gray,
                                        textAlign = TextAlign.Center)
                                    Text(badge.description, fontSize = 9.sp, color = Color.Gray,
                                        textAlign = TextAlign.Center)
                                    Spacer(Modifier.height(6.dp))
                                    Card(colors = CardDefaults.cardColors(
                                        if (badge.isUnlocked) Color(0xFFFFEB3B) else Color(0xFFE0E0E0)),
                                        shape = RoundedCornerShape(6.dp)) {
                                        Text(if (badge.isUnlocked) "✅ UNLOCKED" else "🔒 LOCKED",
                                            Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                            fontSize = 9.sp, fontWeight = FontWeight.Bold,
                                            color = if (badge.isUnlocked) Color(0xFF5D4037) else Color.Gray)
                                    }
                                }
                            }
                        }
                        if (row.size == 1) Spacer(Modifier.weight(1f))
                    }
                }

                // Live progress context
                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(Color(0xFFE3F2FD)),
                    shape = RoundedCornerShape(14.dp)) {
                    Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("📊 Your Progress", fontWeight = FontWeight.Bold, color = WaterDeep, fontSize = 14.sp)
                        ProgressRow("Entries logged", "${entries.size}")
                        ProgressRow("Water in tank", "%.0f L".format(tank.currentLiters))
                        ProgressRow("Tank level", "%.0f%%".format(tank.fillPercent))
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ProgressRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontSize = 12.sp, color = Color.Gray)
        Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = WaterDeep)
    }
}
