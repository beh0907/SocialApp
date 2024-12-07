package com.skymilk.socialapp.util

import kotlinx.datetime.LocalDateTime

object DateFormatter {
    fun parseDate(dateTime: String): String {
        return try {
            val parsedDateTime = LocalDateTime.parse(dateTime)

            "${
                parsedDateTime.month.name.lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
            } ${parsedDateTime.dayOfMonth}, ${parsedDateTime.time.hour}:${parsedDateTime.time.minute}"
        } catch (e: IllegalArgumentException) {
            dateTime
        }
    }
}