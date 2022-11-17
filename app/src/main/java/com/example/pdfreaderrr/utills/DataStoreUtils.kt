package com.pdf.pdfreader.pdfviewer.pdfeditor.imagetopdf.pdfconverter.jpgtopdf.pdfreaderfree.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DataStoreUtils(
    var context: Context,
    var datastore: DataStore<Preferences>? = context.createDataStore(name = "SETTINGS")
) {
    var mainobserver = MutableLiveData<Boolean>()
    fun callObserver() {
        mainobserver.postValue(true)
    }

    suspend fun saveValue(key: String, value: Any) = withContext(Dispatchers.IO){

        if (value is Int) {
            var dataStoreKey = preferencesKey<Int>(key)
            datastore?.edit { settings ->
                settings[dataStoreKey] = value
                callObserver()
            }
        } else if (value is Boolean) {
            val dataStoreKey = preferencesKey<Boolean>(key)
            datastore?.edit { settings ->
                settings[dataStoreKey] = value
            }
            callObserver()

        } else  {
            val dataStoreKey = preferencesKey<String>(key)
            datastore?.edit { settings ->
                settings[dataStoreKey] = value as String
            }
            callObserver()

        }
    }


    suspend fun getValue(key: String, dataTypeValue: DataTypeValue, defaultValue: Any) = withContext(Dispatchers.IO){
        when (dataTypeValue) {
            DataTypeValue.INT -> {
                val dataStoreKey = preferencesKey<Int>(key)
                val preferences = datastore?.data?.first()
                preferences?.get(dataStoreKey)?:defaultValue
            }

            DataTypeValue.BOOLEAN -> {
                val dataStoreKey = preferencesKey<Boolean>(key)
                val preferences = datastore?.data?.first()
                preferences?.get(dataStoreKey)?:defaultValue
            }

            DataTypeValue.STRING -> {
                val dataStoreKey = preferencesKey<String>(key)
                val preferences = datastore?.data?.first()
                preferences?.get(dataStoreKey)?:defaultValue
            }
        }


    }


     fun getValueFlow(key:String, dataTypeValue: DataTypeValue, defaultValue: Any)=
       run{
            when (dataTypeValue) {
            DataTypeValue.INT -> {
            val dataStoreKey = preferencesKey<Int>(key)
                datastore?.data?.map {
                    it[dataStoreKey] ?: defaultValue as Int
                }
        }
            DataTypeValue.BOOLEAN -> {
            val dataStoreKey = preferencesKey<Boolean>(key)
                datastore?.data?.map {
                    it[dataStoreKey] ?: defaultValue as Boolean
                }
        }
            DataTypeValue.STRING -> {
            val dataStoreKey = preferencesKey<String>(key)
                datastore?.data?.map {
                    it[dataStoreKey] ?: defaultValue as String
                }
        }
            }
        }
}