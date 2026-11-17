package com.example.todolist_masco.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TodoDao_Impl implements TodoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Usuario> __insertionAdapterOfUsuario;

  private final EntityInsertionAdapter<Categoria> __insertionAdapterOfCategoria;

  private final EntityInsertionAdapter<Tarea> __insertionAdapterOfTarea;

  private final EntityDeletionOrUpdateAdapter<Tarea> __deletionAdapterOfTarea;

  private final EntityDeletionOrUpdateAdapter<Tarea> __updateAdapterOfTarea;

  private final SharedSQLiteStatement __preparedStmtOfEditarUsuario;

  private final SharedSQLiteStatement __preparedStmtOfCambiarClave;

  private final SharedSQLiteStatement __preparedStmtOfEliminarCategoria;

  public TodoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUsuario = new EntityInsertionAdapter<Usuario>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `usuarios` (`id`,`nombre`,`correo`,`clave`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Usuario entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getNombre() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getNombre());
        }
        if (entity.getCorreo() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCorreo());
        }
        if (entity.getClave() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getClave());
        }
      }
    };
    this.__insertionAdapterOfCategoria = new EntityInsertionAdapter<Categoria>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `categorias` (`id`,`usuarioId`,`nombre`,`color`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Categoria entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUsuarioId());
        if (entity.getNombre() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNombre());
        }
        if (entity.getColor() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getColor());
        }
      }
    };
    this.__insertionAdapterOfTarea = new EntityInsertionAdapter<Tarea>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `tareas` (`id`,`usuarioId`,`nombre`,`descripcion`,`categoria`,`completada`,`prioridad`,`estado`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Tarea entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUsuarioId());
        if (entity.getNombre() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNombre());
        }
        if (entity.getDescripcion() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDescripcion());
        }
        if (entity.getCategoria() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCategoria());
        }
        final int _tmp = entity.getCompletada() ? 1 : 0;
        statement.bindLong(6, _tmp);
        final int _tmp_1 = entity.getPrioridad() ? 1 : 0;
        statement.bindLong(7, _tmp_1);
        if (entity.getEstado() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getEstado());
        }
      }
    };
    this.__deletionAdapterOfTarea = new EntityDeletionOrUpdateAdapter<Tarea>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `tareas` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Tarea entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTarea = new EntityDeletionOrUpdateAdapter<Tarea>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `tareas` SET `id` = ?,`usuarioId` = ?,`nombre` = ?,`descripcion` = ?,`categoria` = ?,`completada` = ?,`prioridad` = ?,`estado` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Tarea entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUsuarioId());
        if (entity.getNombre() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNombre());
        }
        if (entity.getDescripcion() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDescripcion());
        }
        if (entity.getCategoria() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCategoria());
        }
        final int _tmp = entity.getCompletada() ? 1 : 0;
        statement.bindLong(6, _tmp);
        final int _tmp_1 = entity.getPrioridad() ? 1 : 0;
        statement.bindLong(7, _tmp_1);
        if (entity.getEstado() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getEstado());
        }
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfEditarUsuario = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE usuarios SET nombre=?, correo=? WHERE id=?";
        return _query;
      }
    };
    this.__preparedStmtOfCambiarClave = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE usuarios SET clave=? WHERE id=?";
        return _query;
      }
    };
    this.__preparedStmtOfEliminarCategoria = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM categorias WHERE id=? AND usuarioId=?";
        return _query;
      }
    };
  }

  @Override
  public long insertarUsuario(final Usuario usuario) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfUsuario.insertAndReturnId(usuario);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long insertarCategoria(final Categoria categoria) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfCategoria.insertAndReturnId(categoria);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long insertarTarea(final Tarea tarea) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfTarea.insertAndReturnId(tarea);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void eliminarTarea(final Tarea tarea) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTarea.handle(tarea);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void actualizarTarea(final Tarea tarea) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfTarea.handle(tarea);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void editarUsuario(final long id, final String nombre, final String correo) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfEditarUsuario.acquire();
    int _argIndex = 1;
    if (nombre == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, nombre);
    }
    _argIndex = 2;
    if (correo == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, correo);
    }
    _argIndex = 3;
    _stmt.bindLong(_argIndex, id);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfEditarUsuario.release(_stmt);
    }
  }

  @Override
  public void cambiarClave(final long id, final String clave) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfCambiarClave.acquire();
    int _argIndex = 1;
    if (clave == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, clave);
    }
    _argIndex = 2;
    _stmt.bindLong(_argIndex, id);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfCambiarClave.release(_stmt);
    }
  }

  @Override
  public void eliminarCategoria(final long id, final long usuarioId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfEliminarCategoria.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, usuarioId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfEliminarCategoria.release(_stmt);
    }
  }

  @Override
  public Usuario usuarioPorCorreo(final String correo) {
    final String _sql = "SELECT * FROM usuarios WHERE correo = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (correo == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, correo);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
      final int _cursorIndexOfCorreo = CursorUtil.getColumnIndexOrThrow(_cursor, "correo");
      final int _cursorIndexOfClave = CursorUtil.getColumnIndexOrThrow(_cursor, "clave");
      final Usuario _result;
      if (_cursor.moveToFirst()) {
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final String _tmpNombre;
        if (_cursor.isNull(_cursorIndexOfNombre)) {
          _tmpNombre = null;
        } else {
          _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
        }
        final String _tmpCorreo;
        if (_cursor.isNull(_cursorIndexOfCorreo)) {
          _tmpCorreo = null;
        } else {
          _tmpCorreo = _cursor.getString(_cursorIndexOfCorreo);
        }
        final String _tmpClave;
        if (_cursor.isNull(_cursorIndexOfClave)) {
          _tmpClave = null;
        } else {
          _tmpClave = _cursor.getString(_cursorIndexOfClave);
        }
        _result = new Usuario(_tmpId,_tmpNombre,_tmpCorreo,_tmpClave);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Usuario usuarioPorId(final long id) {
    final String _sql = "SELECT * FROM usuarios WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
      final int _cursorIndexOfCorreo = CursorUtil.getColumnIndexOrThrow(_cursor, "correo");
      final int _cursorIndexOfClave = CursorUtil.getColumnIndexOrThrow(_cursor, "clave");
      final Usuario _result;
      if (_cursor.moveToFirst()) {
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final String _tmpNombre;
        if (_cursor.isNull(_cursorIndexOfNombre)) {
          _tmpNombre = null;
        } else {
          _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
        }
        final String _tmpCorreo;
        if (_cursor.isNull(_cursorIndexOfCorreo)) {
          _tmpCorreo = null;
        } else {
          _tmpCorreo = _cursor.getString(_cursorIndexOfCorreo);
        }
        final String _tmpClave;
        if (_cursor.isNull(_cursorIndexOfClave)) {
          _tmpClave = null;
        } else {
          _tmpClave = _cursor.getString(_cursorIndexOfClave);
        }
        _result = new Usuario(_tmpId,_tmpNombre,_tmpCorreo,_tmpClave);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Categoria> categorias(final long usuarioId) {
    final String _sql = "SELECT * FROM categorias WHERE usuarioId = ? ORDER BY id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, usuarioId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUsuarioId = CursorUtil.getColumnIndexOrThrow(_cursor, "usuarioId");
      final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
      final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
      final List<Categoria> _result = new ArrayList<Categoria>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Categoria _item;
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final long _tmpUsuarioId;
        _tmpUsuarioId = _cursor.getLong(_cursorIndexOfUsuarioId);
        final String _tmpNombre;
        if (_cursor.isNull(_cursorIndexOfNombre)) {
          _tmpNombre = null;
        } else {
          _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
        }
        final String _tmpColor;
        if (_cursor.isNull(_cursorIndexOfColor)) {
          _tmpColor = null;
        } else {
          _tmpColor = _cursor.getString(_cursorIndexOfColor);
        }
        _item = new Categoria(_tmpId,_tmpUsuarioId,_tmpNombre,_tmpColor);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Tarea> tareas(final long usuarioId) {
    final String _sql = "SELECT * FROM tareas WHERE usuarioId=? ORDER BY completada ASC, prioridad DESC, id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, usuarioId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUsuarioId = CursorUtil.getColumnIndexOrThrow(_cursor, "usuarioId");
      final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
      final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
      final int _cursorIndexOfCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "categoria");
      final int _cursorIndexOfCompletada = CursorUtil.getColumnIndexOrThrow(_cursor, "completada");
      final int _cursorIndexOfPrioridad = CursorUtil.getColumnIndexOrThrow(_cursor, "prioridad");
      final int _cursorIndexOfEstado = CursorUtil.getColumnIndexOrThrow(_cursor, "estado");
      final List<Tarea> _result = new ArrayList<Tarea>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Tarea _item;
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final long _tmpUsuarioId;
        _tmpUsuarioId = _cursor.getLong(_cursorIndexOfUsuarioId);
        final String _tmpNombre;
        if (_cursor.isNull(_cursorIndexOfNombre)) {
          _tmpNombre = null;
        } else {
          _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
        }
        final String _tmpDescripcion;
        if (_cursor.isNull(_cursorIndexOfDescripcion)) {
          _tmpDescripcion = null;
        } else {
          _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
        }
        final String _tmpCategoria;
        if (_cursor.isNull(_cursorIndexOfCategoria)) {
          _tmpCategoria = null;
        } else {
          _tmpCategoria = _cursor.getString(_cursorIndexOfCategoria);
        }
        final boolean _tmpCompletada;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfCompletada);
        _tmpCompletada = _tmp != 0;
        final boolean _tmpPrioridad;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfPrioridad);
        _tmpPrioridad = _tmp_1 != 0;
        final String _tmpEstado;
        if (_cursor.isNull(_cursorIndexOfEstado)) {
          _tmpEstado = null;
        } else {
          _tmpEstado = _cursor.getString(_cursorIndexOfEstado);
        }
        _item = new Tarea(_tmpId,_tmpUsuarioId,_tmpNombre,_tmpDescripcion,_tmpCategoria,_tmpCompletada,_tmpPrioridad,_tmpEstado);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
