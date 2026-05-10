package com.moviestream.core.common

import java.time.format.DateTimeFormatter
import java.time.LocalDate
import java.util.Locale

object DateUtils {
    fun formatDate(dateString: String?): String {
        if (dateString == null) return "Unknown Date"
        return try {
            val date = LocalDate.parse(dateString)
            val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.getDefault())
            formatter.format(date)
        } catch (e: Exception) {
            dateString
        }
    }

    fun formatYear(dateString: String?): String {
        if (dateString == null) return "Unknown"
        return try {
            val date = LocalDate.parse(dateString)
            date.year.toString()
        } catch (e: Exception) {
            "Unknown"
        }
    }
}

object TextUtils {
    fun truncateText(text: String, maxLength: Int): String {
        return if (text.length > maxLength) {
            text.substring(0, maxLength) + "..."
        } else {
            text
        }
    }

    fun formatRating(rating: Double?): String {
        return rating?.let { String.format("%.1f", it) } ?: "N/A"
    }

    fun formatRuntime(minutes: Int?): String {
        return minutes?.let { 
            val hours = it / 60
            val mins = it % 60
            if (hours > 0) "$hours h ${mins}m" else "${mins}m"
        } ?: "Unknown"
    }
}

object NumberUtils {
    fun formatViewCount(count: Int): String {
        return when {
            count >= 1_000_000 -> String.format("%.1fM", count / 1_000_000.0)
            count >= 1_000 -> String.format("%.1fK", count / 1_000.0)
            else -> count.toString()
        }
    }
}
