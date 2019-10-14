package com.yigelangzi.jetpack.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * 此处涉及Jetpack中的LiveData内容，可参考：https://developer.android.com/topic/libraries/architecture/livedata
 *
 * LiveData是一个可观察数据的持有类，同一般的可观察数据持有类不同，LiveData具有生命周期意识，只有在观察者（如Activity、Fragment）处于活跃状态时，才通知更新
 *
 * 若要在项目中使用LiveData，需要在App的Gradle文件中添加依赖：implementation "androidx.lifecycle:lifecycle-livedata-ktx:$rootProject.lifecycleVersion"
 *
 * 使用LiveData主要包括以下3个步骤：
 * 1.创建一个存储特定类型数据的LiveData实例，通常在ViewModel中创建
 * 2.创建一个实现了onChange()的Observer对象，通常在UI控制器（如Activity、Fragment）中创建
 * 3.将LiveData实例和Observer对象进行绑定，通常在UI控制器（如Activity、Fragment）中进行
 */
@Dao
interface PlantDao {
    @Query("select * from plants order by name")
    fun getPlants(): LiveData<List<Plant>>

    @Query("select * from plants where growZoneNumber = :growZoneNumber order by name")
    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int): LiveData<List<Plant>>

    @Query("select * from plants where id = :plantId")
    fun getPlant(plantId: String): LiveData<Plant>

    /**
     * 此处涉及Kotlin中的协程知识，可参考：https://developer.android.com/kotlin/coroutines
     *
     * 若要在项目中使用协程，需要在App的Gradle文件中添加依赖：
     * implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
     * implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
     *
     * suspend关键字标识该方法只能被协程调用
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Plant>)
}
