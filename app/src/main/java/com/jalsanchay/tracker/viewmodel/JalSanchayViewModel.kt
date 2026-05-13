package com.jalsanchay.tracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jalsanchay.tracker.data.RainfallLogEntity
import com.jalsanchay.tracker.data.database.JalSanchayDatabase
import com.jalsanchay.tracker.data.repository.JalSanchayRepository
import com.jalsanchay.tracker.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import kotlinx.coroutines.flow.first

class JalSanchayViewModel(application: Application) : AndroidViewModel(application) {

    private val db = JalSanchayDatabase.getInstance(application)
    private val repo = JalSanchayRepository(
        db.setupConfigDao(), db.rainfallLogDao(),
        db.tankSnapshotDao(), db.alertHistoryDao()
    )

    val setup: StateFlow<AppSetup> = repo.setupConfig
        .map { cfg ->
            if (cfg == null) AppSetup()
            else AppSetup(
                roofAreaSqFt       = cfg.roofAreaSqFt,
                tankCapacityLiters = cfg.tankCapacityLiters,
                rooftopType        = rooftopById(cfg.rooftopTypeName),
                isComplete         = true
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppSetup())

    val tank: StateFlow<TankState> = combine(repo.tankSnapshot, repo.setupConfig) { snap, cfg ->
        TankState(
            currentLiters  = snap?.currentLiters ?: 0.0,
            capacityLiters = cfg?.tankCapacityLiters ?: 0.0
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TankState())

    val rainfallEntries: StateFlow<List<RainfallEntry>> = repo.allRainfallEntries
        .map { list -> list.map { it.toModel() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _todayRainfallMm = MutableStateFlow(0.0)
    val todayRainfallMm: StateFlow<Double> = _todayRainfallMm.asStateFlow()

    val alertHistory: StateFlow<List<com.jalsanchay.tracker.data.AlertHistoryEntity>> =
        repo.recentAlerts.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun saveSetup(roofAreaSqFt: Double, tankCapacityLiters: Double, rooftopType: RooftopType) {
        viewModelScope.launch {
            repo.saveSetup(roofAreaSqFt, tankCapacityLiters, rooftopType.id, rooftopType.runoffCoefficient)
            // Initialize tank if no snapshot exists yet
            val existing = repo.tankSnapshot.first()
            if (existing == null) {
                repo.updateTank(0.0, tankCapacityLiters)
            } else {
                // If capacity changed, clamp existing level
                val clamped = existing.currentLiters.coerceAtMost(tankCapacityLiters)
                repo.updateTank(clamped, tankCapacityLiters)
            }
        }
    }

    // Add this new combined function
    fun calculateAndSave(
        roofAreaSqFt: Double,
        tankCapacityLiters: Double,
        rooftopType: RooftopType,
        rainfallMm: Double
    ) {
        viewModelScope.launch {
            // Step 1 — save setup first and wait for it
            repo.saveSetup(
                roofAreaSqFt       = roofAreaSqFt,
                tankCapacityLiters = tankCapacityLiters,
                rooftopTypeName    = rooftopType.id,
                runoffCoefficient  = rooftopType.runoffCoefficient
            )

            // Step 2 — get current tank level from DB (not stale state)
            val existingTank = repo.tankSnapshot.first()
            val currentLiters = existingTank?.currentLiters ?: 0.0

            // Step 3 — calculate with correct values passed directly
            val collected = calculateCollectedLiters(
                roofAreaSqFt       = roofAreaSqFt,
                rainfallMm         = rainfallMm,
                runoffCoefficient  = rooftopType.runoffCoefficient
            )

            // Step 4 — add to existing (not replace)
            val newLevel  = currentLiters + collected
            val capped    = newLevel.coerceAtMost(tankCapacityLiters)
            val overflow  = (newLevel - tankCapacityLiters).coerceAtLeast(0.0)

            // Step 5 — save rainfall log
            val today = LocalDate.now()
            val month = today.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())

            repo.logRainfall(
                RainfallLogEntity(
                    rainfallMm        = rainfallMm,
                    litersCollected   = collected,
                    roofAreaSqFt      = roofAreaSqFt,
                    runoffCoefficient = rooftopType.runoffCoefficient,
                    tankLevelBefore   = currentLiters,
                    tankLevelAfter    = capped,
                    overflowLiters    = overflow,
                    dayOfMonth        = today.dayOfMonth,
                    monthName         = month,
                    year              = today.year
                )
            )

            // Step 6 — update tank snapshot
            repo.updateTank(capped, tankCapacityLiters)
            _todayRainfallMm.value = rainfallMm

            // Step 7 — log alert if needed
            val pct = if (tankCapacityLiters > 0) capped / tankCapacityLiters * 100 else 0.0
            val lvl = AlertLevel.from(pct)
            if (lvl != AlertLevel.NORMAL) {
                repo.saveAlert(lvl.name, lvl.message, pct)
            }
        }
    }

    fun resetData() {
        viewModelScope.launch {
            repo.resetAllData()
            _todayRainfallMm.value = 0.0
            repo.clearSetup()
        }
    }
}

private fun RainfallLogEntity.toModel() = RainfallEntry(
    id = id, rainfallMm = rainfallMm, litersCollected = litersCollected,
    overflowLiters = overflowLiters, dayOfMonth = dayOfMonth,
    monthName = monthName, year = year, timestamp = timestamp
)
