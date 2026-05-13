package com.jalsanchay.tracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// ── Setup Config ──────────────────────────────────────────────────────────────
@Entity(tableName = "setup_config")
data class SetupConfigEntity(
    @PrimaryKey val id: Int = 1,
    val roofAreaSqFt: Double,
    val tankCapacityLiters: Double,
    val rooftopTypeName: String,
    val runoffCoefficient: Double,
    val setupTimestamp: Long = System.currentTimeMillis()
)

// ── Rainfall Log ──────────────────────────────────────────────────────────────
@Entity(tableName = "rainfall_log")
data class RainfallLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val rainfallMm: Double,
    val litersCollected: Double,
    val roofAreaSqFt: Double,
    val runoffCoefficient: Double,
    val tankLevelBefore: Double,
    val tankLevelAfter: Double,
    val overflowLiters: Double,
    val dayOfMonth: Int,
    val monthName: String,
    val year: Int,
    val timestamp: Long = System.currentTimeMillis()
)

// ── Tank Snapshot ─────────────────────────────────────────────────────────────
@Entity(tableName = "tank_snapshot")
data class TankSnapshotEntity(
    @PrimaryKey val id: Int = 1,
    val currentLiters: Double,
    val tankCapacityLiters: Double,
    val lastUpdated: Long = System.currentTimeMillis()
)

// ── Alert History ─────────────────────────────────────────────────────────────
@Entity(tableName = "alert_history")
data class AlertHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val alertLevel: String,   // "NORMAL", "STABLE", "WARNING", "OVERFLOW"
    val message: String,
    val fillPercent: Double,
    val timestamp: Long = System.currentTimeMillis()
)
