package com.yigelangzi.jetpack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yigelangzi.jetpack.data.GardenPlantingRepository
import com.yigelangzi.jetpack.data.PlantAndGardenPlantings

/**
 * 此处涉及Jetpack中的ViewModel内容，可参考：https://developer.android.com/topic/libraries/architecture/viewmodel
 *
 * ViewModel类被设计用来以生命周期关联的方式存储和管理UI相关的数据，允许数据在设备配置变化时（如屏幕旋转）保留下来（而不是销毁重建）
 *
 * ViewModel主要同LiveData或者DataBinding配合发挥作用，此处是和LiveData，[PlantAndGardenPlantingsViewModel]体现了DataBinding
 *
 * 若要在项目中使用ViewModel，需要在App的Gradle文件中增加依赖：implementation "android.lifecycle:lifecycle-viewmodel-ktx:$version"
 */
// internal是Kotlin的可见性修饰符，表示模块内可见
class GardenPlantingListViewModel internal constructor(
    gardenPlantingRepository: GardenPlantingRepository
) : ViewModel() {
    val plantAndGardenPlantings: LiveData<List<PlantAndGardenPlantings>> = gardenPlantingRepository.getPlantedGardens()
}
