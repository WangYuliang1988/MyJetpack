package com.yigelangzi.jetpack.viewmodel

import com.yigelangzi.jetpack.data.PlantAndGardenPlantings
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * 此处体现了ViewModel通过和DataBinding配合而发挥作用，参看list_item_garden_planting.xml
 */
class PlantAndGardenPlantingsViewModel(plantings: PlantAndGardenPlantings) {
    private val plant = checkNotNull(plantings.plant)
    private val gardenPlanting = plantings.gardenPlantings[0]

    val waterDateString: String? = dateFormat.format(gardenPlanting.lastWateringDate.time)
    val wateringInterval
        get() = plant.wateringInterval
    val imageUrl
        get() = plant.imageUrl
    val plantName
        get() = plant.name
    val plantDateString: String = dateFormat.format(gardenPlanting.plantDate.time)
    val plantId
        get() = plant.plantId

    companion object {
        private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
    }
}
