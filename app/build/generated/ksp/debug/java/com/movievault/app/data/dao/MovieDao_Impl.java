package com.movievault.app.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.movievault.app.data.entity.MovieEntity;
import java.lang.Class;
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
public final class MovieDao_Impl implements MovieDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MovieEntity> __insertionAdapterOfMovieEntity;

  private final EntityDeletionOrUpdateAdapter<MovieEntity> __deletionAdapterOfMovieEntity;

  private final EntityDeletionOrUpdateAdapter<MovieEntity> __updateAdapterOfMovieEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteMovieById;

  public MovieDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMovieEntity = new EntityInsertionAdapter<MovieEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `movies` (`id`,`title`,`year`,`poster_url`,`plot`,`director`,`actors`,`genre`,`imdb_rating`,`runtime`,`imdb_id`,`is_favorite`,`watch_status`,`user_rating`,`created_at`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MovieEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getYear());
        statement.bindString(4, entity.getPosterUrl());
        statement.bindString(5, entity.getPlot());
        statement.bindString(6, entity.getDirector());
        statement.bindString(7, entity.getActors());
        statement.bindString(8, entity.getGenre());
        statement.bindString(9, entity.getImdbRating());
        statement.bindString(10, entity.getRuntime());
        statement.bindString(11, entity.getImdbId());
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(12, _tmp);
        statement.bindString(13, entity.getWatchStatus());
        statement.bindDouble(14, entity.getUserRating());
        statement.bindLong(15, entity.getCreatedAt());
        statement.bindLong(16, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfMovieEntity = new EntityDeletionOrUpdateAdapter<MovieEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `movies` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MovieEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfMovieEntity = new EntityDeletionOrUpdateAdapter<MovieEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `movies` SET `id` = ?,`title` = ?,`year` = ?,`poster_url` = ?,`plot` = ?,`director` = ?,`actors` = ?,`genre` = ?,`imdb_rating` = ?,`runtime` = ?,`imdb_id` = ?,`is_favorite` = ?,`watch_status` = ?,`user_rating` = ?,`created_at` = ?,`updated_at` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MovieEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getYear());
        statement.bindString(4, entity.getPosterUrl());
        statement.bindString(5, entity.getPlot());
        statement.bindString(6, entity.getDirector());
        statement.bindString(7, entity.getActors());
        statement.bindString(8, entity.getGenre());
        statement.bindString(9, entity.getImdbRating());
        statement.bindString(10, entity.getRuntime());
        statement.bindString(11, entity.getImdbId());
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(12, _tmp);
        statement.bindString(13, entity.getWatchStatus());
        statement.bindDouble(14, entity.getUserRating());
        statement.bindLong(15, entity.getCreatedAt());
        statement.bindLong(16, entity.getUpdatedAt());
        statement.bindLong(17, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteMovieById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM movies WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertMovie(final MovieEntity movie, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfMovieEntity.insertAndReturnId(movie);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteMovie(final MovieEntity movie, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfMovieEntity.handle(movie);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateMovie(final MovieEntity movie, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfMovieEntity.handle(movie);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteMovieById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteMovieById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
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
          __preparedStmtOfDeleteMovieById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<MovieEntity>> getAllMovies() {
    final String _sql = "SELECT * FROM movies ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"movies"}, new Callable<List<MovieEntity>>() {
      @Override
      @NonNull
      public List<MovieEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "poster_url");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(_cursor, "director");
          final int _cursorIndexOfActors = CursorUtil.getColumnIndexOrThrow(_cursor, "actors");
          final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(_cursor, "genre");
          final int _cursorIndexOfImdbRating = CursorUtil.getColumnIndexOrThrow(_cursor, "imdb_rating");
          final int _cursorIndexOfRuntime = CursorUtil.getColumnIndexOrThrow(_cursor, "runtime");
          final int _cursorIndexOfImdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "imdb_id");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfWatchStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "watch_status");
          final int _cursorIndexOfUserRating = CursorUtil.getColumnIndexOrThrow(_cursor, "user_rating");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<MovieEntity> _result = new ArrayList<MovieEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MovieEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpYear;
            _tmpYear = _cursor.getString(_cursorIndexOfYear);
            final String _tmpPosterUrl;
            _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
            final String _tmpPlot;
            _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
            final String _tmpDirector;
            _tmpDirector = _cursor.getString(_cursorIndexOfDirector);
            final String _tmpActors;
            _tmpActors = _cursor.getString(_cursorIndexOfActors);
            final String _tmpGenre;
            _tmpGenre = _cursor.getString(_cursorIndexOfGenre);
            final String _tmpImdbRating;
            _tmpImdbRating = _cursor.getString(_cursorIndexOfImdbRating);
            final String _tmpRuntime;
            _tmpRuntime = _cursor.getString(_cursorIndexOfRuntime);
            final String _tmpImdbId;
            _tmpImdbId = _cursor.getString(_cursorIndexOfImdbId);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpWatchStatus;
            _tmpWatchStatus = _cursor.getString(_cursorIndexOfWatchStatus);
            final float _tmpUserRating;
            _tmpUserRating = _cursor.getFloat(_cursorIndexOfUserRating);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new MovieEntity(_tmpId,_tmpTitle,_tmpYear,_tmpPosterUrl,_tmpPlot,_tmpDirector,_tmpActors,_tmpGenre,_tmpImdbRating,_tmpRuntime,_tmpImdbId,_tmpIsFavorite,_tmpWatchStatus,_tmpUserRating,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getMovieById(final long id, final Continuation<? super MovieEntity> $completion) {
    final String _sql = "SELECT * FROM movies WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<MovieEntity>() {
      @Override
      @Nullable
      public MovieEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "poster_url");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(_cursor, "director");
          final int _cursorIndexOfActors = CursorUtil.getColumnIndexOrThrow(_cursor, "actors");
          final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(_cursor, "genre");
          final int _cursorIndexOfImdbRating = CursorUtil.getColumnIndexOrThrow(_cursor, "imdb_rating");
          final int _cursorIndexOfRuntime = CursorUtil.getColumnIndexOrThrow(_cursor, "runtime");
          final int _cursorIndexOfImdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "imdb_id");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfWatchStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "watch_status");
          final int _cursorIndexOfUserRating = CursorUtil.getColumnIndexOrThrow(_cursor, "user_rating");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final MovieEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpYear;
            _tmpYear = _cursor.getString(_cursorIndexOfYear);
            final String _tmpPosterUrl;
            _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
            final String _tmpPlot;
            _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
            final String _tmpDirector;
            _tmpDirector = _cursor.getString(_cursorIndexOfDirector);
            final String _tmpActors;
            _tmpActors = _cursor.getString(_cursorIndexOfActors);
            final String _tmpGenre;
            _tmpGenre = _cursor.getString(_cursorIndexOfGenre);
            final String _tmpImdbRating;
            _tmpImdbRating = _cursor.getString(_cursorIndexOfImdbRating);
            final String _tmpRuntime;
            _tmpRuntime = _cursor.getString(_cursorIndexOfRuntime);
            final String _tmpImdbId;
            _tmpImdbId = _cursor.getString(_cursorIndexOfImdbId);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpWatchStatus;
            _tmpWatchStatus = _cursor.getString(_cursorIndexOfWatchStatus);
            final float _tmpUserRating;
            _tmpUserRating = _cursor.getFloat(_cursorIndexOfUserRating);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new MovieEntity(_tmpId,_tmpTitle,_tmpYear,_tmpPosterUrl,_tmpPlot,_tmpDirector,_tmpActors,_tmpGenre,_tmpImdbRating,_tmpRuntime,_tmpImdbId,_tmpIsFavorite,_tmpWatchStatus,_tmpUserRating,_tmpCreatedAt,_tmpUpdatedAt);
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

  @Override
  public Object getMovieByImdbId(final String imdbId,
      final Continuation<? super MovieEntity> $completion) {
    final String _sql = "SELECT * FROM movies WHERE imdb_id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, imdbId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<MovieEntity>() {
      @Override
      @Nullable
      public MovieEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "poster_url");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(_cursor, "director");
          final int _cursorIndexOfActors = CursorUtil.getColumnIndexOrThrow(_cursor, "actors");
          final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(_cursor, "genre");
          final int _cursorIndexOfImdbRating = CursorUtil.getColumnIndexOrThrow(_cursor, "imdb_rating");
          final int _cursorIndexOfRuntime = CursorUtil.getColumnIndexOrThrow(_cursor, "runtime");
          final int _cursorIndexOfImdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "imdb_id");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfWatchStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "watch_status");
          final int _cursorIndexOfUserRating = CursorUtil.getColumnIndexOrThrow(_cursor, "user_rating");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final MovieEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpYear;
            _tmpYear = _cursor.getString(_cursorIndexOfYear);
            final String _tmpPosterUrl;
            _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
            final String _tmpPlot;
            _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
            final String _tmpDirector;
            _tmpDirector = _cursor.getString(_cursorIndexOfDirector);
            final String _tmpActors;
            _tmpActors = _cursor.getString(_cursorIndexOfActors);
            final String _tmpGenre;
            _tmpGenre = _cursor.getString(_cursorIndexOfGenre);
            final String _tmpImdbRating;
            _tmpImdbRating = _cursor.getString(_cursorIndexOfImdbRating);
            final String _tmpRuntime;
            _tmpRuntime = _cursor.getString(_cursorIndexOfRuntime);
            final String _tmpImdbId;
            _tmpImdbId = _cursor.getString(_cursorIndexOfImdbId);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpWatchStatus;
            _tmpWatchStatus = _cursor.getString(_cursorIndexOfWatchStatus);
            final float _tmpUserRating;
            _tmpUserRating = _cursor.getFloat(_cursorIndexOfUserRating);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new MovieEntity(_tmpId,_tmpTitle,_tmpYear,_tmpPosterUrl,_tmpPlot,_tmpDirector,_tmpActors,_tmpGenre,_tmpImdbRating,_tmpRuntime,_tmpImdbId,_tmpIsFavorite,_tmpWatchStatus,_tmpUserRating,_tmpCreatedAt,_tmpUpdatedAt);
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

  @Override
  public Flow<List<MovieEntity>> getFavoriteMovies() {
    final String _sql = "SELECT * FROM movies WHERE is_favorite = 1 ORDER BY updated_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"movies"}, new Callable<List<MovieEntity>>() {
      @Override
      @NonNull
      public List<MovieEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "poster_url");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(_cursor, "director");
          final int _cursorIndexOfActors = CursorUtil.getColumnIndexOrThrow(_cursor, "actors");
          final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(_cursor, "genre");
          final int _cursorIndexOfImdbRating = CursorUtil.getColumnIndexOrThrow(_cursor, "imdb_rating");
          final int _cursorIndexOfRuntime = CursorUtil.getColumnIndexOrThrow(_cursor, "runtime");
          final int _cursorIndexOfImdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "imdb_id");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfWatchStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "watch_status");
          final int _cursorIndexOfUserRating = CursorUtil.getColumnIndexOrThrow(_cursor, "user_rating");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<MovieEntity> _result = new ArrayList<MovieEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MovieEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpYear;
            _tmpYear = _cursor.getString(_cursorIndexOfYear);
            final String _tmpPosterUrl;
            _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
            final String _tmpPlot;
            _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
            final String _tmpDirector;
            _tmpDirector = _cursor.getString(_cursorIndexOfDirector);
            final String _tmpActors;
            _tmpActors = _cursor.getString(_cursorIndexOfActors);
            final String _tmpGenre;
            _tmpGenre = _cursor.getString(_cursorIndexOfGenre);
            final String _tmpImdbRating;
            _tmpImdbRating = _cursor.getString(_cursorIndexOfImdbRating);
            final String _tmpRuntime;
            _tmpRuntime = _cursor.getString(_cursorIndexOfRuntime);
            final String _tmpImdbId;
            _tmpImdbId = _cursor.getString(_cursorIndexOfImdbId);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpWatchStatus;
            _tmpWatchStatus = _cursor.getString(_cursorIndexOfWatchStatus);
            final float _tmpUserRating;
            _tmpUserRating = _cursor.getFloat(_cursorIndexOfUserRating);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new MovieEntity(_tmpId,_tmpTitle,_tmpYear,_tmpPosterUrl,_tmpPlot,_tmpDirector,_tmpActors,_tmpGenre,_tmpImdbRating,_tmpRuntime,_tmpImdbId,_tmpIsFavorite,_tmpWatchStatus,_tmpUserRating,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<MovieEntity>> getMoviesByWatchStatus(final String status) {
    final String _sql = "SELECT * FROM movies WHERE watch_status = ? ORDER BY updated_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, status);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"movies"}, new Callable<List<MovieEntity>>() {
      @Override
      @NonNull
      public List<MovieEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "poster_url");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(_cursor, "director");
          final int _cursorIndexOfActors = CursorUtil.getColumnIndexOrThrow(_cursor, "actors");
          final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(_cursor, "genre");
          final int _cursorIndexOfImdbRating = CursorUtil.getColumnIndexOrThrow(_cursor, "imdb_rating");
          final int _cursorIndexOfRuntime = CursorUtil.getColumnIndexOrThrow(_cursor, "runtime");
          final int _cursorIndexOfImdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "imdb_id");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfWatchStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "watch_status");
          final int _cursorIndexOfUserRating = CursorUtil.getColumnIndexOrThrow(_cursor, "user_rating");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<MovieEntity> _result = new ArrayList<MovieEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MovieEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpYear;
            _tmpYear = _cursor.getString(_cursorIndexOfYear);
            final String _tmpPosterUrl;
            _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
            final String _tmpPlot;
            _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
            final String _tmpDirector;
            _tmpDirector = _cursor.getString(_cursorIndexOfDirector);
            final String _tmpActors;
            _tmpActors = _cursor.getString(_cursorIndexOfActors);
            final String _tmpGenre;
            _tmpGenre = _cursor.getString(_cursorIndexOfGenre);
            final String _tmpImdbRating;
            _tmpImdbRating = _cursor.getString(_cursorIndexOfImdbRating);
            final String _tmpRuntime;
            _tmpRuntime = _cursor.getString(_cursorIndexOfRuntime);
            final String _tmpImdbId;
            _tmpImdbId = _cursor.getString(_cursorIndexOfImdbId);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpWatchStatus;
            _tmpWatchStatus = _cursor.getString(_cursorIndexOfWatchStatus);
            final float _tmpUserRating;
            _tmpUserRating = _cursor.getFloat(_cursorIndexOfUserRating);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new MovieEntity(_tmpId,_tmpTitle,_tmpYear,_tmpPosterUrl,_tmpPlot,_tmpDirector,_tmpActors,_tmpGenre,_tmpImdbRating,_tmpRuntime,_tmpImdbId,_tmpIsFavorite,_tmpWatchStatus,_tmpUserRating,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<MovieEntity>> searchMovies(final String query) {
    final String _sql = "SELECT * FROM movies WHERE title LIKE '%' || ? || '%' OR director LIKE '%' || ? || '%' OR actors LIKE '%' || ? || '%' ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    _argIndex = 3;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"movies"}, new Callable<List<MovieEntity>>() {
      @Override
      @NonNull
      public List<MovieEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "poster_url");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(_cursor, "director");
          final int _cursorIndexOfActors = CursorUtil.getColumnIndexOrThrow(_cursor, "actors");
          final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(_cursor, "genre");
          final int _cursorIndexOfImdbRating = CursorUtil.getColumnIndexOrThrow(_cursor, "imdb_rating");
          final int _cursorIndexOfRuntime = CursorUtil.getColumnIndexOrThrow(_cursor, "runtime");
          final int _cursorIndexOfImdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "imdb_id");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfWatchStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "watch_status");
          final int _cursorIndexOfUserRating = CursorUtil.getColumnIndexOrThrow(_cursor, "user_rating");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<MovieEntity> _result = new ArrayList<MovieEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MovieEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpYear;
            _tmpYear = _cursor.getString(_cursorIndexOfYear);
            final String _tmpPosterUrl;
            _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
            final String _tmpPlot;
            _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
            final String _tmpDirector;
            _tmpDirector = _cursor.getString(_cursorIndexOfDirector);
            final String _tmpActors;
            _tmpActors = _cursor.getString(_cursorIndexOfActors);
            final String _tmpGenre;
            _tmpGenre = _cursor.getString(_cursorIndexOfGenre);
            final String _tmpImdbRating;
            _tmpImdbRating = _cursor.getString(_cursorIndexOfImdbRating);
            final String _tmpRuntime;
            _tmpRuntime = _cursor.getString(_cursorIndexOfRuntime);
            final String _tmpImdbId;
            _tmpImdbId = _cursor.getString(_cursorIndexOfImdbId);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpWatchStatus;
            _tmpWatchStatus = _cursor.getString(_cursorIndexOfWatchStatus);
            final float _tmpUserRating;
            _tmpUserRating = _cursor.getFloat(_cursorIndexOfUserRating);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new MovieEntity(_tmpId,_tmpTitle,_tmpYear,_tmpPosterUrl,_tmpPlot,_tmpDirector,_tmpActors,_tmpGenre,_tmpImdbRating,_tmpRuntime,_tmpImdbId,_tmpIsFavorite,_tmpWatchStatus,_tmpUserRating,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<MovieEntity>> getMoviesByGenre(final String genre) {
    final String _sql = "SELECT * FROM movies WHERE genre LIKE '%' || ? || '%' ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, genre);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"movies"}, new Callable<List<MovieEntity>>() {
      @Override
      @NonNull
      public List<MovieEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "poster_url");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(_cursor, "director");
          final int _cursorIndexOfActors = CursorUtil.getColumnIndexOrThrow(_cursor, "actors");
          final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(_cursor, "genre");
          final int _cursorIndexOfImdbRating = CursorUtil.getColumnIndexOrThrow(_cursor, "imdb_rating");
          final int _cursorIndexOfRuntime = CursorUtil.getColumnIndexOrThrow(_cursor, "runtime");
          final int _cursorIndexOfImdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "imdb_id");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfWatchStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "watch_status");
          final int _cursorIndexOfUserRating = CursorUtil.getColumnIndexOrThrow(_cursor, "user_rating");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<MovieEntity> _result = new ArrayList<MovieEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MovieEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpYear;
            _tmpYear = _cursor.getString(_cursorIndexOfYear);
            final String _tmpPosterUrl;
            _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
            final String _tmpPlot;
            _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
            final String _tmpDirector;
            _tmpDirector = _cursor.getString(_cursorIndexOfDirector);
            final String _tmpActors;
            _tmpActors = _cursor.getString(_cursorIndexOfActors);
            final String _tmpGenre;
            _tmpGenre = _cursor.getString(_cursorIndexOfGenre);
            final String _tmpImdbRating;
            _tmpImdbRating = _cursor.getString(_cursorIndexOfImdbRating);
            final String _tmpRuntime;
            _tmpRuntime = _cursor.getString(_cursorIndexOfRuntime);
            final String _tmpImdbId;
            _tmpImdbId = _cursor.getString(_cursorIndexOfImdbId);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpWatchStatus;
            _tmpWatchStatus = _cursor.getString(_cursorIndexOfWatchStatus);
            final float _tmpUserRating;
            _tmpUserRating = _cursor.getFloat(_cursorIndexOfUserRating);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new MovieEntity(_tmpId,_tmpTitle,_tmpYear,_tmpPosterUrl,_tmpPlot,_tmpDirector,_tmpActors,_tmpGenre,_tmpImdbRating,_tmpRuntime,_tmpImdbId,_tmpIsFavorite,_tmpWatchStatus,_tmpUserRating,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getMovieCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM movies";
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

  @Override
  public Object getMovieCountByStatus(final String status,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM movies WHERE watch_status = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, status);
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

  @Override
  public Object getAllGenres(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT genre FROM movies";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
            _result.add(_item);
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
