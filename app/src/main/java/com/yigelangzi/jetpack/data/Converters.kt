package com.yigelangzi.jetpack.data

import androidx.room.TypeConverter
import java.util.Calendar

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    @TypeConverter fun calendarToTimestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter fun timestampToCalendar(value: Long): Calendar = Calendar.getInstance().apply { timeInMillis = value }
}
