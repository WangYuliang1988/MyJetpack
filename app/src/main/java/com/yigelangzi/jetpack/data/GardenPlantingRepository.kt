package com.yigelangzi.jetpack.data

class GardenPlantingRepository private constructor(private val gardenPlantingDao: GardenPlantingDao) {

    suspend fun createGardenPlanting(plantId: String) {
        val gardenPlanting = GardenPlanting(plantId)
        gardenPlantingDao.insertGardenPlanting(gardenPlanting)
    }

    suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting) {
        gardenPlantingDao.deleteGardenPlanting(gardenPlanting)
    }

    fun isPlanted(plantId: String) = gardenPlantingDao.isPlanted(plantId)

    fun getPlantedGardens() = gardenPlantingDao.getPlantedGardens()

    companion object {
        // For singleton instantiation
        @Volatile private var instance: GardenPlantingRepository? = null

        fun getInstance(gardenPlantingDao: GardenPlantingDao) = instance ?: synchronized(this) {
            instance ?: GardenPlantingRepository(gardenPlantingDao).also { instance = it }
        }
    }
}
