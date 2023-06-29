package com.bangkit.submissionfundamentalaplikasiandroid.setting

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.preferencesDataStore by preferencesDataStore("setting")

class Setting(context: Context) {
    private val settingDataStore = context.preferencesDataStore
    private val settingKey = booleanPreferencesKey("theme")

    fun getSetting(): Flow<Boolean> =
        settingDataStore.data.map { preferences ->
            preferences[settingKey] ?: false
        }

    suspend fun saveSetting(isDarkMode: Boolean) {
        settingDataStore.edit { preferences ->
            preferences[settingKey] = isDarkMode
        }
    }
}