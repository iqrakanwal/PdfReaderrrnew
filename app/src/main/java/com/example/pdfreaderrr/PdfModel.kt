package com.example.pdfreaderrr

class PdfModel {
    lateinit var name: String
    lateinit var size: String
    lateinit var path: String
     var isSelected: Boolean =false
    fun setMFileDate(readableDate: String) {

    }


    fun setMAbsolute_path(string: String?) {
        path = string.toString()
    }

    fun setMFile_name(string: String?) {
        name = string.toString()
    }

    fun setFileType(name: Any) {

    }

    fun getMAbsolute_path(): String? {
        return path


    }

    fun setMParent_file(parent: String?) {

    }

    fun setMFile_size(readableSize: String) {
        size = readableSize
    }


}