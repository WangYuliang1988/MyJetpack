package com.yigelangzi.jetpack.data

import java.util.Calendar
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.MONTH
import java.util.Calendar.SEPTEMBER
import java.util.Calendar.YEAR
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {
    private val cal = Calendar.getInstance().apply {
        set(YEAR, 1998)
        set(MONTH, SEPTEMBER)
        set(DAY_OF_MONTH, 7)
    }

    @Test fun calendarToTimestamp() {
        assertEquals(cal.timeInMillis, Converters().calendarToTimestamp(cal))
    }

    @Test fun timestampToCalendar() {
        assertEquals(Converters().timestampToCalendar(cal.timeInMillis), cal)
    }
}
