package com.jalsanchay.tracker.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.jalsanchay.tracker.data.TankSnapshotEntity;
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
public final class TankSnapshotDao_Impl implements TankSnapshotDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TankSnapshotEntity> __insertionAdapterOfTankSnapshotEntity;

  public TankSnapshotDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTankSnapshotEntity = new EntityInsertionAdapter<TankSnapshotEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `tank_snapshot` (`id`,`currentLiters`,`tankCapacityLiters`,`lastUpdated`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TankSnapshotEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindDouble(2, entity.getCurrentLiters());
        statement.bindDouble(3, entity.getTankCapacityLiters());
        statement.bindLong(4, entity.getLastUpdated());
      }
    };
  }

  @Override
  public Object saveTankSnapshot(final TankSnapshotEntity snapshot,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTankSnapshotEntity.insert(snapshot);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<TankSnapshotEntity> getTankSnapshot() {
    final String _sql = "SELECT * FROM tank_snapshot WHERE id = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tank_snapshot"}, new Callable<TankSnapshotEntity>() {
      @Override
      @Nullable
      public TankSnapshotEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCurrentLiters = CursorUtil.getColumnIndexOrThrow(_cursor, "currentLiters");
          final int _cursorIndexOfTankCapacityLiters = CursorUtil.getColumnIndexOrThrow(_cursor, "tankCapacityLiters");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final TankSnapshotEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final double _tmpCurrentLiters;
            _tmpCurrentLiters = _cursor.getDouble(_cursorIndexOfCurrentLiters);
            final double _tmpTankCapacityLiters;
            _tmpTankCapacityLiters = _cursor.getDouble(_cursorIndexOfTankCapacityLiters);
            final long _tmpLastUpdated;
            _tmpLastUpdated = _cursor.getLong(_cursorIndexOfLastUpdated);
            _result = new TankSnapshotEntity(_tmpId,_tmpCurrentLiters,_tmpTankCapacityLiters,_tmpLastUpdated);
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
  public Object getTankSnapshotOnce(final Continuation<? super TankSnapshotEntity> $completion) {
    final String _sql = "SELECT * FROM tank_snapshot WHERE id = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TankSnapshotEntity>() {
      @Override
      @Nullable
      public TankSnapshotEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCurrentLiters = CursorUtil.getColumnIndexOrThrow(_cursor, "currentLiters");
          final int _cursorIndexOfTankCapacityLiters = CursorUtil.getColumnIndexOrThrow(_cursor, "tankCapacityLiters");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final TankSnapshotEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final double _tmpCurrentLiters;
            _tmpCurrentLiters = _cursor.getDouble(_cursorIndexOfCurrentLiters);
            final double _tmpTankCapacityLiters;
            _tmpTankCapacityLiters = _cursor.getDouble(_cursorIndexOfTankCapacityLiters);
            final long _tmpLastUpdated;
            _tmpLastUpdated = _cursor.getLong(_cursorIndexOfLastUpdated);
            _result = new TankSnapshotEntity(_tmpId,_tmpCurrentLiters,_tmpTankCapacityLiters,_tmpLastUpdated);
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
