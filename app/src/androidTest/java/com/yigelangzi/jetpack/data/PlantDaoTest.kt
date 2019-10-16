package com.yigelangzi.jetpack.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.yigelangzi.jetpack.util.getValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlantDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var plantDao: PlantDao

    private val plantA = Plant("1", "A", "Description A", 1, 1, "")
    private val plantB = Plant("2", "B", "", 1, 1, "")
    private val plantC = Plant("3", "C", "", 2, 2, "")

    @Rule @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        plantDao = database.plantDao()

        // Insert plants in non-alphabetical order to test that results are sorted by name
        plantDao.insertAll(listOf(plantB, plantC, plantA))
    }

    @After fun closeDb() {
        database.close()
    }

    @Test fun testGetPlants() {
        val plantList = getValue(plantDao.getPlants())
        assertThat(plantList.size, equalTo(3))

        // Ensure plant list is sorted by name
        assertEquals(plantList[0], plantA)
        assertEquals(plantList[1], plantB)
        assertEquals(plantList[2], plantC)
    }

    @Test fun testGetPlantsWithGrowZoneNumber() {
        assertThat(getValue(plantDao.getPlantsWithGrowZoneNumber(1)).size, equalTo(2))
        assertThat(getValue(plantDao.getPlantsWithGrowZoneNumber(2)).size, equalTo(1))
        assertThat(getValue(plantDao.getPlantsWithGrowZoneNumber(3)).size, equalTo(0))

        // Ensure plant list is sorted by name
        val plantList = getValue(plantDao.getPlantsWithGrowZoneNumber(1))
        assertThat(plantList[0], equalTo(plantA))
        assertThat(plantList[1], equalTo(plantB))
    }

    @Test fun testGetPlant() {
        assertThat(getValue(plantDao.getPlant(plantA.plantId)), equalTo(plantA))
    }
}
