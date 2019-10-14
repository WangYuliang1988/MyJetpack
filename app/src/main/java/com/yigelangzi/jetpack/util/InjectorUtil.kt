package com.yigelangzi.jetpack.util

import android.content.Context
import com.yigelangzi.jetpack.data.AppDatabase
import com.yigelangzi.jetpack.data.GardenPlantingRepository
import com.yigelangzi.jetpack.data.PlantRepository
import com.yigelangzi.jetpack.viewmodel.GardenPlantingListViewModelFactory
import com.yigelangzi.jetpack.viewmodel.PlantDetailViewModelFactory
import com.yigelangzi.jetpack.viewmodel.PlantListViewModelFactory

object InjectorUtil {

    private fun getPlantRepository(context: Context): PlantRepository {
        return PlantRepository.getInstance(AppDatabase.getInstance(context.applicationContext).plantDao())
    }

    private fun getGardenPlantingRepository(context: Context): GardenPlantingRepository {
        return GardenPlantingRepository.getInstance(AppDatabase.getInstance(context.applicationContext).gardenPlantingDao())
    }

    fun provideGardenPlantingListViewModelFactory(context: Context): GardenPlantingListViewModelFactory {
        val repository = getGardenPlantingRepository(context)
        return GardenPlantingListViewModelFactory(repository)
    }

    fun providePlantListViewModelFactory(context: Context): PlantListViewModelFactory {
        val repository = getPlantRepository(context)
        return PlantListViewModelFactory(repository)
    }

    fun providePlantDetailViewModelFactory(
        context: Context,
        plantId: String
    ): PlantDetailViewModelFactory {
        return PlantDetailViewModelFactory(getPlantRepository(context), getGardenPlantingRepository(context), plantId)
    }
}
