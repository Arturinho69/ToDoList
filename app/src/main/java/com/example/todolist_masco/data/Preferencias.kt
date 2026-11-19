package com.example.todolist_masco.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStorePreferencias by preferencesDataStore(name = "preferencias")

object Preferencias {

    private val MODO_OSCURO = booleanPreferencesKey("modo_oscuro")
    private val NOTIFICACIONES = booleanPreferencesKey("notificaciones")
    private val IDIOMA = stringPreferencesKey("idioma")

    suspend fun guardarModoOscuro(context: Context, valor: Boolean) {
        context.dataStorePreferencias.edit { it[MODO_OSCURO] = valor }
    }

    suspend fun obtenerModoOscuro(context: Context): Boolean {
        return context.dataStorePreferencias.data.first()[MODO_OSCURO] ?: false
    }

    suspend fun guardarNotificaciones(context: Context, valor: Boolean) {
        context.dataStorePreferencias.edit { it[NOTIFICACIONES] = valor }
    }

    suspend fun obtenerNotificaciones(context: Context): Boolean {
        return context.dataStorePreferencias.data.first()[NOTIFICACIONES] ?: true
    }

    suspend fun guardarIdioma(context: Context, idioma: String) {
        context.dataStorePreferencias.edit { it[IDIOMA] = idioma }
    }

    suspend fun obtenerIdioma(context: Context): String {
        return context.dataStorePreferencias.data.first()[IDIOMA] ?: "Español"
    }

    suspend fun restablecer(context: Context) {
        context.dataStorePreferencias.edit {
            it[MODO_OSCURO] = false
            it[NOTIFICACIONES] = true
            it[IDIOMA] = "Español"
        }
    }
}