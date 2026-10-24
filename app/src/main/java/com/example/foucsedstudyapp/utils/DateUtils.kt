package com.example.foucsedstudyapp.utils

import java.util.Calendar

object DateUtils {

    fun getStartOfDay(timeMillis: Long = System.currentTimeMillis()): Long {
        val calendar = Calendar.getInstance().apply {
            this.timeInMillis = timeMillis
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    fun getStartOfWeek(timeMillis: Long = System.currentTimeMillis()): Long {
        val calendar = Calendar.getInstance().apply {
            this.timeInMillis = timeMillis
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            val dayOfWeek = get(Calendar.DAY_OF_WEEK)
            val daysSinceMonday = (dayOfWeek - Calendar.MONDAY + 7) % 7
            add(Calendar.DAY_OF_MONTH, -daysSinceMonday)
        }
        return calendar.timeInMillis
    }

    fun isSameDay(date1Millis: Long, date2Millis: Long): Boolean {
        if (date1Millis <= 0L || date2Millis <= 0L) return false
        val cal1 = Calendar.getInstance().apply { timeInMillis = date1Millis }
        val cal2 = Calendar.getInstance().apply { timeInMillis = date2Millis }
        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    fun isYesterday(lastDateMillis: Long, currentDateMillis: Long = System.currentTimeMillis()): Boolean {
        if (lastDateMillis <= 0L || currentDateMillis <= 0L) return false
        val calYesterday = Calendar.getInstance().apply {
            timeInMillis = currentDateMillis
            add(Calendar.DAY_OF_YEAR, -1)
        }
        val calLast = Calendar.getInstance().apply { timeInMillis = lastDateMillis }
        return calYesterday.get(Calendar.ERA) == calLast.get(Calendar.ERA) &&
                calYesterday.get(Calendar.YEAR) == calLast.get(Calendar.YEAR) &&
                calYesterday.get(Calendar.DAY_OF_YEAR) == calLast.get(Calendar.DAY_OF_YEAR)
    }

    fun calculateEffectiveStreak(streak: Int, lastStudyDate: Long, currentMillis: Long = System.currentTimeMillis()): Int {
        if (streak <= 0 || lastStudyDate <= 0L) return 0
        return if (isSameDay(lastStudyDate, currentMillis) || isYesterday(lastStudyDate, currentMillis)) {
            streak
        } else {
            0
        }
    }
}
