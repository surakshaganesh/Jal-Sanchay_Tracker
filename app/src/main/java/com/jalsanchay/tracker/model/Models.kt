package com.jalsanchay.tracker.model

data class RooftopType(
    val id: String,
    val name: String,
    val emoji: String,
    val description: String,
    val runoffCoefficient: Double,
    val notes: String
)

val ROOFTOP_TYPES = listOf(
    RooftopType("flat_concrete", "Flat Concrete", "🏠", "Standard urban rooftop", 0.85, "Smooth surface — moderate losses from ponding & evaporation"),
    RooftopType("sloped_tiles", "Sloped Tiles", "🏡", "Traditional clay/cement tiled roof", 0.75, "Porous tiles absorb ~10% more water than concrete"),
    RooftopType("metal_sheet", "Metal Sheet", "🏭", "Galvanised iron / corrugated sheet", 0.95, "Smoothest surface — highest collection efficiency"),
    RooftopType("green_roof", "Green Roof", "🌱", "Living / vegetation roof", 0.40, "Vegetation retains most rain — low runoff, high ecology"),
    RooftopType("asphalt", "Asphalt / Bitumen", "🏗️", "Waterproofed flat roof", 0.90, "Near-impervious surface — second best after metal")
)

fun rooftopById(id: String): RooftopType = ROOFTOP_TYPES.find { it.id == id } ?: ROOFTOP_TYPES[0]

fun calculateCollectedLiters(roofAreaSqFt: Double, rainfallMm: Double, runoffCoefficient: Double): Double {
    if (roofAreaSqFt <= 0 || rainfallMm <= 0) return 0.0
    return roofAreaSqFt * rainfallMm * 0.0929 * runoffCoefficient
}

data class AppSetup(
    val roofAreaSqFt: Double = 0.0,
    val tankCapacityLiters: Double = 0.0,
    val rooftopType: RooftopType = ROOFTOP_TYPES[0],
    val isComplete: Boolean = false
)

data class TankState(
    val currentLiters: Double = 0.0,
    val capacityLiters: Double = 0.0
) {
    val fillPercent: Double get() = if (capacityLiters > 0) (currentLiters / capacityLiters * 100).coerceIn(0.0, 100.0) else 0.0
    val remainingLiters: Double get() = (capacityLiters - currentLiters).coerceAtLeast(0.0)
    val isOverflowing: Boolean get() = currentLiters >= capacityLiters
    val alertLevel: AlertLevel get() = AlertLevel.from(fillPercent)
}

data class RainfallEntry(
    val id: Long = 0,
    val rainfallMm: Double,
    val litersCollected: Double,
    val overflowLiters: Double,
    val dayOfMonth: Int,
    val monthName: String,
    val year: Int,
    val timestamp: Long = System.currentTimeMillis()
)

enum class AlertLevel(val label: String, val threshold: String, val emoji: String, val colorHex: String, val message: String, val action: String) {
    NORMAL("Normal", "< 50%", "💧", "#1565C0", "Tank is at low-to-normal level. Keep logging rainfall.", "Log any rainfall events to build your reserves."),
    STABLE("Stable", "≥ 50%", "✅", "#2E7D32", "Great job! Your tank is more than half full.", "Consider using some for gardening to make room."),
    WARNING("Warning", "≥ 80%", "⚠️", "#E65100", "Tank approaching capacity. Plan water usage now.", "Use stored water for toilets, gardening, or cleaning."),
    OVERFLOW("Overflow Risk!", "= 100%", "🚨", "#C62828", "Tank FULL — overflow occurring! Water is being lost.", "Immediately divert water or expand storage capacity.");

    companion object {
        fun from(fillPercent: Double) = when {
            fillPercent >= 100.0 -> OVERFLOW
            fillPercent >= 80.0  -> WARNING
            fillPercent >= 50.0  -> STABLE
            else                  -> NORMAL
        }
    }
}

data class Badge(val id: String, val title: String, val description: String, val icon: String, val isUnlocked: Boolean)

fun computeBadges(entryCount: Int, totalLiters: Double, fillPercent: Double): List<Badge> = listOf(
    Badge("first_drop",    "First Drop",       "Log your first rainfall entry", "💧", entryCount >= 1),
    Badge("water_warrior", "Water Warrior",     "Save 100 liters",              "🛡️", totalLiters >= 100),
    Badge("rain_master",   "Rain Master",       "Log 10 rainfall entries",      "🌧️", entryCount >= 10),
    Badge("hydro_hero",    "Hydro Hero",        "Save 500 liters",              "🦸", totalLiters >= 500),
    Badge("conserve",      "Conservationist",   "Save 1,000 liters",            "🌿", totalLiters >= 1000),
    Badge("full_tank",     "Full Tank",         "Fill your tank to 100%",       "🏆", fillPercent >= 100.0)
)

data class Insight(val emoji: String, val title: String, val body: String)

fun generateInsights(tank: TankState, setup: AppSetup, entries: List<RainfallEntry>): List<Insight> {
    val insights = mutableListOf<Insight>()
    if (entries.isNotEmpty()) {
        val avg = entries.map { it.rainfallMm }.average()
        val perMm = calculateCollectedLiters(setup.roofAreaSqFt, 1.0, setup.rooftopType.runoffCoefficient)
        insights += Insight("📊", "Your average rainfall", "Avg %.1f mm per entry — collecting ~%.0f L each time.".format(avg, avg * perMm))
    }
    if (tank.fillPercent < 20 && setup.roofAreaSqFt > 0) {
        val neededMm = tank.remainingLiters / (setup.roofAreaSqFt * 0.0929 * setup.rooftopType.runoffCoefficient)
        insights += Insight("🎯", "To fill your tank", "You need ~%.1f mm of rainfall to reach 100%% capacity.".format(neededMm))
    }
    if (setup.rooftopType.runoffCoefficient < 0.85) {
        val gain = (0.95 - setup.rooftopType.runoffCoefficient) * 100
        insights += Insight("💡", "Upgrade tip", "Switching to Metal Sheet could increase collection by ~%.0f%%.".format(gain))
    }
    if (tank.currentLiters > 0) {
        val showers = (tank.currentLiters / 60).toInt()
        val days = (tank.currentLiters / 135).toInt()
        insights += Insight("🚿", "Current reserves equal", "$showers showers or $days days of water per person.")
    }
    return insights
}
