package com.jalsanchay.tracker.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jalsanchay.tracker.ui.components.GradientBackground
import com.jalsanchay.tracker.ui.components.JalTopBar
import com.jalsanchay.tracker.ui.theme.WaterDeep
import com.jalsanchay.tracker.ui.theme.WaterMid

private data class Tip(val category: String, val emoji: String, val title: String, val body: String, val color: Color)

private val ALL_TIPS = listOf(
    Tip("Collection","🏠","Optimize Roof Area","Clean your rooftop regularly. Dirt and debris can reduce collection efficiency by up to 15%. A simple monthly sweep makes a big difference.",Color(0xFF1565C0)),
    Tip("Collection","🪣","First Flush Diverter","Install a first-flush diverter to discard the first 25 L of rainfall which carries rooftop contaminants and bird droppings. Greatly improves water quality.",Color(0xFF1565C0)),
    Tip("Collection","📐","Correct Gutter Slope","Ensure gutters slope at 1:50 ratio toward downpipes. Flat gutters pool debris and reduce flow into your tank.",Color(0xFF1565C0)),
    Tip("Storage","🛢️","Tank Maintenance","Clean storage tanks every 6 months. Keep them covered and dark — algae grows rapidly in light-exposed water and can block filters.",Color(0xFF6A1B9A)),
    Tip("Storage","🔒","Cover Your Tank","Always use a tight-fitting lid with a fine mesh screen. This prevents mosquito breeding, leaf contamination, and evaporation losses.",Color(0xFF6A1B9A)),
    Tip("Storage","🌡️","Underground Storage","Partially bury tanks to keep water cool. Ground temperature (~22°C) slows bacterial growth and reduces evaporation compared to surface tanks.",Color(0xFF6A1B9A)),
    Tip("Usage","🌱","Garden First","Use harvested rainwater for gardening before any other use. Plants love slightly acidic rainwater and it conserves your municipal supply entirely.",Color(0xFF2E7D32)),
    Tip("Usage","🚽","Toilet Flushing","Connect rainwater to toilet cisterns — toilets account for ~30% of household water use. Perfect non-potable application with a simple plumbing connection.",Color(0xFF2E7D32)),
    Tip("Usage","🚿","Car & Floor Washing","Rainwater is naturally soft (low minerals), ideal for washing vehicles and floors without leaving soap residue or calcium streaks.",Color(0xFF2E7D32)),
    Tip("Monsoon","⛈️","Pre-Monsoon Prep","Before June, inspect all gutters, downpipes, and tank connections. A blocked pipe can waste an entire monsoon's rainfall. Clear everything in May.",Color(0xFFE65100)),
    Tip("Monsoon","🌧️","Maximize the Season","Peak Indian monsoon (June–Sep): even a 20mm rain on 500 sq ft metal roof collects ~930 L. Log every event — small rains add up fast.",Color(0xFFE65100)),
    Tip("Science","🔬","The Water Formula","Collected L = Area(sqft) × Rainfall(mm) × 0.0929 × Runoff Coefficient. 0.0929 converts sq ft to sq m. Runoff accounts for evaporation & splash.",Color(0xFF1A237E)),
    Tip("Science","💧","Runoff Coefficients","Metal Sheet: 0.95 (best), Asphalt: 0.90, Concrete: 0.85, Tiles: 0.75, Green Roof: 0.40. These are scientifically measured averages.",Color(0xFF1A237E)),
)

@Composable
fun KnowledgeHubScreen(onBack: () -> Unit, modifier: Modifier = Modifier) {
    var query    by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("All") }
    val categories = listOf("All","Collection","Storage","Usage","Monsoon","Science")

    val filtered = ALL_TIPS.filter { tip ->
        (category == "All" || tip.category == category) &&
        (query.isBlank() || tip.title.contains(query, ignoreCase = true) ||
         tip.body.contains(query, ignoreCase = true))
    }

    Scaffold(modifier = modifier, topBar = { JalTopBar("Knowledge Hub", onBack = onBack) }) { padding ->
        GradientBackground {
            Column(
                modifier = Modifier.fillMaxSize().padding(padding)
            ) {                // Search bar — fixed, no rogue border/line
                OutlinedTextField(
                    value = query, onValueChange = { query = it },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
                    placeholder = { Text("Search tips...") },
                    leadingIcon = { Icon(Icons.Default.Search, null, tint = WaterMid) },
                    shape = RoundedCornerShape(28.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedBorderColor = Color(0xFFBBDEFB),
                        focusedBorderColor = WaterMid
                    )
                )

                // Category chips
                Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    categories.forEach { cat ->
                        FilterChip(selected = category == cat, onClick = { category = cat },
                            label = { Text(cat, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = WaterDeep,
                                selectedLabelColor = Color.White
                            ))
                    }
                }

                Spacer(Modifier.height(4.dp))
                Text("${filtered.size} tips", fontSize = 11.sp, color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 18.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Spacer(Modifier.height(4.dp))
                    filtered.forEach { tip -> ExpandableTipCard(tip) }
                    Spacer(Modifier.height(16.dp))
                }
            }
            }
        }
    }

@Composable
private fun ExpandableTipCard(tip: Tip) {
    var expanded by remember { mutableStateOf(false) }
    Card(Modifier.fillMaxWidth().clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = tip.color.copy(.07f)),
        shape = RoundedCornerShape(14.dp)) {
        Column(Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(tip.emoji, fontSize = 22.sp)
                Spacer(Modifier.width(8.dp))
                Column(Modifier.weight(1f)) {
                    Text(tip.title, fontWeight = FontWeight.Bold, color = tip.color, fontSize = 14.sp)
                    Card(colors = CardDefaults.cardColors(tip.color.copy(.13f)), shape = RoundedCornerShape(4.dp)) {
                        Text(tip.category, Modifier.padding(horizontal = 6.dp, vertical = 1.dp),
                            fontSize = 9.sp, color = tip.color, fontWeight = FontWeight.SemiBold)
                    }
                }
                Icon(if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    null, tint = tip.color)
            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(Modifier.height(8.dp))
                    HorizontalDivider(color = tip.color.copy(.2f))
                    Spacer(Modifier.height(8.dp))
                    Text(tip.body, fontSize = 13.sp, color = WaterDeep, lineHeight = 20.sp)
                }
            }
        }
    }
}
