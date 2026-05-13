package com.jalsanchay.tracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jalsanchay.tracker.data.*
import com.jalsanchay.tracker.data.dao.*

@Database(
    entities = [
        SetupConfigEntity::class,
        RainfallLogEntity::class,
        TankSnapshotEntity::class,
        AlertHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class JalSanchayDatabase : RoomDatabase() {
    abstract fun setupConfigDao(): SetupConfigDao
    abstract fun rainfallLogDao(): RainfallLogDao
    abstract fun tankSnapshotDao(): TankSnapshotDao
    abstract fun alertHistoryDao(): AlertHistoryDao

    companion object {
        @Volatile private var INSTANCE: JalSanchayDatabase? = null

        fun getInstance(context: Context): JalSanchayDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    JalSanchayDatabase::class.java,
                    "jalsanchay_db"
                )
                .fallbackToDestructiveMigration()
                .build()
                .also { INSTANCE = it }
            }
        }
    }
}
