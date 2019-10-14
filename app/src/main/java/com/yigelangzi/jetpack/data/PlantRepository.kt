package com.yigelangzi.jetpack.data

class PlantRepository private constructor(private val plantDao: PlantDao) {

    fun getPlants() = plantDao.getPlants()

    fun getPlant(plantId: String) = plantDao.getPlant(plantId)

    fun getPlantsWithGrownZoneNumber(growZoneNumber: Int) = plantDao.getPlantsWithGrowZoneNumber(growZoneNumber)

    companion object {
        // For singleton instantiation
        @Volatile private var instance: PlantRepository? = null

        fun getInstance(plantDao: PlantDao) = instance ?: synchronized(this) {
            instance ?: PlantRepository(plantDao).also { instance = it }
        }
    }
}
