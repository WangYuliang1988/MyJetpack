package com.yigelangzi.jetpack.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.yigelangzi.jetpack.util.DATABASE_NAME
import com.yigelangzi.jetpack.worker.SeedDatabaseWorker

/**
 * 此处涉及Jetpack中的Room内容，可参考：https://developer.android.com/training/data-storage/room/index.html
 *
 * Room是对SQLite的高层抽象，实现实体类与数据库表之间的映射，对数据的存取更为简便直观
 *
 * 若要在项目中使用Room，需要在App的Gradle文件中添加以下依赖：
 * kapt "androidx.room:room-compiler:$version"
 * implementation "androidx.room:room-runtime:$version"
 * implementation "androidx.room:room-ktx:$version"
 *
 * Room主要包含以下3个组件：
 * Database：数据库，对应[AppDatabase]
 * Entity：表，对应[Plant]和[GardenPlanting]
 * DAO：对数据进行增删查改的数据访问对象，对应[PlantDao]和[GardenPlantingDao]
 *
 */
@Database(entities = [GardenPlanting::class, Plant::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gardenPlantingDao(): GardenPlantingDao
    abstract fun plantDao(): PlantDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        // For singleton instantiation
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // 创建并用本地数据预填充数据库
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        /**
                         * 此处涉及Jetpack中的WorkManager内容，可参考：https://developer.android.com/topic/libraries/architecture/workmanager
                         *
                         * 通过WorkManager，可以很容易地规划可延期的、异步执行的任务，即使应用退出或设备重启，也能可靠地执行
                         *
                         * 若要在项目中使用WorkManager，需要在App的Gradle文件中添加以下配置及依赖：
                         * kotlinOptions {
                         *     jvmTarget = "1.8" // work-runtime-ktx requires Java 8
                         * }
                         * implementation "androidx.work:work-runtime-ktx:$rootProject.version"
                         *
                         */
                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                    }
                })
                .build()
        }
    }
}
