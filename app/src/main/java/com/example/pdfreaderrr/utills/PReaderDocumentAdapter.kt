package com.example.pdfreaderrr.utills

import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class PReaderDocumentAdapter(private val pathName: File) : PrintDocumentAdapter() {

    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes,
        cancellationSignal: CancellationSignal?,
        callback: LayoutResultCallback,
        bundle: Bundle
    ) {
        if (cancellationSignal?.isCanceled == true) {
            callback.onLayoutCancelled()
            return
        } else {
            val builder = PrintDocumentInfo.Builder(pathName.absolutePath)
            builder.setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                .build()

            callback.onLayoutFinished(builder.build(), newAttributes == oldAttributes)
        }
    }

    override fun onWrite(
        pageRanges: Array<out PageRange>,
        destination: ParcelFileDescriptor,
        cancellationSignal: CancellationSignal?,
        callback: WriteResultCallback
    ) {
        try {
            // copy file from the input stream to the output stream
            FileInputStream(pathName).use { inStream ->
                FileOutputStream(destination.fileDescriptor).use { outStream ->
                    inStream.copyTo(outStream)
                }
            }

            if (cancellationSignal?.isCanceled == true) {
                callback.onWriteCancelled()
            } else {
                callback.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
            }

        } catch (e: Exception) {


            Log.e("jfjg", "${e}")

            callback.onWriteFailed(e.message)
        }
    }

}