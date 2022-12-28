package com.example.pdfreaderrr.interfaces

import com.example.pdfreaderrr.PdfModel

interface OptionMenuClickListener {
    fun info(uri:PdfModel)
    fun delete(uri: PdfModel)
    fun rename(uri: PdfModel)
}