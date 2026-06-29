package com.movievault.app.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepository(private val context: Context) {

    companion object {
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val DEFAULT_CATEGORY = stringPreferencesKey("default_category")
        val LAST_SEARCH_QUERY = stringPreferencesKey("last_search_query")
        val LAST_SEARCH_TIMESTAMP = stringPreferencesKey("last_search_timestamp")
        val GRID_VIEW_MODE = booleanPreferencesKey("grid_view_mode")
        val SORT_ORDER = stringPreferencesKey("sort_order")
    }

    val themeMode: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[THEME_MODE] ?: "system"
    }

    val defaultCategory: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[DEFAULT_CATEGORY] ?: "All"
    }

    val lastSearchQuery: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[LAST_SEARCH_QUERY] ?: ""
    }

    val isGridViewMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[GRID_VIEW_MODE] ?: true
    }

    val sortOrder: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[SORT_ORDER] ?: "date_desc"
    }

    suspend fun setThemeMode(mode: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_MODE] = mode
        }
    }

    suspend fun setDefaultCategory(category: String) {
        context.dataStore.edit { preferences ->
            preferences[DEFAULT_CATEGORY] = category
        }
    }

    suspend fun setLastSearchQuery(query: String) {
        context.dataStore.edit { preferences ->
            preferences[LAST_SEARCH_QUERY] = query
            preferences[LAST_SEARCH_TIMESTAMP] = System.currentTimeMillis().toString()
        }
    }

    suspend fun setGridViewMode(isGrid: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[GRID_VIEW_MODE] = isGrid
        }
    }

    suspend fun setSortOrder(order: String) {
        context.dataStore.edit { preferences ->
            preferences[SORT_ORDER] = order
        }
    }
}
