package com.jalsanchay.tracker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Water-inspired palette
val WaterDeep = Color(0xFF0D3B6E)
val WaterMid = Color(0xFF1565C0)
val WaterLight = Color(0xFF42A5F5)
val WaterSurface = Color(0xFFE3F2FD)
val WaterTeal = Color(0xFF00838F)

val GreenSafe = Color(0xFF2E7D32)
val GreenLight = Color(0xFF66BB6A)
val YellowWarn = Color(0xFFF9A825)
val OrangeAlert = Color(0xFFE65100)
val RedDanger = Color(0xFFC62828)

val BackgroundLight = Color(0xFFF0F8FF)
val SurfaceLight = Color(0xFFFFFFFF)

private val LightColorScheme = lightColorScheme(
    primary = WaterMid,
    onPrimary = Color.White,
    primaryContainer = WaterSurface,
    onPrimaryContainer = WaterDeep,
    secondary = WaterTeal,
    onSecondary = Color.White,
    background = BackgroundLight,
    surface = SurfaceLight,
    onBackground = Color(0xFF1A237E),
    onSurface = Color(0xFF0D1B4B)
)

@Composable
fun JalSanchayTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}
