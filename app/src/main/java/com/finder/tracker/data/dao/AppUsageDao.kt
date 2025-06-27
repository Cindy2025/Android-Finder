package com.finder.tracker.data.dao

import androidx.room.*
import com.finder.tracker.data.entity.AppUsageData
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface AppUsageDao {
    
    @Query("SELECT * FROM app_usage_data ORDER BY timestamp DESC")
    fun getAllAppUsage(): Flow<List<AppUsageData>>
    
    @Query("SELECT * FROM app_usage_data WHERE timestamp BETWEEN :startDate AND :endDate ORDER BY usageTime DESC")
    fun getAppUsageByDateRange(startDate: Date, endDate: Date): Flow<List<AppUsageData>>
    
    @Query("SELECT packageName, appName, SUM(usageTime) as totalUsageTime FROM app_usage_data WHERE timestamp BETWEEN :startDate AND :endDate GROUP BY packageName ORDER BY totalUsageTime DESC")
    fun getTopAppsByDateRange(startDate: Date, endDate: Date): Flow<List<AppUsageSummary>>
    
    @Insert
    suspend fun insertAppUsage(appUsage: AppUsageData)
    
    @Insert
    suspend fun insertAppUsageList(appUsageList: List<AppUsageData>)
    
    @Delete
    suspend fun deleteAppUsage(appUsage: AppUsageData)
    
    @Query("DELETE FROM app_usage_data WHERE timestamp < :date")
    suspend fun deleteOldAppUsage(date: Date)
    
    @Query("SELECT COUNT(*) FROM app_usage_data")
    suspend fun getAppUsageCount(): Int
}

data class AppUsageSummary(
    val packageName: String,
    val appName: String,
    val totalUsageTime: Long
) 