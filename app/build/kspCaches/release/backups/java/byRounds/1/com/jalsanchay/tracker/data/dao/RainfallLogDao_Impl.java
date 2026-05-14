package com.jalsanchay.tracker.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.jalsanchay.tracker.data.RainfallLogEntity;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class RainfallLogDao_Impl implements RainfallLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RainfallLogEntity> __insertionAdapterOfRainfallLogEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public RainfallLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRainfallLogEntity = new EntityInsertionAdapter<RainfallLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `rainfall_log` (`id`,`rainfallMm`,`litersCollected`,`roofAreaSqFt`,`runoffCoefficient`,`tankLevelBefore`,`tankLevelAfter`,`overflowLiters`,`dayOfMonth`,`monthName`,`year`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RainfallLogEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindDouble(2, entity.getRainfallMm());
        statement.bindDouble(3, entity.getLitersCollected());
        statement.bindDouble(4, entity.getRoofAreaSqFt());
        statement.bindDouble(5, entity.getRunoffCoefficient());
        statement.bindDouble(6, entity.getTankLevelBefore());
        statement.bindDouble(7, entity.getTankLevelAfter());
        statement.bindDouble(8, entity.getOverflowLiters());
        statement.bindLong(9, entity.getDayOfMonth());
        statement.bindString(10, entity.getMonthName());
        statement.bindLong(11, entity.getYear());
        statement.bindLong(12, entity.getTimestamp());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM rainfall_log";
        return _query;
      }
    };
  }

  @Override
  public Object insertEntry(final RainfallLogEntity entry,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfRainfallLogEntity.insertAndReturnId(entry);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<RainfallLogEntity>> getAllEntries() {
    final String _sql = "SELECT * FROM rainfall_log ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"rainfall_log"}, new Callable<List<RainfallLogEntity>>() {
      @Override
      @NonNull
      public List<RainfallLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRainfallMm = CursorUtil.getColumnIndexOrThrow(_cursor, "rainfallMm");
          final int _cursorIndexOfLitersCollected = CursorUtil.getColumnIndexOrThrow(_cursor, "litersCollected");
          final int _cursorIndexOfRoofAreaSqFt = CursorUtil.getColumnIndexOrThrow(_cursor, "roofAreaSqFt");
          final int _cursorIndexOfRunoffCoefficient = CursorUtil.getColumnIndexOrThrow(_cursor, "runoffCoefficient");
          final int _cursorIndexOfTankLevelBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "tankLevelBefore");
          final int _cursorIndexOfTankLevelAfter = CursorUtil.getColumnIndexOrThrow(_cursor, "tankLevelAfter");
          final int _cursorIndexOfOverflowLiters = CursorUtil.getColumnIndexOrThrow(_cursor, "overflowLiters");
          final int _cursorIndexOfDayOfMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfMonth");
          final int _cursorIndexOfMonthName = CursorUtil.getColumnIndexOrThrow(_cursor, "monthName");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<RainfallLogEntity> _result = new ArrayList<RainfallLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RainfallLogEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final double _tmpRainfallMm;
            _tmpRainfallMm = _cursor.getDouble(_cursorIndexOfRainfallMm);
            final double _tmpLitersCollected;
            _tmpLitersCollected = _cursor.getDouble(_cursorIndexOfLitersCollected);
            final double _tmpRoofAreaSqFt;
            _tmpRoofAreaSqFt = _cursor.getDouble(_cursorIndexOfRoofAreaSqFt);
            final double _tmpRunoffCoefficient;
            _tmpRunoffCoefficient = _cursor.getDouble(_cursorIndexOfRunoffCoefficient);
            final double _tmpTankLevelBefore;
            _tmpTankLevelBefore = _cursor.getDouble(_cursorIndexOfTankLevelBefore);
            final double _tmpTankLevelAfter;
            _tmpTankLevelAfter = _cursor.getDouble(_cursorIndexOfTankLevelAfter);
            final double _tmpOverflowLiters;
            _tmpOverflowLiters = _cursor.getDouble(_cursorIndexOfOverflowLiters);
            final int _tmpDayOfMonth;
            _tmpDayOfMonth = _cursor.getInt(_cursorIndexOfDayOfMonth);
            final String _tmpMonthName;
            _tmpMonthName = _cursor.getString(_cursorIndexOfMonthName);
            final int _tmpYear;
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new RainfallLogEntity(_tmpId,_tmpRainfallMm,_tmpLitersCollected,_tmpRoofAreaSqFt,_tmpRunoffCoefficient,_tmpTankLevelBefore,_tmpTankLevelAfter,_tmpOverflowLiters,_tmpDayOfMonth,_tmpMonthName,_tmpYear,_tmpTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<RainfallLogEntity>> getRecentEntries() {
    final String _sql = "SELECT * FROM rainfall_log ORDER BY timestamp DESC LIMIT 30";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"rainfall_log"}, new Callable<List<RainfallLogEntity>>() {
      @Override
      @NonNull
      public List<RainfallLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRainfallMm = CursorUtil.getColumnIndexOrThrow(_cursor, "rainfallMm");
          final int _cursorIndexOfLitersCollected = CursorUtil.getColumnIndexOrThrow(_cursor, "litersCollected");
          final int _cursorIndexOfRoofAreaSqFt = CursorUtil.getColumnIndexOrThrow(_cursor, "roofAreaSqFt");
          final int _cursorIndexOfRunoffCoefficient = CursorUtil.getColumnIndexOrThrow(_cursor, "runoffCoefficient");
          final int _cursorIndexOfTankLevelBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "tankLevelBefore");
          final int _cursorIndexOfTankLevelAfter = CursorUtil.getColumnIndexOrThrow(_cursor, "tankLevelAfter");
          final int _cursorIndexOfOverflowLiters = CursorUtil.getColumnIndexOrThrow(_cursor, "overflowLiters");
          final int _cursorIndexOfDayOfMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfMonth");
          final int _cursorIndexOfMonthName = CursorUtil.getColumnIndexOrThrow(_cursor, "monthName");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<RainfallLogEntity> _result = new ArrayList<RainfallLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RainfallLogEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final double _tmpRainfallMm;
            _tmpRainfallMm = _cursor.getDouble(_cursorIndexOfRainfallMm);
            final double _tmpLitersCollected;
            _tmpLitersCollected = _cursor.getDouble(_cursorIndexOfLitersCollected);
            final double _tmpRoofAreaSqFt;
            _tmpRoofAreaSqFt = _cursor.getDouble(_cursorIndexOfRoofAreaSqFt);
            final double _tmpRunoffCoefficient;
            _tmpRunoffCoefficient = _cursor.getDouble(_cursorIndexOfRunoffCoefficient);
            final double _tmpTankLevelBefore;
            _tmpTankLevelBefore = _cursor.getDouble(_cursorIndexOfTankLevelBefore);
            final double _tmpTankLevelAfter;
            _tmpTankLevelAfter = _cursor.getDouble(_cursorIndexOfTankLevelAfter);
            final double _tmpOverflowLiters;
            _tmpOverflowLiters = _cursor.getDouble(_cursorIndexOfOverflowLiters);
            final int _tmpDayOfMonth;
            _tmpDayOfMonth = _cursor.getInt(_cursorIndexOfDayOfMonth);
            final String _tmpMonthName;
            _tmpMonthName = _cursor.getString(_cursorIndexOfMonthName);
            final int _tmpYear;
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new RainfallLogEntity(_tmpId,_tmpRainfallMm,_tmpLitersCollected,_tmpRoofAreaSqFt,_tmpRunoffCoefficient,_tmpTankLevelBefore,_tmpTankLevelAfter,_tmpOverflowLiters,_tmpDayOfMonth,_tmpMonthName,_tmpYear,_tmpTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<RainfallLogEntity>> getEntriesForMonth(final String month, final int year) {
    final String _sql = "SELECT * FROM rainfall_log WHERE monthName = ? AND year = ? ORDER BY dayOfMonth ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, month);
    _argIndex = 2;
    _statement.bindLong(_argIndex, year);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"rainfall_log"}, new Callable<List<RainfallLogEntity>>() {
      @Override
      @NonNull
      public List<RainfallLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRainfallMm = CursorUtil.getColumnIndexOrThrow(_cursor, "rainfallMm");
          final int _cursorIndexOfLitersCollected = CursorUtil.getColumnIndexOrThrow(_cursor, "litersCollected");
          final int _cursorIndexOfRoofAreaSqFt = CursorUtil.getColumnIndexOrThrow(_cursor, "roofAreaSqFt");
          final int _cursorIndexOfRunoffCoefficient = CursorUtil.getColumnIndexOrThrow(_cursor, "runoffCoefficient");
          final int _cursorIndexOfTankLevelBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "tankLevelBefore");
          final int _cursorIndexOfTankLevelAfter = CursorUtil.getColumnIndexOrThrow(_cursor, "tankLevelAfter");
          final int _cursorIndexOfOverflowLiters = CursorUtil.getColumnIndexOrThrow(_cursor, "overflowLiters");
          final int _cursorIndexOfDayOfMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfMonth");
          final int _cursorIndexOfMonthName = CursorUtil.getColumnIndexOrThrow(_cursor, "monthName");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<RainfallLogEntity> _result = new ArrayList<RainfallLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RainfallLogEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final double _tmpRainfallMm;
            _tmpRainfallMm = _cursor.getDouble(_cursorIndexOfRainfallMm);
            final double _tmpLitersCollected;
            _tmpLitersCollected = _cursor.getDouble(_cursorIndexOfLitersCollected);
            final double _tmpRoofAreaSqFt;
            _tmpRoofAreaSqFt = _cursor.getDouble(_cursorIndexOfRoofAreaSqFt);
            final double _tmpRunoffCoefficient;
            _tmpRunoffCoefficient = _cursor.getDouble(_cursorIndexOfRunoffCoefficient);
            final double _tmpTankLevelBefore;
            _tmpTankLevelBefore = _cursor.getDouble(_cursorIndexOfTankLevelBefore);
            final double _tmpTankLevelAfter;
            _tmpTankLevelAfter = _cursor.getDouble(_cursorIndexOfTankLevelAfter);
            final double _tmpOverflowLiters;
            _tmpOverflowLiters = _cursor.getDouble(_cursorIndexOfOverflowLiters);
            final int _tmpDayOfMonth;
            _tmpDayOfMonth = _cursor.getInt(_cursorIndexOfDayOfMonth);
            final String _tmpMonthName;
            _tmpMonthName = _cursor.getString(_cursorIndexOfMonthName);
            final int _tmpYear;
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new RainfallLogEntity(_tmpId,_tmpRainfallMm,_tmpLitersCollected,_tmpRoofAreaSqFt,_tmpRunoffCoefficient,_tmpTankLevelBefore,_tmpTankLevelAfter,_tmpOverflowLiters,_tmpDayOfMonth,_tmpMonthName,_tmpYear,_tmpTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Double> getTotalLiters() {
    final String _sql = "SELECT SUM(litersCollected) FROM rainfall_log";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"rainfall_log"}, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Double> getMonthlyRainfall(final String month, final int year) {
    final String _sql = "SELECT SUM(rainfallMm) FROM rainfall_log WHERE monthName = ? AND year = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, month);
    _argIndex = 2;
    _statement.bindLong(_argIndex, year);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"rainfall_log"}, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getEntryCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM rainfall_log";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
