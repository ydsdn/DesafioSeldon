package com.example.desafioseldon.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "favorites_prefs")

class FavoritesDataStore(private val context: Context) {
    private val favoriteKey = stringSetPreferencesKey("favorite_ids")

    val favoriteIds: Flow<Set<String>> = context.dataStore.data.map { prefs ->
        prefs[favoriteKey] ?: emptySet()
    }

    suspend fun toggleFavorite(deviceId: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[favoriteKey] ?: emptySet()
            if (current.contains(deviceId)) {
                prefs[favoriteKey] = current - deviceId
            } else {
                prefs[favoriteKey] = current + deviceId
            }
        }
    }
}