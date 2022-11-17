package com.example.pdfreaderrr.utills

import android.Manifest
import android.graphics.Color

class Constants {
    companion object {
        const val SETTINGS : String = "settings"
        const val ENABLE_VIBRATIONCLAP : String = "ENABLE_VIBRATION_CLAP"
        const val ENABLE_MELODYWHISTLE : String = "ENABLE_MELODY_WHISTLE"
        const val ENABLE_MELODYCLAP : String = "ENABLE_MELODY_CLAP"
        const val GETMODE : String = "MODE"
        const val RINGTONE_NAME : String = "RINGTONE"
        const val ADOPTIVE_MODE : String = "ADOPTIVEMODE"
        const val ISADWATCHED:String="ISADWATCHED"
        const val LENGTH_TOTAL : String = "LENGTH"
        const val IS_SERVICE_ON : String = "SERVICEON"
        const val NIGHT_MODE = "night_mode"
        const val DARK = "dark"
        const val THEME = "theme"
        const val LIGHT = "light"
        const val DEFAULT = "defualt"
        const val LANGUAGE= "langauge"
        const val LANGUAGEPOSITION= "langauge_position"
        const val RECORD_AUDIO_REQUEST_CODE = 1
        const val PERMISSION_REQUEST_CODE = 3
        const val MY_PERMISSIONS_REQUEST_CAMERA = 1
        const val PURCHASED:String="purchase"


        const val DEFAULT_COMPRESSION = "DefaultCompression"
        const val SORTING_INDEX = "SORTING_INDEX"
        const val IMAGE_EDITOR_KEY = "first"
        const val DEFAULT_FONT_SIZE_TEXT = "DefaultFontSize"
        const val DEFAULT_FONT_SIZE = 11
        const val PREVIEW_IMAGES = "preview_images"
        const val DATABASE_NAME = "ImagesToPdfDB.db"
        const val DEFAULT_FONT_FAMILY_TEXT = "DefaultFontFamily"
        const val DEFAULT_FONT_FAMILY = "TIMES_ROMAN"
        const val DEFAULT_FONT_COLOR_TEXT = "DefaultFontColor"
        const val DEFAULT_FONT_COLOR = -16777216

        // key for text to pdf (TTP) page color
        const val DEFAULT_PAGE_COLOR_TTP = "DefaultPageColorTTP"

        // key for images to pdf (ITP) page color
        const val DEFAULT_PAGE_COLOR_ITP = "DefaultPageColorITP"
        const val DEFAULT_PAGE_COLOR = Color.WHITE
        const val DEFAULT_THEME_TEXT = "DefaultTheme"
        const val DEFAULT_THEME = "System"
        const val DEFAULT_IMAGE_BORDER_TEXT = "Image_border_text"
        const val RESULT = "result"
        const val SAME_FILE = "SameFile"
        const val DEFAULT_PAGE_SIZE_TEXT = "DefaultPageSize"
        const val DEFAULT_PAGE_SIZE = "A4"
        const val CHOICE_REMOVE_IMAGE = "CHOICE_REMOVE_IMAGE"
        const val DEFAULT_QUALITY_VALUE = 30
        const val DEFAULT_BORDER_WIDTH = 0
        const val STORAGE_LOCATION = "storage_location"
        const val DEFAULT_IMAGE_SCALE_TYPE_TEXT = "image_scale_type"
        const val IMAGE_SCALE_TYPE_STRETCH = "stretch_image"
        const val IMAGE_SCALE_TYPE_ASPECT_RATIO = "maintain_aspect_ratio"
        const val PG_NUM_STYLE_PAGE_X_OF_N = "pg_num_style_page_x_of_n"
        const val PG_NUM_STYLE_X_OF_N = "pg_num_style_x_of_n"
        const val PG_NUM_STYLE_X = "pg_num_style_x"
        const val MASTER_PWD_STRING = "master_password"

        const val IMAGE_TO_PDF_KEY = "Images to PDF"
        const val TEXT_TO_PDF_KEY = "Text To PDF"
        const val QR_BARCODE_KEY = "QR & Barcodes"
        const val VIEW_FILES_KEY = "View Files"
        const val HISTORY_KEY = "History"
        const val ADD_TEXT_KEY = "Add Text"
        const val ADD_PASSWORD_KEY = "Add password"
        const val REMOVE_PASSWORD_KEY = "Remove password"
        const val ROTATE_PAGES_KEY = "Rotate Pages"
        const val ADD_WATERMARK_KEY = "Add Watermark"
        const val ADD_IMAGES_KEY = "Add Images"
        const val MERGE_PDF_KEY = "Merge PDF"
        const val SPLIT_PDF_KEY = "Split PDF"
        const val INVERT_PDF_KEY = "Invert Pdf"
        const val COMPRESS_PDF_KEY = "Compress PDF"
        const val REMOVE_DUPLICATE_PAGES_KEY = "Remove Duplicate Pages"
        const val REMOVE_PAGES_KEY = "Remove Pages"
        const val REORDER_PAGES_KEY = "Reorder Pages"
        const val EXTRACT_TEXT_KEY = "Extract Text"
        const val EXTRACT_IMAGES_KEY = "Extract Images"
        const val PDF_TO_IMAGES_KEY = "PDF to Images"
        const val EXCEL_TO_PDF_KEY = "Excel to PDF"
        const val ZIP_TO_PDF_KEY = "ZIP to PDF"

        const val BUNDLE_DATA = "bundle_data"
        const val REORDER_PAGES = "Reorder pages"
        const val REMOVE_PAGES = "Remove pages"
        const val COMPRESS_PDF = "Compress PDF"
        const val ADD_PWD = "Add password"
        const val REMOVE_PWd = "Remove password"
        const val ADD_IMAGES = "add_images"
        const val PDF_TO_IMAGES = "pdf_to_images"
        const val EXTRACT_IMAGES = "extract_images"

        const val LAUNCH_COUNT = "launch_count"

        const val pdfDirectory = "/Pdffiles/"
        const val pdfExtension = ".pdf"
        const val appName = "PDF Converter"
        const val PATH_SEPERATOR = "/"
        const val textExtension = ".txt"
        const val excelExtension = ".xls"
        const val excelWorkbookExtension = ".xlsx"
        const val docExtension = ".doc"
        const val docxExtension = ".docx"
        const val tempDirectory = "temp"

        const val AUTHORITY_APP = "com.swati4star.shareFile"

        const val ACTION_SELECT_IMAGES = "android.intent.action.SELECT_IMAGES"
        const val ACTION_VIEW_FILES = "android.intent.action.VIEW_FILES"
        const val ACTION_TEXT_TO_PDF = "android.intent.action.TEXT_TO_PDF"
        const val ACTION_MERGE_PDF = "android.intent.action.MERGE_PDF"
        const val OPEN_SELECT_IMAGES = "open_select_images"

        const val THEME_WHITE = "White"
        const val THEME_BLACK = "Black"
        const val THEME_DARK = "Dark"
        const val THEME_SYSTEM = "System"

        const val IS_WELCOME_ACTIVITY_SHOWN = "is_Welcome_activity_shown"
        const val SHOW_WELCOME_ACT = "show_welcome_activity"

        const val VERSION_NAME = "VERSION_NAME"

        const val PREF_PAGE_STYLE = "pref_page_number_style"
        const val PREF_PAGE_STYLE_ID = "pref_page_number_style_rb_id"

        const val REQUEST_CODE_FOR_WRITE_PERMISSION = 4
        const val REQUEST_CODE_FOR_READ_PERMISSION = 5


        val WRITE_PERMISSIONS = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val READ_PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )


        const val MODIFY_STORAGE_LOCATION_CODE = 1

        const val ROTATE_PAGES = 20
        const val ADD_PASSWORD = 21
        const val REMOVE_PASSWORD = 22
        const val ADD_WATERMARK = 23

        //Preference key name.
        const val RECENT_PREF = "Recent"




    }
}