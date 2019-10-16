package com.yigelangzi.jetpack.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.yigelangzi.jetpack.util.getValue
import com.yigelangzi.jetpack.util.testCalendar
import com.yigelangzi.jetpack.util.testGardenPlanting
import com.yigelangzi.jetpack.util.testPlant
import com.yigelangzi.jetpack.util.testPlants
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GardenPlantingDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var gardenPlantingDao: GardenPlantingDao

    private var testGardenPlantingId: Long = 0L

    @Rule @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun createDb() = runBlocking {
        val context =  InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        gardenPlantingDao = database.gardenPlantingDao()

        database.plantDao().insertAll(testPlants)
        testGardenPlantingId = gardenPlantingDao.insertGardenPlanting(testGardenPlanting)
    }

    @After fun closeDb() {
        database.close()
    }

    @Test fun testGetGardenPlantings() = runBlocking {
        val gardenPlanting = GardenPlanting(
            testPlants[1].plantId,
            testCalendar,
            testCalendar
        ).also { it.gardenPlantingId = 2 }
        gardenPlantingDao.insertGardenPlanting(gardenPlanting)
        assertEquals(getValue(gardenPlantingDao.getGardenPlantings()).size, 2)
    }

    @Test fun testDeleteGardenPlanting() = runBlocking {
        val gardenPlanting = GardenPlanting(
            testPlants[1].plantId,
            testCalendar,
            testCalendar
        ).also { it.gardenPlantingId = 2 }
        gardenPlantingDao.insertGardenPlanting(gardenPlanting)
        assertEquals(getValue(gardenPlantingDao.getGardenPlantings()).size, 2)
        gardenPlantingDao.deleteGardenPlanting(gardenPlanting)
        assertEquals(getValue(gardenPlantingDao.getGardenPlantings()).size, 1)
    }

    @Test fun testGetGardenPlantingForPlant() {
        assertTrue(getValue(gardenPlantingDao.isPlanted(testPlant.plantId)))
    }

    @Test fun testGetGardenPlantingForPlant_notFound() {
        assertFalse(getValue(gardenPlantingDao.isPlanted(testPlants[2].plantId)))
    }

    @Test fun testGetPlantAndGardenPlantings() {
        val plantAndGardenPlantings = getValue(gardenPlantingDao.getPlantedGardens())
        assertThat(plantAndGardenPlantings.size, equalTo(1))

        /**
         * Only the [testPlant] has been planted, and thus has an associated [GardenPlanting]
         */
        assertThat(plantAndGardenPlantings[0].plant, equalTo(testPlant))
        assertThat(plantAndGardenPlantings[0].gardenPlantings.size, equalTo(1))
        assertThat(plantAndGardenPlantings[0].gardenPlantings[0], equalTo(testGardenPlanting))
    }
}
