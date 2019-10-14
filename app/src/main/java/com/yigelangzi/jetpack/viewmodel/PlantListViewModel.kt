package com.yigelangzi.jetpack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.yigelangzi.jetpack.data.Plant
import com.yigelangzi.jetpack.data.PlantRepository

class PlantListViewModel internal constructor(plantRepository: PlantRepository) : ViewModel() {

    companion object {
        private const val NO_GROW_ZONE = -1
    }

    private val growZoneNumber = MutableLiveData<Int>(NO_GROW_ZONE)

    val plants: LiveData<List<Plant>> = growZoneNumber.switchMap {
        if (it == NO_GROW_ZONE) {
            plantRepository.getPlants()
        } else {
            plantRepository.getPlantsWithGrownZoneNumber(it)
        }
    }

    fun setGrowZoneNumber(num: Int) {
        growZoneNumber.value = num
    }

    fun clearGrowZoneNumber() {
        growZoneNumber.value = NO_GROW_ZONE
    }

    fun isFiltered() = growZoneNumber.value != NO_GROW_ZONE
}
