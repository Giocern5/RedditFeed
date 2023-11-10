package com.example.tinderonsight.utils

object DateTimeUtils {
    fun timeAgo(unixTimestamp: Long): String {
        val currentTime = System.currentTimeMillis() / 1000
        val timeDifference = currentTime - unixTimestamp

        val minutes = (timeDifference / 60).toInt()
        val hours = (timeDifference / 3600).toInt()
        val days = (timeDifference / 86400).toInt()

        return when {
            timeDifference < 120 -> "a few minutes ago"
            minutes >= 15 && minutes < 60 -> "$minutes minutes ago"
            minutes >= 60 && minutes < 120 -> "1 hour ago"
            hours >= 2 && hours < 24 -> "$hours hours ago"
            hours >= 24 && hours < 48 -> "1 day ago"
            else -> "$days days ago"
        }
    }
}