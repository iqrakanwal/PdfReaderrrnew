package com.example.pdfreaderrr.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.example.pdfreaderrr.utills.Constants.Companion.DEFAULT
import com.example.pdfreaderrr.utills.Constants.Companion.THEME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UIModePreference(context: Context) {
    //1
    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = "ui_mode_preference"
    )
    //2
    suspend fun saveToDataStore(isNightMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[UI_MODE_KEY] = isNightMode
        }
    }


    suspend fun saveNightMode(isNightMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_NIIGHT_MODE] = isNightMode
        }
    }

    suspend fun setTheme(theme: String) {
        dataStore.edit { preferences ->
            preferences[preferencesKey<String>(THEME)] = theme
        }
    }


    suspend fun saveIsVerticalView(isVerticalView: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_VERTICAL] = isVerticalView
        }
    }


    val uiMode: Flow<Boolean> = dataStore.data
        .map { preferences ->
            val uiMode = preferences[UI_MODE_KEY] ?: false
            uiMode
        }
    //4
    val isVerticale: Flow<Boolean> = dataStore.data
        .map { preferences ->
            val uiMode = preferences[IS_VERTICAL] ?: false
            uiMode
        }
    val isNightMode: Flow<Boolean> = dataStore.data
        .map { preferences ->
            val uiMode = preferences[IS_NIIGHT_MODE] ?: false
            uiMode
        }


  /*  val gettheme: Flow<String> = dataStore.data
        .map { preferences ->
            val uiMode = preferences[THEME] ?: DEFAULT
            uiMode
        }*/




    suspend fun getValue() = withContext(
        Dispatchers.IO){
                val dataStoreKey = preferencesKey<String>(THEME)
                val preferences = dataStore?.data?.first()
                preferences?.get(dataStoreKey)?: DEFAULT

        }





    companion object {
        private val UI_MODE_KEY = preferencesKey<Boolean>("ui_mode")
        private val IS_VERTICAL = preferencesKey<Boolean>("is_vertical")
        private val IS_NIIGHT_MODE = preferencesKey<Boolean>("is_night_mode")

    }

}