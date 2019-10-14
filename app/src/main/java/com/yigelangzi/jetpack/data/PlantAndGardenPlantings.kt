package com.yigelangzi.jetpack.data

import androidx.room.Embedded
import androidx.room.Relation

data class PlantAndGardenPlantings(
    // @Embedded 用来进行类嵌套，嵌套后，Plant类的属性也相当于PlantAndGardenPlantings类的属性
    @Embedded val plant: Plant,

    // @Relation 用来定义关联查询，当查询获取PlantAndGardenPlantings数据时，针对gardenPlantings属性的赋值，
    // @Relation会告诉Room，去查询GardenPlanting中plant_id等于Plant中id的数据，并封装为List赋值到该属性中
    // parentColumn对应的是Plant中的字段，entityColumn对应的是GardenPlanting中的字段
    @Relation(parentColumn = "id", entityColumn = "plant_id")
    val gardenPlantings: List<GardenPlanting> = emptyList()
)
