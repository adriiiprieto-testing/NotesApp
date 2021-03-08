package es.adriiiprieto.notesapp.base.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.toTimestampString(): String = try {
    val dateTime = Date()
    dateTime.time = this

    val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy_hh:mm:ss", Locale.getDefault())
    simpleDateFormat.format(dateTime)
} catch (e: Exception) {
    ""
}

