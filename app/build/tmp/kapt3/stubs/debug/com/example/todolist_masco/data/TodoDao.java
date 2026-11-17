package com.example.todolist_masco.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0018\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\'J\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\bH\'J \u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\nH\'J\u0018\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\bH\'J\u0010\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0010\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\rH\'J\u0010\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0010\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u0019H\'J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\f2\u0006\u0010\u000e\u001a\u00020\bH\'J\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0011\u001a\u00020\nH\'J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0007\u001a\u00020\bH\'\u00a8\u0006\u001d"}, d2 = {"Lcom/example/todolist_masco/data/TodoDao;", "", "actualizarTarea", "", "tarea", "Lcom/example/todolist_masco/data/Tarea;", "cambiarClave", "id", "", "clave", "", "categorias", "", "Lcom/example/todolist_masco/data/Categoria;", "usuarioId", "editarUsuario", "nombre", "correo", "eliminarCategoria", "eliminarTarea", "insertarCategoria", "categoria", "insertarTarea", "insertarUsuario", "usuario", "Lcom/example/todolist_masco/data/Usuario;", "tareas", "usuarioPorCorreo", "usuarioPorId", "app_debug"})
@androidx.room.Dao()
public abstract interface TodoDao {
    
    @androidx.room.Insert()
    public abstract long insertarUsuario(@org.jetbrains.annotations.NotNull()
    com.example.todolist_masco.data.Usuario usuario);
    
    @androidx.room.Query(value = "SELECT * FROM usuarios WHERE correo = :correo LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract com.example.todolist_masco.data.Usuario usuarioPorCorreo(@org.jetbrains.annotations.NotNull()
    java.lang.String correo);
    
    @androidx.room.Query(value = "SELECT * FROM usuarios WHERE id = :id LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract com.example.todolist_masco.data.Usuario usuarioPorId(long id);
    
    @androidx.room.Query(value = "UPDATE usuarios SET nombre=:nombre, correo=:correo WHERE id=:id")
    public abstract void editarUsuario(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String nombre, @org.jetbrains.annotations.NotNull()
    java.lang.String correo);
    
    @androidx.room.Query(value = "UPDATE usuarios SET clave=:clave WHERE id=:id")
    public abstract void cambiarClave(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String clave);
    
    @androidx.room.Insert()
    public abstract long insertarCategoria(@org.jetbrains.annotations.NotNull()
    com.example.todolist_masco.data.Categoria categoria);
    
    @androidx.room.Query(value = "SELECT * FROM categorias WHERE usuarioId = :usuarioId ORDER BY id")
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.example.todolist_masco.data.Categoria> categorias(long usuarioId);
    
    @androidx.room.Query(value = "DELETE FROM categorias WHERE id=:id AND usuarioId=:usuarioId")
    public abstract void eliminarCategoria(long id, long usuarioId);
    
    @androidx.room.Insert()
    public abstract long insertarTarea(@org.jetbrains.annotations.NotNull()
    com.example.todolist_masco.data.Tarea tarea);
    
    @androidx.room.Update()
    public abstract void actualizarTarea(@org.jetbrains.annotations.NotNull()
    com.example.todolist_masco.data.Tarea tarea);
    
    @androidx.room.Delete()
    public abstract void eliminarTarea(@org.jetbrains.annotations.NotNull()
    com.example.todolist_masco.data.Tarea tarea);
    
    @androidx.room.Query(value = "SELECT * FROM tareas WHERE usuarioId=:usuarioId ORDER BY completada ASC, prioridad DESC, id DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.example.todolist_masco.data.Tarea> tareas(long usuarioId);
}