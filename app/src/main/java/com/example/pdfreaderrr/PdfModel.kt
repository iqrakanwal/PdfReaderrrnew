package com.example.pdfreaderrr

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "fav_table")
data class PdfModel(
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "size")
    var size: String,

    @ColumnInfo(name = "path")
    var path: String,

    @ColumnInfo(name = "isSelected")
    var isSelected: Boolean,

    @ColumnInfo(name = "isfav")
    var isfav: Boolean = false,

    @ColumnInfo(name = "datamodifeid")
    var date: String,


)



