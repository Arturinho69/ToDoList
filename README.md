# ToDoList MASCO — Android Studio

Proyecto escolar Android nativo en **Kotlin + XML**, con **ConstraintLayout** y **LinearLayout**, y base de datos local **Room**.

## Cómo abrirlo
1. Descomprime el ZIP.
2. En Android Studio: **File > Open** y selecciona la carpeta `ToDoList_MASCO` (la que contiene `settings.gradle.kts`).
3. Deja sincronizar Gradle con internet. Se recomienda JDK 17 y Android SDK 35.
4. Ejecuta `app` en tu emulador o teléfono Android (Android 7.0 o posterior).
5. Entra a **Regístrate** y crea una cuenta de prueba; después podrás iniciar sesión.

## Qué funciona
- Registro, login y sesión persistente en el mismo teléfono.
- Crear, editar, marcar como hecha, marcar prioridad y eliminar tareas.
- Lista de pendientes y filtro por categorías.
- Categorías personalizadas; se crean Personal, Hogar y Escuela con cada cuenta.
- Datos de perfil, edición de nombre/correo, cambio de contraseña y cierre de sesión.
- Preferencias de notificaciones guardadas.
- Recuperación *local de demostración* (sin servidor ni envío real de correo).

## Limitaciones conocidas
- **No** se envían notificaciones del sistema: la pantalla solo configura interruptores.
- **No** hay recuperación real de contraseña por correo; es un prototipo local. No usar para cuentas verdaderas. Las contraseñas se resumen con SHA-256 simple, sin salt: **no apto para producción**.
- Room usa `allowMainThreadQueries()` para simplificar este ejercicio; para una aplicación real, usar `suspend` y corrutinas.
- Los colores de las categorías se guardan, pero las tarjetas no se colorean con ellos.
- La interfaz sigue el estilo del boceto, pero no reproduce exactamente cada icono o tipografía.

## Archivos clave
- `app/src/main/java/com/example/todolist_masco/MainActivity.kt`: navegación y eventos.
- `app/src/main/java/com/example/todolist_masco/data/AppDatabase.kt`: entidades y consultas Room.
- `app/src/main/res/layout/`: diseños XML.
- `app/src/main/res/values/styles.xml`: paleta de colores.

**Si Android Studio indica que falta el Gradle Wrapper**, usa su opción de sincronización/instalación de Gradle; este ZIP incluye los archivos Gradle del proyecto pero no el wrapper binario. No se ha compilado en este entorno.
