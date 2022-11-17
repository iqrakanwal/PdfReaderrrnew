package com.example.pdfreaderrr.repositories

import androidx.lifecycle.MutableLiveData
import com.example.pdfreaderrr.PdfModel
import com.example.pdfreaderrr.utills.Constants.Companion.DEFAULT
import com.example.pdfreaderrr.utills.Constants.Companion.NIGHT_MODE
import com.example.pdfreaderrr.utills.Constants.Companion.PURCHASED
import com.pdf.pdfreader.pdfviewer.pdfeditor.imagetopdf.pdfconverter.jpgtopdf.pdfreaderfree.utils.DataStoreUtils
import com.pdf.pdfreader.pdfviewer.pdfeditor.imagetopdf.pdfconverter.jpgtopdf.pdfreaderfree.utils.DataTypeValue
import kotlinx.coroutines.runBlocking
import pdfreader.pdfviewer.officetool.pdfscanner.models.FilesManager
import java.util.ArrayList

class FilesRepositories(private val dataStoreUtils: DataStoreUtils, var filesManager: FilesManager) {
    fun getPdfFile(): MutableLiveData<ArrayList<PdfModel>> {
        return filesManager.loadPdfFiles()
    }
    fun setDarkTheme(darktheme: Any) {
        runBlocking {
            dataStoreUtils.saveValue(NIGHT_MODE, darktheme)
        }
    }

    fun isDarkTheme() = run {
        var isDarktheme = DEFAULT
        runBlocking {
            isDarktheme =
                dataStoreUtils.getValue(
                    NIGHT_MODE,
                    DataTypeValue.STRING,
                    isDarktheme
                ) as String
        }
        isDarktheme
    }



    fun isPurchased() = run {
        var isPurchased = false
        runBlocking {
            isPurchased =
                dataStoreUtils.getValue(
                    PURCHASED,
                    DataTypeValue.BOOLEAN,
                    isPurchased
                ) as Boolean
        }
        isPurchased
    }

    fun setPurchase(purchase: Any) {
        runBlocking {
            dataStoreUtils.saveValue(PURCHASED, purchase)

        }

    }

}