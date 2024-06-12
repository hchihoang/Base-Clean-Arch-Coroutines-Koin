package com.inetkr.base.utils.extensions

import android.content.res.Resources
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern
import kotlin.math.floor

fun String?.formatAgeNull(): String {
    return if (this.isNullOrEmpty())
        "-"
    else
        this
}

fun String.formatAge(): String {  // YYYY-MM-DD
    val year = this.substring(0, 4).toInt()
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    return "${(currentYear - year)} tuổi"
}

fun String.formatAgebyBirthday(): String {  // YYYY-MM-DD
    val year = this.substring(0, 4).toInt()
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    return "${(currentYear - year)}"
}


fun CharSequence?.nullOrEmpty(): CharSequence? = if (isNullOrEmpty()) null else this

// tra ve ten anh khi upload tu thu vien
fun String.getNameImage(): String {
    val dir = this
    val f = File(dir)
    return f.name
}

val Int.dpToPx: Int get() = Math.round(this * Resources.getSystem().displayMetrics.density)

fun getToday(): String {  // yyyy-MM-dd
    val date = Calendar.getInstance().time
    return date.convertFomat("yyyy-MM-dd")
}

fun Date.convertFomat(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

val Int.degit: String get() = if (this <= 9) "0${this}" else this.toString()

/**
 * Function to convert milliseconds time to
 * Timer Format
 * Hours:Minutes:Seconds
 */
fun Long.formatMilliSecond(): String {
    val milliseconds = this
    // Convert total duration into time
    val hours = (milliseconds / (1000 * 60 * 60)).toInt()
    val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
    val seconds = (milliseconds % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()

    // Add hours if there
    if (hours > 0) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        return String.format("%02d:%02d", minutes, seconds)
    }
}

fun Int.formatIntToTime(): String {
    val seconds = (this % 60)
    val minutes = (this % 3600) / 60
    val hour = this / 3600
    val secondFormatter = String.format("%02d", seconds)
    val minutesFormatter = String.format("%02d", minutes)
    val hourFormatter = String.format("%02d", hour)
    return if (hour == 0) {
        "$minutesFormatter : $secondFormatter"
    } else {
        "$hourFormatter :  $minutesFormatter : $secondFormatter"
    }
}

fun String.isEmailValid(): Boolean {
    val emailRegex = "^[a-zA-Z0-9_+.-]+@[a-zA-Z0-9.-]+$"
    val pattern = Pattern.compile(emailRegex)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

fun Long.formatViewersCount(): String {

    return when {
        this < 1000 -> this.toString()
        this < 1_000_000 -> {
            val roundedValue = floor(this / 100.0) / 10.0
            "$roundedValue K"
        }

        else -> {
            val roundedValue = floor(this / 10_000.0) / 100.0
            "$roundedValue M"
        }
    }
}
