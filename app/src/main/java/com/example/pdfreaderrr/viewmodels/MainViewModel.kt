package com.example.pdfreaderrr.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdfreaderrr.PdfModel
import com.example.pdfreaderrr.preference.UIModePreference
import com.example.pdfreaderrr.repositories.FilesRepositories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext.get

class MainViewModel(var application: Context,var filesRepositories: FilesRepositories):ViewModel() {
 public var files :LiveData<ArrayList<PdfModel>>? = filesRepositories.getPdfFile()
 private val uiDataStore = UIModePreference(application)


 /**
  * Load All Files
  */
 //   val isDarkTheme get() = uiDataStore.isNightMode
 val isDarkTheme get() = filesRepositories.isDarkTheme()

 /*   val getTheme get() = uiDataStore.get*/
 /*   fun getTheme() = run {
        var value = DEFAULT
        runBlocking {
            value = uiDataStore.getValue()
        }
        value
    }*/


 fun setDarkTheme(darktheme: String) {
  filesRepositories.setDarkTheme(darktheme)
 }
 val getUIMode = uiDataStore.uiMode
 val getVerticalView = uiDataStore.isVerticale
 val getNightMode = uiDataStore.isNightMode

 fun saveToDataStore(isNightMode: Boolean) {
  viewModelScope.launch(Dispatchers.IO) {
   uiDataStore.saveToDataStore(isNightMode)
  }
 }

 fun saveToNightMode(isNightMode: Boolean) {
  viewModelScope.launch(Dispatchers.IO) {
   uiDataStore.saveNightMode(isNightMode)
  }
 }

 fun saveView(isVertial: Boolean) {
  viewModelScope.launch(Dispatchers.IO) {
   uiDataStore.saveIsVerticalView(isVertial)
  }

 }


 fun setTheme(theme: String) {
  viewModelScope.launch(Dispatchers.IO) {
   uiDataStore.setTheme(theme)
  }


 }

 suspend fun getvalue() {
  Log.e("fffffffffd", getUIMode.first().toString())
 }

}