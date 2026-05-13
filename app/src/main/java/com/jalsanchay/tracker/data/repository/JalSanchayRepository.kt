package com.jalsanchay.tracker.data.repository

import com.jalsanchay.tracker.data.*
import com.jalsanchay.tracker.data.dao.*
import kotlinx.coroutines.flow.Flow

class JalSanchayRepository(
    private val setupConfigDao: SetupConfigDao,
    private val rainfallLogDao: RainfallLogDao,
    private val tankSnapshotDao: TankSnapshotDao,
    private val alertHistoryDao: AlertHistoryDao
) {
    // ── Setup ────────────────────────────────────────────────────────────────
    val setupConfig: Flow<SetupConfigEntity?> = setupConfigDao.getSetupConfig()

    suspend fun saveSetup(
        roofAreaSqFt: Double,
        tankCapacityLiters: Double,
        rooftopTypeName: String,
        runoffCoefficient: Double
    ) {
        setupConfigDao.saveSetupConfig(
            SetupConfigEntity(
                roofAreaSqFt = roofAreaSqFt,
                tankCapacityLiters = tankCapacityLiters,
                rooftopTypeName = rooftopTypeName,
                runoffCoefficient = runoffCoefficient
            )
        )
    }
    suspend fun clearSetup() {
        setupConfigDao.clearSetup()
    }

    // ── Tank ─────────────────────────────────────────────────────────────────
    val tankSnapshot: Flow<TankSnapshotEntity?> = tankSnapshotDao.getTankSnapshot()

    suspend fun updateTank(currentLiters: Double, tankCapacityLiters: Double) {
        tankSnapshotDao.saveTankSnapshot(
            TankSnapshotEntity(
                currentLiters = currentLiters,
                tankCapacityLiters = tankCapacityLiters
            )
        )
    }

    // ── Rainfall ──────────────────────────────────────────────────────────────
    val allRainfallEntries: Flow<List<RainfallLogEntity>> = rainfallLogDao.getAllEntries()
    val recentEntries: Flow<List<RainfallLogEntity>> = rainfallLogDao.getRecentEntries()
    val totalLiters: Flow<Double?> = rainfallLogDao.getTotalLiters()

    suspend fun logRainfall(entry: RainfallLogEntity): Long {
        return rainfallLogDao.insertEntry(entry)
    }

    suspend fun getEntryCount(): Int = rainfallLogDao.getEntryCount()

    // ── Alerts ────────────────────────────────────────────────────────────────
    val recentAlerts: Flow<List<AlertHistoryEntity>> = alertHistoryDao.getRecentAlerts()

    suspend fun saveAlert(level: String, message: String, fillPercent: Double) {
        alertHistoryDao.insertAlert(
            AlertHistoryEntity(
                alertLevel = level,
                message = message,
                fillPercent = fillPercent
            )
        )
        // Auto-prune alerts older than 30 days
        val thirtyDaysAgo = System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000)
        alertHistoryDao.deleteOldAlerts(thirtyDaysAgo)
    }

    suspend fun resetAllData() {
        rainfallLogDao.deleteAll()
        tankSnapshotDao.saveTankSnapshot(
            TankSnapshotEntity(currentLiters = 0.0, tankCapacityLiters = 0.0)
        )
    }
}
