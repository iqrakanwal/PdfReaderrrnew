package com.example.pdfreaderrr

import android.app.Application
import android.app.ApplicationErrorReport
import android.content.Context
import com.example.pdfreaderrr.repositories.FilesRepositories
import com.example.pdfreaderrr.viewmodels.MainViewModel
import com.pdf.pdfreader.pdfviewer.pdfeditor.imagetopdf.pdfconverter.jpgtopdf.pdfreaderfree.utils.DataStoreUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import pdfreader.pdfviewer.officetool.pdfscanner.models.FilesManager

class BaseClass() : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: BaseClass? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

    }

    val viewModelModule = module {
        viewModel { MainViewModel(get(), get()) }
        single { FilesRepositories(get(), get()) }
        single { FilesManager(get()) }
        single { DataStoreUtils(get()) }
    }


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseClass)
            modules(listOf(viewModelModule))
        }
    }
}