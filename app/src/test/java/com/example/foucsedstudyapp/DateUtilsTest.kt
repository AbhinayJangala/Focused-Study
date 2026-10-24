package com.example.foucsedstudyapp

import com.example.foucsedstudyapp.utils.DateUtils
import org.junit.Assert.*
import org.junit.Test
import java.util.Calendar

class DateUtilsTest {

    private fun createTimestamp(year: Int, month: Int, dayOfMonth: Int, hour: Int = 12, minute: Int = 0): Long {
        return Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    @Test
    fun testIsSameDay_sameDayDifferentHours() {
        val time1 = createTimestamp(2026, Calendar.OCTOBER, 24, hour = 9, minute = 30)
        val time2 = createTimestamp(2026, Calendar.OCTOBER, 24, hour = 23, minute = 45)
        assertTrue(DateUtils.isSameDay(time1, time2))
    }

    @Test
    fun testIsSameDay_differentDays() {
        val time1 = createTimestamp(2026, Calendar.OCTOBER, 23, hour = 23, minute = 59)
        val time2 = createTimestamp(2026, Calendar.OCTOBER, 24, hour = 0, minute = 1)
        assertFalse(DateUtils.isSameDay(time1, time2))
    }

    @Test
    fun testIsYesterday_consecutiveDays() {
        val yesterday = createTimestamp(2026, Calendar.OCTOBER, 23, hour = 20, minute = 0)
        val today = createTimestamp(2026, Calendar.OCTOBER, 24, hour = 10, minute = 0)
        assertTrue(DateUtils.isYesterday(yesterday, today))
    }

    @Test
    fun testIsYesterday_twoDaysAgo() {
        val twoDaysAgo = createTimestamp(2026, Calendar.OCTOBER, 22, hour = 20, minute = 0)
        val today = createTimestamp(2026, Calendar.OCTOBER, 24, hour = 10, minute = 0)
        assertFalse(DateUtils.isYesterday(twoDaysAgo, today))
    }

    @Test
    fun testIsYesterday_monthBoundary() {
        val endOfPrevMonth = createTimestamp(2026, Calendar.SEPTEMBER, 30, hour = 22, minute = 0)
        val startOfNextMonth = createTimestamp(2026, Calendar.OCTOBER, 1, hour = 8, minute = 0)
        assertTrue(DateUtils.isYesterday(endOfPrevMonth, startOfNextMonth))
    }

    @Test
    fun testIsYesterday_yearBoundary() {
        val endOfPrevYear = createTimestamp(2026, Calendar.DECEMBER, 31, hour = 23, minute = 30)
        val startOfNextYear = createTimestamp(2027, Calendar.JANUARY, 1, hour = 1, minute = 15)
        assertTrue(DateUtils.isYesterday(endOfPrevYear, startOfNextYear))
    }

    @Test
    fun testCalculateEffectiveStreak_studiedToday() {
        val today = createTimestamp(2026, Calendar.OCTOBER, 24, hour = 14, minute = 0)
        val effective = DateUtils.calculateEffectiveStreak(streak = 5, lastStudyDate = today, currentMillis = today)
        assertEquals(5, effective)
    }

    @Test
    fun testCalculateEffectiveStreak_studiedYesterday_streakMaintained() {
        val yesterday = createTimestamp(2026, Calendar.OCTOBER, 23, hour = 18, minute = 0)
        val today = createTimestamp(2026, Calendar.OCTOBER, 24, hour = 10, minute = 0)
        val effective = DateUtils.calculateEffectiveStreak(streak = 5, lastStudyDate = yesterday, currentMillis = today)
        assertEquals(5, effective)
    }

    @Test
    fun testCalculateEffectiveStreak_missedYesterday_resetsToZero() {
        val twoDaysAgo = createTimestamp(2026, Calendar.OCTOBER, 22, hour = 18, minute = 0)
        val today = createTimestamp(2026, Calendar.OCTOBER, 24, hour = 10, minute = 0)
        val effective = DateUtils.calculateEffectiveStreak(streak = 5, lastStudyDate = twoDaysAgo, currentMillis = today)
        assertEquals(0, effective)
    }

    @Test
    fun testCalculateEffectiveStreak_neverStudied_returnsZero() {
        val today = createTimestamp(2026, Calendar.OCTOBER, 24, hour = 10, minute = 0)
        val effective = DateUtils.calculateEffectiveStreak(streak = 0, lastStudyDate = 0L, currentMillis = today)
        assertEquals(0, effective)
    }

    @Test
    fun testGetStartOfWeek_isMonday() {
        val wednesday = createTimestamp(2026, Calendar.OCTOBER, 28, hour = 15, minute = 0)
        val weekStart = DateUtils.getStartOfWeek(wednesday)
        val cal = Calendar.getInstance().apply { timeInMillis = weekStart }
        assertEquals(Calendar.MONDAY, cal.get(Calendar.DAY_OF_WEEK))
        assertEquals(0, cal.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, cal.get(Calendar.MINUTE))
        assertEquals(0, cal.get(Calendar.SECOND))
        assertEquals(0, cal.get(Calendar.MILLISECOND))
    }
}
