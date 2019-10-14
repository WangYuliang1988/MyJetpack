package com.yigelangzi.jetpack.data

import java.util.Calendar
import java.util.Calendar.DAY_OF_YEAR
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PlantTest {
    private lateinit var plant: Plant

    @Before fun setUp() {
        plant = Plant("1", "Tomato", "A red vegetable", 1, 2, "")
    }

    @Test fun test_default_values() {
        val defaultPlant = Plant("2", "Apple", "Description", 1)
        assertEquals(7, defaultPlant.wateringInterval)
        assertEquals("", defaultPlant.imageUrl)
    }

    @Test fun test_shouldBeWatered() {
        Calendar.getInstance().let { now ->
            val lastWateringDate = Calendar.getInstance()

            lastWateringDate.time = now.time
            assertFalse(plant.shouldBeWatered(now, lastWateringDate.apply { add(DAY_OF_YEAR, 0) }))

            lastWateringDate.time = now.time
            assertFalse(plant.shouldBeWatered(now, lastWateringDate.apply { add(DAY_OF_YEAR, -1) }))

            lastWateringDate.time = now.time
            assertTrue(plant.shouldBeWatered(now, lastWateringDate.apply { add(DAY_OF_YEAR, -14) }))
        }
    }

    @Test fun text_toString() {
        assertEquals("Tomato", plant.toString())
    }
}
