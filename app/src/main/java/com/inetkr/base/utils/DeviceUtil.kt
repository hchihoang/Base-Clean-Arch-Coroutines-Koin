package com.inetkr.base.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.res.Configuration
import android.media.AudioManager
import android.net.ConnectivityManager
import android.text.TextUtils
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


object DeviceUtil {

    /**
     * Check internet connected
     *
     * @param context
     * @return
     */
    fun hasConnection(context: Context?): Boolean {
        if (context == null) {
            return false
        }

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            ?: return false

        val wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (wifiNetwork != null && wifiNetwork.isConnected) {
            return true
        }

        val mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (mobileNetwork != null && mobileNetwork.isConnected) {
            return true
        }

        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    fun hideSoftKeyboard(activity: Activity?) {
        if (activity != null && activity.window != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }
    }

    fun clearFocus(activity: Activity?) {
        if (activity != null && activity.currentFocus != null) {
            activity.currentFocus!!.clearFocus()
        }
    }

    fun convertDpToPx(context: Context, inputDp: Int): Int {
        val resources = context.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            inputDp.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

    fun getSoftButtonsBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    fun widthScreenPixel(context: Context?): Int {
        if (context == null) {
            return 0
        }
        val resources = context.resources
        val metrics = resources.displayMetrics

        return metrics.widthPixels
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId: Int =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun heightScreenPixel(context: Context?): Int {
        if (context == null) {
            return 0
        }
        val resources = context.resources
        val metrics = resources.displayMetrics

        return metrics.heightPixels
    }

    fun setAudioNormal(context: Context) {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.mode = AudioManager.MODE_IN_COMMUNICATION
        audioManager.isSpeakerphoneOn = true

    }

    fun isMiUi(): Boolean {
        return !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"))
    }

    private fun getSystemProperty(propName: String): String? {
        val line: String
        var input: BufferedReader? = null
        try {
            val p = Runtime.getRuntime().exec("getprop $propName")
            input = BufferedReader(InputStreamReader(p.inputStream), 1024)
            line = input.readLine()
            input.close()
        } catch (ex: IOException) {
            return null
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return line
    }

    fun isAppIsInBackground(context: Context): Boolean {
        var isInBackground = true
        val am: ActivityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningProcesses: List<ActivityManager.RunningAppProcessInfo> =
            am.runningAppProcesses
        for (processInfo in runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (activeProcess in processInfo.pkgList) {
                    if (activeProcess == context.packageName) {
                        isInBackground = false
                    }
                }
            }
        }
        return isInBackground
    }

    fun isDarkMode(context: Context): Boolean {
        val darkModeFlag =
            context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return darkModeFlag == Configuration.UI_MODE_NIGHT_YES
    }
}
