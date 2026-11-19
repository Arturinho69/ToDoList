package com.example.todolist_masco.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0004H\u0002J\b\u0010\u0015\u001a\u00020\u0012H\u0002J\u0014\u0010\u0016\u001a\u00020\u00122\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0012H\u0002J\u0012\u0010\u001a\u001a\u00020\u00122\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0014J\b\u0010\u001d\u001a\u00020\u0012H\u0014JB\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00040\"2\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u00042\u0016\b\u0002\u0010$\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0012\u0018\u00010%H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/example/todolist_masco/ui/TareasActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "busqueda", "", "dao", "Lcom/example/todolist_masco/data/TodoDao;", "getDao", "()Lcom/example/todolist_masco/data/TodoDao;", "dao$delegate", "Lkotlin/Lazy;", "filtro", "orden", "pendientes", "", "usuarioId", "", "actualizarLista", "", "aviso", "mensaje", "configurarBarra", "editarTarea", "tarea", "Lcom/example/todolist_masco/data/Tarea;", "mostrarTareas", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "ponerOpciones", "spinner", "Landroid/widget/Spinner;", "opciones", "", "seleccionado", "alCambiar", "Lkotlin/Function1;", "app_debug"})
public final class TareasActivity extends androidx.appcompat.app.AppCompatActivity {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy dao$delegate = null;
    private long usuarioId = 0L;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String filtro = "Todas";
    private boolean pendientes = false;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String busqueda = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String orden = "M\u00e1s recientes";
    
    public TareasActivity() {
        super();
    }
    
    private final com.example.todolist_masco.data.TodoDao getDao() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    private final void mostrarTareas() {
    }
    
    private final void actualizarLista() {
    }
    
    private final void editarTarea(com.example.todolist_masco.data.Tarea tarea) {
    }
    
    private final void ponerOpciones(android.widget.Spinner spinner, java.util.List<java.lang.String> opciones, java.lang.String seleccionado, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> alCambiar) {
    }
    
    private final void configurarBarra() {
    }
    
    private final void aviso(java.lang.String mensaje) {
    }
}