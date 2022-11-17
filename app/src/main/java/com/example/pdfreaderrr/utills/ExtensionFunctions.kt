package com.example.pdfreaderrr.utills

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.*
import android.provider.Settings
import android.widget.Toast
import com.example.pdfreaderrr.R
import java.io.Serializable
import java.util.*
import java.util.prefs.Preferences


inline fun <reified T : Any> Activity.openActivity(doFinish: Boolean = false, vararg params: Pair<String, Any?>) {
    val intent = Intent(this, T::class.java)
    if (params.isNotEmpty()) fillIntentArguments(intent, params)
    startActivity(intent)
    if (doFinish) finish()
}


fun fillIntentArguments(intent: Intent, params: Array<out Pair<String, Any?>>) {
    params.forEach {
        when (val value = it.second) {
            null -> intent.putExtra(it.first, null as Serializable?)
            is Int -> intent.putExtra(it.first, value)
            is Long -> intent.putExtra(it.first, value)
            is CharSequence -> intent.putExtra(it.first, value)
            is String -> intent.putExtra(it.first, value)
            is Float -> intent.putExtra(it.first, value)
            is Double -> intent.putExtra(it.first, value)
            is Char -> intent.putExtra(it.first, value)
            is Short -> intent.putExtra(it.first, value)
            is Boolean -> intent.putExtra(it.first, value)
            is Serializable -> intent.putExtra(it.first, value)
            is Bundle -> intent.putExtra(it.first, value)
            is Parcelable -> intent.putExtra(it.first, value)
            is Array<*> -> when {
                value.isArrayOf<CharSequence>() -> intent.putExtra(it.first, value)
                value.isArrayOf<String>() -> intent.putExtra(it.first, value)
                value.isArrayOf<Parcelable>() -> intent.putExtra(it.first, value)
                else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
            }
            is IntArray -> intent.putExtra(it.first, value)
            is LongArray -> intent.putExtra(it.first, value)
            is FloatArray -> intent.putExtra(it.first, value)
            is DoubleArray -> intent.putExtra(it.first, value)
            is CharArray -> intent.putExtra(it.first, value)
            is ShortArray -> intent.putExtra(it.first, value)
            is BooleanArray -> intent.putExtra(it.first, value)
            else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
        }
        return@forEach
    }
}

fun delay(time: Long, task: Runnable) {
    Handler(Looper.getMainLooper()).postDelayed(task,  time)
}

fun shareApplication(context: Context) {
    try {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
        var shareMessage = context.getString(R.string.share_app_message)
        shareMessage += "\n https://play.google.com/store/apps/details?id=com.pdf.pdfreader.pdfviewer.pdfeditor.imagetopdf.pdfconverter.jpgtopdf.pdfreaderfree";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        context.startActivity(
            Intent.createChooser(
                shareIntent,
                context.getString(R.string.choose_one)
            )
        )
    } catch (e: Exception) {
    }
}

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun enableWifi(context: Activity): Boolean {
    return if (context.isFinishing.not()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                val panelIntent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
                context.startActivityForResult(panelIntent, 0)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            true
        } else {
            return try {
                val wifi =
                    context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager?
                wifi!!.isWifiEnabled = true
                true
            } catch (ex: Exception) {
                ex.printStackTrace()
                false
            }
        }
    } else {
        false
    }
}


fun isInternetConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}

fun enablingWiFiDisplay(context: Activity) {
    try {
        context.startActivity(Intent("android.settings.WIFI_DISPLAY_SETTINGS"))
        return
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
    }
    try {
        context.startActivityForResult(Intent("android.settings.CAST_SETTINGS"), 100)
        return
    } catch (exception1: Exception) {
        Toast.makeText(
            context,
            "Device not supported",
            Toast.LENGTH_LONG
        ).show()
    }
}


fun feedbackEmail(context: Activity) {
    try {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(
                "mailto:artysoulstudios@gmail.com?&subject= ${
                    context.getString(
                        R.string.app_name
                    )
                } - Feedback"
            )
        }
        context.startActivity(Intent.createChooser(emailIntent, "Send feedback"))
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}






