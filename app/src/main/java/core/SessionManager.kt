package com.example.todolist_masco.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by
preferencesDataStore(name = "user_session")

class SessionManager(private val context: Context) {

    companion object {
        val USER_ID = longPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("user_name")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    val isLoggedIn: Flow<Boolean> =
        context.dataStore.data.map { it[IS_LOGGED_IN] ?: false }

    val userId: Flow<Long> =
        context.dataStore.data.map { it[USER_ID] ?: 0L }

    val userName: Flow<String?> =
        context.dataStore.data.map { it[USER_NAME] }

    suspend fun saveSession(userId: Long, userName: String) {
        context.dataStore.edit {
            it[USER_ID] = userId
            it[USER_NAME] = userName
            it[IS_LOGGED_IN] = true
        }
    }

    suspend fun logout() {
        context.dataStore.edit { it.clear() }
    }
}