package com.jalsanchay.tracker.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.jalsanchay.tracker.data.dao.AlertHistoryDao;
import com.jalsanchay.tracker.data.dao.AlertHistoryDao_Impl;
import com.jalsanchay.tracker.data.dao.RainfallLogDao;
import com.jalsanchay.tracker.data.dao.RainfallLogDao_Impl;
import com.jalsanchay.tracker.data.dao.SetupConfigDao;
import com.jalsanchay.tracker.data.dao.SetupConfigDao_Impl;
import com.jalsanchay.tracker.data.dao.TankSnapshotDao;
import com.jalsanchay.tracker.data.dao.TankSnapshotDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class JalSanchayDatabase_Impl extends JalSanchayDatabase {
  private volatile SetupConfigDao _setupConfigDao;

  private volatile RainfallLogDao _rainfallLogDao;

  private volatile TankSnapshotDao _tankSnapshotDao;

  private volatile AlertHistoryDao _alertHistoryDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `setup_config` (`id` INTEGER NOT NULL, `roofAreaSqFt` REAL NOT NULL, `tankCapacityLiters` REAL NOT NULL, `rooftopTypeName` TEXT NOT NULL, `runoffCoefficient` REAL NOT NULL, `setupTimestamp` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `rainfall_log` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `rainfallMm` REAL NOT NULL, `litersCollected` REAL NOT NULL, `roofAreaSqFt` REAL NOT NULL, `runoffCoefficient` REAL NOT NULL, `tankLevelBefore` REAL NOT NULL, `tankLevelAfter` REAL NOT NULL, `overflowLiters` REAL NOT NULL, `dayOfMonth` INTEGER NOT NULL, `monthName` TEXT NOT NULL, `year` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `tank_snapshot` (`id` INTEGER NOT NULL, `currentLiters` REAL NOT NULL, `tankCapacityLiters` REAL NOT NULL, `lastUpdated` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `alert_history` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `alertLevel` TEXT NOT NULL, `message` TEXT NOT NULL, `fillPercent` REAL NOT NULL, `timestamp` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '5c71e42f3eea87eebdab2cbd3177e4a7')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `setup_config`");
        db.execSQL("DROP TABLE IF EXISTS `rainfall_log`");
        db.execSQL("DROP TABLE IF EXISTS `tank_snapshot`");
        db.execSQL("DROP TABLE IF EXISTS `alert_history`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsSetupConfig = new HashMap<String, TableInfo.Column>(6);
        _columnsSetupConfig.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSetupConfig.put("roofAreaSqFt", new TableInfo.Column("roofAreaSqFt", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSetupConfig.put("tankCapacityLiters", new TableInfo.Column("tankCapacityLiters", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSetupConfig.put("rooftopTypeName", new TableInfo.Column("rooftopTypeName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSetupConfig.put("runoffCoefficient", new TableInfo.Column("runoffCoefficient", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSetupConfig.put("setupTimestamp", new TableInfo.Column("setupTimestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSetupConfig = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSetupConfig = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSetupConfig = new TableInfo("setup_config", _columnsSetupConfig, _foreignKeysSetupConfig, _indicesSetupConfig);
        final TableInfo _existingSetupConfig = TableInfo.read(db, "setup_config");
        if (!_infoSetupConfig.equals(_existingSetupConfig)) {
          return new RoomOpenHelper.ValidationResult(false, "setup_config(com.jalsanchay.tracker.data.SetupConfigEntity).\n"
                  + " Expected:\n" + _infoSetupConfig + "\n"
                  + " Found:\n" + _existingSetupConfig);
        }
        final HashMap<String, TableInfo.Column> _columnsRainfallLog = new HashMap<String, TableInfo.Column>(12);
        _columnsRainfallLog.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRainfallLog.put("rainfallMm", new TableInfo.Column("rainfallMm", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRainfallLog.put("litersCollected", new TableInfo.Column("litersCollected", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRainfallLog.put("roofAreaSqFt", new TableInfo.Column("roofAreaSqFt", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRainfallLog.put("runoffCoefficient", new TableInfo.Column("runoffCoefficient", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRainfallLog.put("tankLevelBefore", new TableInfo.Column("tankLevelBefore", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRainfallLog.put("tankLevelAfter", new TableInfo.Column("tankLevelAfter", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRainfallLog.put("overflowLiters", new TableInfo.Column("overflowLiters", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRainfallLog.put("dayOfMonth", new TableInfo.Column("dayOfMonth", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRainfallLog.put("monthName", new TableInfo.Column("monthName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRainfallLog.put("year", new TableInfo.Column("year", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRainfallLog.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRainfallLog = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRainfallLog = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRainfallLog = new TableInfo("rainfall_log", _columnsRainfallLog, _foreignKeysRainfallLog, _indicesRainfallLog);
        final TableInfo _existingRainfallLog = TableInfo.read(db, "rainfall_log");
        if (!_infoRainfallLog.equals(_existingRainfallLog)) {
          return new RoomOpenHelper.ValidationResult(false, "rainfall_log(com.jalsanchay.tracker.data.RainfallLogEntity).\n"
                  + " Expected:\n" + _infoRainfallLog + "\n"
                  + " Found:\n" + _existingRainfallLog);
        }
        final HashMap<String, TableInfo.Column> _columnsTankSnapshot = new HashMap<String, TableInfo.Column>(4);
        _columnsTankSnapshot.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTankSnapshot.put("currentLiters", new TableInfo.Column("currentLiters", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTankSnapshot.put("tankCapacityLiters", new TableInfo.Column("tankCapacityLiters", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTankSnapshot.put("lastUpdated", new TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTankSnapshot = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTankSnapshot = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTankSnapshot = new TableInfo("tank_snapshot", _columnsTankSnapshot, _foreignKeysTankSnapshot, _indicesTankSnapshot);
        final TableInfo _existingTankSnapshot = TableInfo.read(db, "tank_snapshot");
        if (!_infoTankSnapshot.equals(_existingTankSnapshot)) {
          return new RoomOpenHelper.ValidationResult(false, "tank_snapshot(com.jalsanchay.tracker.data.TankSnapshotEntity).\n"
                  + " Expected:\n" + _infoTankSnapshot + "\n"
                  + " Found:\n" + _existingTankSnapshot);
        }
        final HashMap<String, TableInfo.Column> _columnsAlertHistory = new HashMap<String, TableInfo.Column>(5);
        _columnsAlertHistory.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlertHistory.put("alertLevel", new TableInfo.Column("alertLevel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlertHistory.put("message", new TableInfo.Column("message", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlertHistory.put("fillPercent", new TableInfo.Column("fillPercent", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlertHistory.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAlertHistory = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAlertHistory = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAlertHistory = new TableInfo("alert_history", _columnsAlertHistory, _foreignKeysAlertHistory, _indicesAlertHistory);
        final TableInfo _existingAlertHistory = TableInfo.read(db, "alert_history");
        if (!_infoAlertHistory.equals(_existingAlertHistory)) {
          return new RoomOpenHelper.ValidationResult(false, "alert_history(com.jalsanchay.tracker.data.AlertHistoryEntity).\n"
                  + " Expected:\n" + _infoAlertHistory + "\n"
                  + " Found:\n" + _existingAlertHistory);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "5c71e42f3eea87eebdab2cbd3177e4a7", "38ee2ac0eb91b7b98522e2bf35cd52d4");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "setup_config","rainfall_log","tank_snapshot","alert_history");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `setup_config`");
      _db.execSQL("DELETE FROM `rainfall_log`");
      _db.execSQL("DELETE FROM `tank_snapshot`");
      _db.execSQL("DELETE FROM `alert_history`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(SetupConfigDao.class, SetupConfigDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RainfallLogDao.class, RainfallLogDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TankSnapshotDao.class, TankSnapshotDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AlertHistoryDao.class, AlertHistoryDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public SetupConfigDao setupConfigDao() {
    if (_setupConfigDao != null) {
      return _setupConfigDao;
    } else {
      synchronized(this) {
        if(_setupConfigDao == null) {
          _setupConfigDao = new SetupConfigDao_Impl(this);
        }
        return _setupConfigDao;
      }
    }
  }

  @Override
  public RainfallLogDao rainfallLogDao() {
    if (_rainfallLogDao != null) {
      return _rainfallLogDao;
    } else {
      synchronized(this) {
        if(_rainfallLogDao == null) {
          _rainfallLogDao = new RainfallLogDao_Impl(this);
        }
        return _rainfallLogDao;
      }
    }
  }

  @Override
  public TankSnapshotDao tankSnapshotDao() {
    if (_tankSnapshotDao != null) {
      return _tankSnapshotDao;
    } else {
      synchronized(this) {
        if(_tankSnapshotDao == null) {
          _tankSnapshotDao = new TankSnapshotDao_Impl(this);
        }
        return _tankSnapshotDao;
      }
    }
  }

  @Override
  public AlertHistoryDao alertHistoryDao() {
    if (_alertHistoryDao != null) {
      return _alertHistoryDao;
    } else {
      synchronized(this) {
        if(_alertHistoryDao == null) {
          _alertHistoryDao = new AlertHistoryDao_Impl(this);
        }
        return _alertHistoryDao;
      }
    }
  }
}
