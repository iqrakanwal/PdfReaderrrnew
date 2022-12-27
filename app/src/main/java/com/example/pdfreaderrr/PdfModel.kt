package com.example.pdfreaderrr
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "pdfmodel")
class PdfModel {
    @PrimaryKey
    @ColumnInfo(name = "name")
    lateinit var name: String
    @ColumnInfo(name = "size")
    lateinit var size: String
    @ColumnInfo(name = "path")
    lateinit var path: String
    @ColumnInfo(name = "isSelected")
    var isSelected: Boolean = false
    @ColumnInfo(name = "isfav")
    var isfav: Boolean = false
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