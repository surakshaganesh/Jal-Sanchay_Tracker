package com.jalsanchay.tracker.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.jalsanchay.tracker.viewmodel.JalSanchayViewModel

private data class BottomTab(
    val label: String,
    val icon: ImageVector,
)

private val TABS = listOf(
    BottomTab("Dashboard", Icons.Default.Home),
    BottomTab("Reports",   Icons.Default.BarChart),
    BottomTab("Tips",      Icons.Default.Lightbulb),
    BottomTab("Badges",    Icons.Default.EmojiEvents),
)

@Composable
fun MainShell(
    viewModel: JalSanchayViewModel,
    onEditInputs: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                TABS.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick  = { selectedTab = index },
                        icon     = { Icon(tab.icon, contentDescription = tab.label) },
                        label    = { Text(tab.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            0 -> DashboardScreen(
                viewModel    = viewModel,
                onEditInputs = onEditInputs,
                modifier     = Modifier.padding(innerPadding)
            )
            1 -> ReportsScreen(
                viewModel = viewModel,
                onBack    = { selectedTab = 0 },
                modifier  = Modifier.padding(innerPadding)
            )
            2 -> KnowledgeHubScreen(
                onBack   = { selectedTab = 0 },
                modifier = Modifier.padding(innerPadding)
            )
            3 -> GamificationScreen(
                viewModel = viewModel,
                onBack    = { selectedTab = 0 },
                modifier  = Modifier.padding(innerPadding)
            )
        }
    }
}