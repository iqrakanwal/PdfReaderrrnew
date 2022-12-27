package com.example.pdfreaderrr.interfaces

import androidx.room.*
import com.example.pdfreaderrr.PdfModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteFileDao {

    @Query("SELECT * FROM pdfmodel ORDER BY name DESC")
    fun getPdfFiles(): Flow<List<PdfModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFaviroteFile(note: PdfModel)

    @Update
    suspend fun updateNote(note: PdfModel)

    @Delete
    suspend fun deleteNote(note: PdfModel)

}