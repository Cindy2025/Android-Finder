package com.finder.tracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.finder.tracker.data.dao.AppUsageDao
import com.finder.tracker.data.dao.DeviceInfoDao
import com.finder.tracker.data.dao.LocationDao
import com.finder.tracker.data.entity.AppUsageData
import com.finder.tracker.data.entity.DeviceInfo
import com.finder.tracker.data.entity.LocationData
import com.finder.tracker.data.util.Converters

@Database(
    entities = [
        LocationData::class,
        AppUsageData::class,
        DeviceInfo::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun locationDao(): LocationDao
    abstract fun appUsageDao(): AppUsageDao
    abstract fun deviceInfoDao(): DeviceInfoDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "finder_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 