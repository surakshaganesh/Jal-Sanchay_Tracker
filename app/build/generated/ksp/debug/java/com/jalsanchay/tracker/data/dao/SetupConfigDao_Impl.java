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
import com.jalsanchay.tracker.data.SetupConfigEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SetupConfigDao_Impl implements SetupConfigDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SetupConfigEntity> __insertionAdapterOfSetupConfigEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearSetup;

  public SetupConfigDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSetupConfigEntity = new EntityInsertionAdapter<SetupConfigEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `setup_config` (`id`,`roofAreaSqFt`,`tankCapacityLiters`,`rooftopTypeName`,`runoffCoefficient`,`setupTimestamp`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SetupConfigEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindDouble(2, entity.getRoofAreaSqFt());
        statement.bindDouble(3, entity.getTankCapacityLiters());
        statement.bindString(4, entity.getRooftopTypeName());
        statement.bindDouble(5, entity.getRunoffCoefficient());
        statement.bindLong(6, entity.getSetupTimestamp());
      }
    };
    this.__preparedStmtOfClearSetup = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM setup_config";
        return _query;
      }
    };
  }

  @Override
  public Object saveSetupConfig(final SetupConfigEntity config,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSetupConfigEntity.insert(config);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearSetup(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearSetup.acquire();
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
          __preparedStmtOfClearSetup.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<SetupConfigEntity> getSetupConfig() {
    final String _sql = "SELECT * FROM setup_config WHERE id = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"setup_config"}, new Callable<SetupConfigEntity>() {
      @Override
      @Nullable
      public SetupConfigEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRoofAreaSqFt = CursorUtil.getColumnIndexOrThrow(_cursor, "roofAreaSqFt");
          final int _cursorIndexOfTankCapacityLiters = CursorUtil.getColumnIndexOrThrow(_cursor, "tankCapacityLiters");
          final int _cursorIndexOfRooftopTypeName = CursorUtil.getColumnIndexOrThrow(_cursor, "rooftopTypeName");
          final int _cursorIndexOfRunoffCoefficient = CursorUtil.getColumnIndexOrThrow(_cursor, "runoffCoefficient");
          final int _cursorIndexOfSetupTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "setupTimestamp");
          final SetupConfigEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final double _tmpRoofAreaSqFt;
            _tmpRoofAreaSqFt = _cursor.getDouble(_cursorIndexOfRoofAreaSqFt);
            final double _tmpTankCapacityLiters;
            _tmpTankCapacityLiters = _cursor.getDouble(_cursorIndexOfTankCapacityLiters);
            final String _tmpRooftopTypeName;
            _tmpRooftopTypeName = _cursor.getString(_cursorIndexOfRooftopTypeName);
            final double _tmpRunoffCoefficient;
            _tmpRunoffCoefficient = _cursor.getDouble(_cursorIndexOfRunoffCoefficient);
            final long _tmpSetupTimestamp;
            _tmpSetupTimestamp = _cursor.getLong(_cursorIndexOfSetupTimestamp);
            _result = new SetupConfigEntity(_tmpId,_tmpRoofAreaSqFt,_tmpTankCapacityLiters,_tmpRooftopTypeName,_tmpRunoffCoefficient,_tmpSetupTimestamp);
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
  public Object getSetupConfigOnce(final Continuation<? super SetupConfigEntity> $completion) {
    final String _sql = "SELECT * FROM setup_config WHERE id = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SetupConfigEntity>() {
      @Override
      @Nullable
      public SetupConfigEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRoofAreaSqFt = CursorUtil.getColumnIndexOrThrow(_cursor, "roofAreaSqFt");
          final int _cursorIndexOfTankCapacityLiters = CursorUtil.getColumnIndexOrThrow(_cursor, "tankCapacityLiters");
          final int _cursorIndexOfRooftopTypeName = CursorUtil.getColumnIndexOrThrow(_cursor, "rooftopTypeName");
          final int _cursorIndexOfRunoffCoefficient = CursorUtil.getColumnIndexOrThrow(_cursor, "runoffCoefficient");
          final int _cursorIndexOfSetupTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "setupTimestamp");
          final SetupConfigEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final double _tmpRoofAreaSqFt;
            _tmpRoofAreaSqFt = _cursor.getDouble(_cursorIndexOfRoofAreaSqFt);
            final double _tmpTankCapacityLiters;
            _tmpTankCapacityLiters = _cursor.getDouble(_cursorIndexOfTankCapacityLiters);
            final String _tmpRooftopTypeName;
            _tmpRooftopTypeName = _cursor.getString(_cursorIndexOfRooftopTypeName);
            final double _tmpRunoffCoefficient;
            _tmpRunoffCoefficient = _cursor.getDouble(_cursorIndexOfRunoffCoefficient);
            final long _tmpSetupTimestamp;
            _tmpSetupTimestamp = _cursor.getLong(_cursorIndexOfSetupTimestamp);
            _result = new SetupConfigEntity(_tmpId,_tmpRoofAreaSqFt,_tmpTankCapacityLiters,_tmpRooftopTypeName,_tmpRunoffCoefficient,_tmpSetupTimestamp);
          } else {
            _result = null;
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
