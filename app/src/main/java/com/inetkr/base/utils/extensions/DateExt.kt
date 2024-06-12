package com.inetkr.base.utils.extensions

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

const val DATE_FORMAT_1 = "yyyy-MM-dd"
const val DATE_FORMAT_2 = "dd/MM/yyyy"
const val DATE_FORMAT_5 = "yyyy/MM/dd"
const val DATE_FORMAT_4 = "dd/MM/yy"
const val DATE_FORMAT_YEAR = "yyyy"
const val DATE_FORMAT_YEAR_MONTH = "yyyy-MM"
const val DATE_FORMAT_MONTH = "M"
const val DATE_FORMAT_3 = "yyyy-MM-dd HH:mm"
const val DATE_FORMAT_6 = "EEEE, dd/MM/yyy"
const val DATE_FORMAT_7 = "HH:mm dd/MM/yyyy"
const val DATE_FORMAT_8 = "HH:mm"
const val DATE_FORMAT_9 = "dd-MM-yyyy"

fun String.convertToDate(formatDate: String): Date? {
    try {
        return SimpleDateFormat(formatDate).parse(this)
    } catch (ex: Exception) {
    }
    return null
}

fun Long.convertToDate(): Date {
    return Calendar.getInstance().apply {
        timeInMillis = this@convertToDate
    }.time
}

fun Date.convertToString(formatDate: String): String {
    return SimpleDateFormat(formatDate).format(this)
}

fun String.convertDate(): String {
    val defaultDate = SimpleDateFormat(DATE_FORMAT_2)
    val date = defaultDate.parse(this)
    return SimpleDateFormat(DATE_FORMAT_1).format(date)
}

fun String.convertDate(format: String, targetFormat: String): String {
    return try {
        val defaultDate = SimpleDateFormat(format)
        val date = defaultDate.parse(this)
        return SimpleDateFormat(targetFormat).format(date)
    } catch (e: Exception) {
        ""
    }

}

fun String.convertDayMonthYear(): String {
    val defaultDate = SimpleDateFormat(DATE_FORMAT_5)
    val date = defaultDate.parse(this)
    return SimpleDateFormat(DATE_FORMAT_2).format(date)
}

fun formatDate(
    date: String,
    formatFrom: String = DATE_FORMAT_1,
    formatTo: String
): String {
    val cal = date.stringDateToCalendar(formatFrom)
    return cal.calendarToString(formatTo)
}

fun Calendar.calendarToString(format: String): String {
    val sdf = SimpleDateFormat(format)
    return sdf.format(this.time)
}

fun String.stringDateToCalendar(format: String = DATE_FORMAT_1): Calendar {
    val spf = SimpleDateFormat(format)
    return try {
        val cal = Calendar.getInstance()
        cal.time = spf.parse(this)
        cal
    } catch (e: Exception) {
        Calendar.getInstance()
    }
}