package com.jalsanchay.tracker.data.dao

import androidx.room.*
import com.jalsanchay.tracker.data.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SetupConfigDao {
    @Query("SELECT * FROM setup_config WHERE id = 1 LIMIT 1")
    fun getSetupConfig(): Flow<SetupConfigEntity?>

    @Query("SELECT * FROM setup_config WHERE id = 1 LIMIT 1")
    suspend fun getSetupConfigOnce(): SetupConfigEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSetupConfig(config: SetupConfigEntity)

    @Query("DELETE FROM setup_config")
    suspend fun clearSetup()
}

@Dao
interface RainfallLogDao {
    @Query("SELECT * FROM rainfall_log ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<RainfallLogEntity>>

    @Query("SELECT * FROM rainfall_log ORDER BY timestamp DESC LIMIT 30")
    fun getRecentEntries(): Flow<List<RainfallLogEntity>>

    @Query("SELECT * FROM rainfall_log WHERE monthName = :month AND year = :year ORDER BY dayOfMonth ASC")
    fun getEntriesForMonth(month: String, year: Int): Flow<List<RainfallLogEntity>>

    @Query("SELECT SUM(litersCollected) FROM rainfall_log")
    fun getTotalLiters(): Flow<Double?>

    @Query("SELECT SUM(rainfallMm) FROM rainfall_log WHERE monthName = :month AND year = :year")
    fun getMonthlyRainfall(month: String, year: Int): Flow<Double?>

    @Query("SELECT COUNT(*) FROM rainfall_log")
    suspend fun getEntryCount(): Int

    @Insert
    suspend fun insertEntry(entry: RainfallLogEntity): Long

    @Query("DELETE FROM rainfall_log")
    suspend fun deleteAll()
}

@Dao
interface TankSnapshotDao {
    @Query("SELECT * FROM tank_snapshot WHERE id = 1 LIMIT 1")
    fun getTankSnapshot(): Flow<TankSnapshotEntity?>

    @Query("SELECT * FROM tank_snapshot WHERE id = 1 LIMIT 1")
    suspend fun getTankSnapshotOnce(): TankSnapshotEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTankSnapshot(snapshot: TankSnapshotEntity)
}

@Dao
interface AlertHistoryDao {
    @Query("SELECT * FROM alert_history ORDER BY timestamp DESC LIMIT 50")
    fun getRecentAlerts(): Flow<List<AlertHistoryEntity>>

    @Insert
    suspend fun insertAlert(alert: AlertHistoryEntity)

    @Query("DELETE FROM alert_history WHERE timestamp < :cutoff")
    suspend fun deleteOldAlerts(cutoff: Long)
}
