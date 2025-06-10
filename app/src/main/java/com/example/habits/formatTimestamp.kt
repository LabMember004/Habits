package com.example.habits

import android.annotation.SuppressLint

@SuppressLint("SimpleDateFormat")
fun formatTimestamp(timestamp: Long): String {
    val sdf = java.text.SimpleDateFormat("MMM dd, yyyy - hh:mm a")
    sdf.timeZone = java.util.TimeZone.getDefault()
    return sdf.format(java.util.Date(timestamp))
}
