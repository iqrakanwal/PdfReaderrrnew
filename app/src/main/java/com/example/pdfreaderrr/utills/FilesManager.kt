package pdfreader.pdfviewer.officetool.pdfscanner.models

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.database.Cursor
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import com.example.pdfreaderrr.PdfModel
import com.example.pdfreaderrr.utills.FileTypes
import java.io.File
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.pow


@Keep
class FilesManager(mContext: Context) {
    val pdf = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf")
    val doc = MimeTypeMap.getSingleton().getMimeTypeFromExtension("doc")
    val docx = MimeTypeMap.getSingleton().getMimeTypeFromExtension("docx")
    val xls = MimeTypeMap.getSingleton().getMimeTypeFromExtension("xls")
    val xlsx = MimeTypeMap.getSingleton().getMimeTypeFromExtension("xlsx")
    val ppt = MimeTypeMap.getSingleton().getMimeTypeFromExtension("ppt")
    val pptx = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pptx")
    val rtx = MimeTypeMap.getSingleton().getMimeTypeFromExtension("rtx")
    val rtf = MimeTypeMap.getSingleton().getMimeTypeFromExtension("rtf")

    val context = mContext
    private var allFilesList = MutableLiveData<ArrayList<PdfModel>>()
    private var pdfFilesList = MutableLiveData<ArrayList<PdfModel>>()
    private var wordFilesList = MutableLiveData<ArrayList<PdfModel>>()
    private var pptFilesList = MutableLiveData<ArrayList<PdfModel>>()
    private var excelFilesList = MutableLiveData<ArrayList<PdfModel>>()

