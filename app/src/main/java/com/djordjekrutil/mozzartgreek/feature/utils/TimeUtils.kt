package com.djordjekrutil.mozzartgreek.feature.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("DefaultLocale")
fun convertMillisToHHMMSS(millis: Long): String {
    val totalSeconds = millis / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

@SuppressLint("SimpleDateFormat")
fun convertMillisToDateTimeFormat(millis: Long, format: String = "HH:mm"): String {
    val formatter = SimpleDateFormat(format)
    val date = Date(millis)
    return formatter.format(date)
}