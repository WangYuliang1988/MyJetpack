package com.yigelangzi.jetpack.data

import java.util.Calendar
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.MONTH
import java.util.Calendar.YEAR
import org.junit.Assert.assertEquals
import org.junit.Test

class GardenPlantingTest {
    @Test fun testDefaultValues() {
        val gardenPlanting = GardenPlanting("1")
        val cal = Calendar.getInstance()
        assertYMD(cal, gardenPlanting.plantDate)
        assertYMD(cal, gardenPlanting.lastWateringDate)
        assertEquals(0L, gardenPlanting.gardenPlantingId)
    }

    private fun assertYMD(expectedCal: Calendar, actualCal: Calendar) {
        assertEquals(actualCal.get(YEAR), expectedCal.get(YEAR))
        assertEquals(actualCal.get(MONTH), expectedCal.get(MONTH))
        assertEquals(actualCal.get(DAY_OF_MONTH), expectedCal.get(DAY_OF_MONTH))
    }
}