    fun loadAllFiles(): MutableLiveData<ArrayList<PdfModel>> {
        try {
            allFilesList.postValue(
                readFiles(

                    arrayOf(pdf, doc, docx, xls, xlsx, ppt, pptx, rtx, rtf),
                    (MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                            + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                            + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                            + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                            + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                            + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                            + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                            + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                            + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?")
                )
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return allFilesList
    }

    fun loadPdfFiles(): MutableLiveData<ArrayList<PdfModel>> {
        try {
            pdfFilesList.postValue(
                readFiles(

                    arrayOf(pdf),
                    ((MediaStore.Files.FileColumns.MIME_TYPE + "=?"))
                )
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return pdfFilesList
    }

    fun loadWordFiles(): MutableLiveData<ArrayList<PdfModel>> {
        try {
            wordFilesList.postValue(
                readFiles(
                    arrayOf(doc, docx),
                    (MediaStore.Files.FileColumns.MIME_TYPE + "=?" + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?")
                )
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return wordFilesList
    }

    fun loadPPTFiles(): MutableLiveData<ArrayList<PdfModel>> {
        try {
            pptFilesList.postValue(
                readFiles(
                    arrayOf(ppt, pptx),
                    (MediaStore.Files.FileColumns.MIME_TYPE + "=?" + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?")
                )
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return pptFilesList
    }

    fun loadExcelFiles(): MutableLiveData<ArrayList<PdfModel>> {
        try {
            excelFilesList.postValue(
                readFiles(
                    arrayOf(xls, xlsx),
                    (MediaStore.Files.FileColumns.MIME_TYPE + "=?" + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?")
                )
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return excelFilesList
    }

    private fun readFiles(
        args: Array<String?>,
        where: String
    ): ArrayList<PdfModel> {
        val fetchedFiles = ArrayList<PdfModel>()
        var fileCursorExternal: Cursor? = null
        var fileCursorInternal: Cursor? = null
        try {
            //Tables
            val tableExternal = MediaStore.Files.getContentUri("external")
            val tableInternal = MediaStore.Files.getContentUri("internal")

            //Column
            val column = arrayOf(
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.MediaColumns.DATA,
                MediaStore.MediaColumns.TITLE,
                MediaStore.MediaColumns.SIZE,
                MediaStore.Files.FileColumns.DATE_MODIFIED,

                )

            //Sort by date
            val orderBy = MediaStore.Files.FileColumns.DATE_MODIFIED

            fileCursorExternal = context.contentResolver.query(
                tableExternal,
                column,
                where,
                args, "$orderBy DESC"
            )
            while (fileCursorExternal!!.moveToNext()) {
                fetchedFiles.add(setPdfModel(fileCursorExternal))
            }
            fileCursorInternal = context.contentResolver.query(
                tableInternal,
                column,
                where,
                args,
                "$orderBy DESC"
            )
            while (fileCursorInternal!!.moveToNext()) {
                fetchedFiles.add(setPdfModel(fileCursorInternal))
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        } finally {
            fileCursorExternal?.close()
            fileCursorInternal?.close()
        }
        return fetchedFiles
    }

    private fun setPdfModel(cursor: Cursor): PdfModel {
        val pdfModel = PdfModel()
        if (cursor != null) {
            try {
                pdfModel.setMFileDate(
                    getReadableDate(
                        cursor.getLong(
                            cursor.getColumnIndexOrThrow(
                                MediaStore.Files.FileColumns.DATE_MODIFIED
                            )
                        )
                    )
                )
                pdfModel.setMAbsolute_path(
                    cursor.getString(
                        cursor.getColumnIndexOrThrow(
                            MediaStore.MediaColumns.DATA
                        )
                    )
                )
                pdfModel.setMFile_name(
                    cursor.getString(
                        cursor.getColumnIndexOrThrow(
                            MediaStore.MediaColumns.TITLE
                        )
                    )
                )
                pdfModel.setMFile_size(
                    getReadableSize(
                        cursor.getLong(
                            cursor.getColumnIndexOrThrow(
                                MediaStore.MediaColumns.SIZE
                            )
                        )
                    )
                )
                pdfModel.isSelected = false
                pdfModel.setFileType(getFileType(pdfModel.getMAbsolute_path()).name)
                if (pdfModel.getMAbsolute_path() != null) {
                    val file = File(pdfModel.getMAbsolute_path())
                    pdfModel.setMParent_file(file.parent)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        return pdfModel
    }

    private fun getReadableDate(dateVal: Long): String {
        try {
            var date = dateVal
            date *= 1000L
            return SimpleDateFormat("dd MMM yyyy").format(Date(date))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return ""

    }

    private fun getReadableSize(size: Long): String {
        try {
            if (size <= 0) return "0"
            val units = arrayOf("B", "kB", "MB", "GB", "TB")
            val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
            return DecimalFormat("#,##0.#").format(size / 1024.0.pow(digitGroups.toDouble()))
                .toString() + " " + units[digitGroups]
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return ""

    }

    fun getFileType(filePath: String?): FileTypes {
        try {
            if (filePath?.endsWith(".pdf")!!) {
                return FileTypes.PDF
            } else if (filePath.endsWith(".doc") || filePath.endsWith(".docx") || filePath.endsWith(
                    ".rtf"
                ) || filePath.endsWith(
                    ".rtx"
                )
            ) {
                return FileTypes.WORD
            } else if (filePath.endsWith(".xls") || filePath.endsWith(".xlsx") || filePath.endsWith(
                    ".lsx"
                )
            ) {
                return FileTypes.EXCEL
            } else if (filePath.endsWith(".ppt") || filePath.endsWith(".pptx")) {
                return FileTypes.PPT
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return FileTypes.PDF
    }

   /* fun getFilePosition(isRename: Boolean, pdfModel: PdfModel?, list: MutableList<PdfModel>): Int {
        try {
            list.forEachIndexed { i, value ->
                if (isRename) {
                    if (pdfModel!!.getOldFileParentFileh().equals(value.getMParent_file())
                        && pdfModel.getOldFilePath()!!.equals(value.getMAbsolute_path())
                        && pdfModel.getOldFileName().equals(value.getMFile_name())
                    ) return i
                } else {
                    if (pdfModel?.equals(value)!!) return i
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return -1
    }

    fun getFilePositionWithAds(
        isRename: Boolean,
        pdfModel: PdfModel?,
        list: MutableList<ListRowItemAdapter.BaseModel?>
    ): Int {
        try {
            list.forEachIndexed { i, value ->
                if (value is ListRowItemAdapter.FileModel) {
                    val filemodel = value.pdfModel
                    if (isRename) {

                        if (pdfModel!!.getOldFileParentFileh().equals(filemodel.getMParent_file())
                            && pdfModel.getOldFilePath()!!.equals(filemodel.getMAbsolute_path())
                            && pdfModel.getOldFileName().equals(filemodel.getMFile_name())
                        ) return i
                    } else {
                        if (pdfModel?.equals(filemodel)!!) return i
                    }

                }

            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return -1
    }
*/
    fun checkFilesWithDB(
        liveDataFromDb: List<PdfModel>?,
        fetchedFiles: ArrayList<PdfModel>?
    ): ArrayList<PdfModel> {
        val resultFiles = ArrayList<PdfModel>()
        try {
            if (fetchedFiles != null) {
                if (fetchedFiles.size > 0) {
                    for (file in fetchedFiles) {
                        val fileInDb = isFileExistsInDB(file, liveDataFromDb)
                        if (fileInDb != null) {
                            resultFiles.add(fileInDb)
                        } else {
                            resultFiles.add(file)
                        }

                    }
                }
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return resultFiles
    }

    private fun isFileExistsInDB(
        pdfModel: PdfModel,
        liveDataFromDb: List<PdfModel>?
    ): PdfModel? {
        if (liveDataFromDb != null) {
            try {
                val count = liveDataFromDb.size
                for (i in 0 until count) {
                    if (pdfModel.equals(liveDataFromDb.get(i))) {
                        return liveDataFromDb.get(i)
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        return null
    }


/*
    fun viewPdfFileOnActivity(pdfModel: PdfModel?, context: Context?, isWordFile: Boolean) {
        val intent = Intent(context, ViewPDFActivity::class.java)
        intent.putExtra("file_uri", pdfModel?.getMAbsolute_path())
        intent.putExtra("file_name", pdfModel?.getMFile_name())
        intent.putExtra("file_id", pdfModel?.getMid())
        intent.putExtra("isWordFile", isWordFile)
        context?.startActivity(intent)
    }

    fun viewExcelFileOnActivity(pdfModel: PdfModel?, context: Context?) {
        */
/* val intent = Intent(context, ViewSheetActivity::class.java)
         intent.putExtra("file_uri", pdfModel?.getMAbsolute_path())
         intent.putExtra("file_name", pdfModel?.getMFile_name())
         context?.startActivity(intent)*//*

        val intent = Intent(context, ViewExcelActivity::class.java)
        intent.putExtra("file_uri", pdfModel?.getMAbsolute_path())
        intent.putExtra("file_name", pdfModel?.getMFile_name())
        intent.putExtra("file_id", pdfModel?.getMid())
        context?.startActivity(intent)
    }

    fun viewWordFileOnActivity(pdfModel: PdfModel?, context: Context?) {
        val intent = Intent(context, ViewWordActivity::class.java)
        intent.putExtra("file_uri", pdfModel?.getMAbsolute_path())
        intent.putExtra("file_name", pdfModel?.getMFile_name())
        intent.putExtra("file_id", pdfModel?.getMid())
        context?.startActivity(intent)
    }
    fun viewPPTFileOnActivity(pdfModel: PdfModel?, context: Context?) {
        val intent = Intent(context, ViewPPTActivity::class.java)
        intent.putExtra("file_uri", pdfModel?.getMAbsolute_path())
        intent.putExtra("file_name", pdfModel?.getMFile_name())
        intent.putExtra("file_id", pdfModel?.getMid())
        context?.startActivity(intent)
    }
*/


    fun openFile(filePath: String?, context: Context?): Boolean {
        val intent = Intent(Intent.ACTION_VIEW)
        val uri: Uri
        val file = File(filePath)
        val extension =
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString())
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context!!, context.packageName, File(filePath))
            val resInfoList: List<ResolveInfo> = context.packageManager.queryIntentActivities(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
            for (resolveInfo in resInfoList) {
                val packageName = resolveInfo.activityInfo.packageName
                context.grantUriPermission(
                    packageName,
                    uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
            intent.setDataAndType(uri, mimeType)
            Log.i("uri info", uri.toString())
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            try {
                if (resInfoList.isNullOrEmpty()) {
                    return false
                }
                context.startActivity(intent)
            } catch (e: java.lang.Exception) {
                return false
            }
        } else {
            uri = Uri.fromFile(File(filePath))
            intent.setDataAndType(uri, mimeType)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            try {
                context?.startActivity(intent)
            } catch (ex: Exception) {
                Toast.makeText(
                    context,
                    ex.message,
                    Toast.LENGTH_LONG
                ).show()
                ex.printStackTrace()
                return false
            }
        }
        return true
    }

    fun shareFile(filePath: String?, context: Context?) {
        val file = File(filePath)
        val share = Intent(Intent.ACTION_SEND)
        share.type = "*/*"
        var pdfUri: Uri?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pdfUri =
                FileProvider.getUriForFile(context!!, context.applicationContext.packageName, file)
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            share.putExtra(Intent.EXTRA_SUBJECT, "Pdf Viewer")
        } else {
            pdfUri = Uri.fromFile(file)
        }
        share.putExtra(Intent.EXTRA_STREAM, pdfUri)
        context?.startActivity(Intent.createChooser(share, "Share File"))
    }

 /*   fun searchFiles(
        searchKeywordVal: String?,
        targetList: List<PdfModel>?
    ): List<PdfModel> {

        val searchResultList = ArrayList<PdfModel>()
        targetList?.forEachIndexed { i, value ->
            try {
                val fileName = value.getMFile_name().toString().toLowerCase(Locale.ROOT)
                val searchKeyword = searchKeywordVal.toString().toLowerCase(Locale.ROOT)
                if (fileName.contains(searchKeyword)) {
                    value.setPosition(i)
                    searchResultList.add(value)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return searchResultList
    }

    fun createShortcutForInAppView(
        pdfModel: PdfModel?,
        context: Context?,
        shortcutDrawableId: Int,
        intentActivity: AppCompatActivity,
        isWordFile: Boolean
    ) {
        if (Build.VERSION.SDK_INT >= 26) {
            val randomCode2 =
                "pdfreader.pdfviewer.officetool.pdfscanner${pdfModel?.getMAbsolute_path()}"

            val shortcutInfo = ShortcutInfo.Builder(context!!, randomCode2)
                .setIntent(
                    Intent(context, intentActivity::class.java).setAction(Intent.ACTION_MAIN)
                        .putExtra("duplicate", false).putExtra(
                            "file_uri",
                            pdfModel?.getMAbsolute_path()
                        ).putExtra("file_name", pdfModel?.getMFile_name())
                        .putExtra("isWordFile", isWordFile)
                )
                // !!! intent's action must be set on oreo
                .setShortLabel(pdfModel?.getMFile_name().toString())
                .setIcon(Icon.createWithResource(context, shortcutDrawableId))
                .build()
            val manager: ShortcutManager = context.getSystemService<ShortcutManager>(
                ShortcutManager::class.java
            )
            val intent =
                PendingIntent.getBroadcast(
                    context,
                    0,
                    Intent(ShortcutBroadCastReceiver.kInstalledAction),
                    0
                )
            manager.requestPinShortcut(shortcutInfo, intent.intentSender)
        } else {
            val shortcutIntent = Intent(context?.applicationContext, intentActivity::class.java)
            shortcutIntent.setAction(Intent.ACTION_MAIN)

            val addIntent = Intent()
            shortcutIntent.putExtra("file_uri", pdfModel?.getMAbsolute_path())
            shortcutIntent.putExtra("file_name", pdfModel?.getMFile_name())
            shortcutIntent.putExtra("isWordFile", isWordFile)
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent)
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, pdfModel?.getMFile_name())
            addIntent.putExtra(
                Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(
                    context!!.applicationContext,
                    shortcutDrawableId
                )
            )
            addIntent.action = "com.android.launcher.action.INSTALL_SHORTCUT"
            addIntent.putExtra("duplicate", false) //may it's already there so don't duplicate
            context.applicationContext.sendBroadcast(addIntent)
        }
    }

    fun createShortcutForIntentView(
        pdfModel: PdfModel?,
        context: Context?,
        position: Int,
        type: String,
        iconDrawableId: Int
    ) {
        val file = File(pdfModel?.getMAbsolute_path())
        val extension =
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString())
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)

        if (Build.VERSION.SDK_INT >= 26) {
            val shortcutIntent = Intent()
            shortcutIntent.setAction(Intent.ACTION_VIEW)
            var mfile = File(pdfModel?.getMAbsolute_path())
            //  shortcutIntent.setDataAndType(Uri.fromFile(mfile), type)
            shortcutIntent.setDataAndType(Uri.fromFile(mfile), mimeType)
            shortcutIntent.putExtra("file_uri", pdfModel?.getMAbsolute_path())
            shortcutIntent.putExtra("file_name", pdfModel?.getMFile_name())
            shortcutIntent.putExtra("duplicate", false)
            shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, pdfModel?.getMFile_name())
            val randomCode2: String = Integer.toString(position)
            val shortcutInfo =
                ShortcutInfo.Builder(context!!, randomCode2).setIntent(shortcutIntent)
                    .setShortLabel(pdfModel?.getMFile_name().toString())
                    .setIcon(Icon.createWithResource(context, iconDrawableId))
                    .build()
            val manager: ShortcutManager =
                context!!.getSystemService<ShortcutManager>(ShortcutManager::class.java)
            val intent =
                PendingIntent.getBroadcast(
                    context,
                    0,
                    Intent(ShortcutBroadCastReceiver.kInstalledAction),
                    0
                )
            manager.requestPinShortcut(shortcutInfo, intent.intentSender)

        } else {
            val shortcutIntent = Intent()
            shortcutIntent.setAction(Intent.ACTION_VIEW)
            val mfile = File(pdfModel?.getMAbsolute_path())
            // shortcutIntent.setDataAndType(Uri.fromFile(mfile), type)
            shortcutIntent.setDataAndType(Uri.fromFile(mfile), mimeType)
            val addIntent = Intent()
            shortcutIntent.putExtra("file_uri", pdfModel?.getMAbsolute_path())
            shortcutIntent.putExtra("file_name", pdfModel?.getMFile_name())
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent)
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, pdfModel?.getMFile_name())
            addIntent.putExtra(
                Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(
                    context!!.applicationContext,
                    iconDrawableId
                )
            )
            addIntent.action = "com.android.launcher.action.INSTALL_SHORTCUT"
            addIntent.putExtra("duplicate", false) //may it's already there so don't duplicate
            context.applicationContext.sendBroadcast(addIntent)
        }


    }

    fun deleteShortcutForInAppView(
        pdfModel: PdfModel?,
        context: Context?,
        shortcutDrawableId: Int,
        intentActivity: AppCompatActivity,
        isWordFile: Boolean
    ) {
        if (Build.VERSION.SDK_INT >= 26) {
            val randomCode2 =
                "pdfreader.pdfviewer.officetool.pdfscanner${pdfModel?.getMAbsolute_path()}"
            val list = arrayListOf<String>()
            list.add(randomCode2)
            val manager: ShortcutManager = context!!.getSystemService<ShortcutManager>(
                ShortcutManager::class.java
            )
            manager.disableShortcuts(list, context.getString(R.string.error_file_removed))
        } else {
            val shortcutIntent = Intent(
                context?.applicationContext,
                intentActivity::class.java
            )
            shortcutIntent.action = Intent.ACTION_MAIN
            val addIntent = Intent()
            addIntent
                .putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent)
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, pdfModel?.getMFile_name())

            addIntent.action = "com.android.launcher.action.UNINSTALL_SHORTCUT"
            context?.applicationContext?.sendBroadcast(addIntent)

        }

    }

    fun deleteShortcutForIntentView(
        pdfModel: PdfModel?,
        context: Context?,
        position: Int,
        type: String,
        iconDrawableId: Int
    ) {
        val file = File(pdfModel?.getMAbsolute_path())
        val extension =
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString())
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)

        if (Build.VERSION.SDK_INT >= 26) {
            val randomCode2 =
                "pdfreader.pdfviewer.officetool.pdfscanner${pdfModel?.getMAbsolute_path()}"
            val list = arrayListOf<String>()
            list.add(randomCode2)
            val manager: ShortcutManager = context!!.getSystemService<ShortcutManager>(
                ShortcutManager::class.java
            )
            manager.disableShortcuts(list, context.getString(R.string.error_file_removed))
        } else {

            val shortcutIntent = Intent()
            shortcutIntent.setAction(Intent.ACTION_MAIN)
            val mfile = File(pdfModel?.getMAbsolute_path())
            shortcutIntent.setDataAndType(Uri.fromFile(mfile), mimeType)
            val addIntent = Intent()
            shortcutIntent.putExtra("file_uri", pdfModel?.getMAbsolute_path())
            shortcutIntent.putExtra("file_name", pdfModel?.getMFile_name())
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent)
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, pdfModel?.getMFile_name())
            addIntent.putExtra(
                Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(
                    context!!.applicationContext,
                    iconDrawableId
                )
            )
            addIntent.action = "com.android.launcher.action.UNINSTALL_SHORTCUT"
            BaseApplication.applicationContext().sendBroadcast(addIntent)
        }

    }*/
}