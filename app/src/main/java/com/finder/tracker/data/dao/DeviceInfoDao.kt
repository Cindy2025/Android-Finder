package com.finder.tracker.data.dao

import androidx.room.*
import com.finder.tracker.data.entity.DeviceInfo
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface DeviceInfoDao {
    
    @Query("SELECT * FROM device_info ORDER BY timestamp DESC")
    fun getAllDeviceInfo(): Flow<List<DeviceInfo>>
    
    @Query("SELECT * FROM device_info WHERE timestamp BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    fun getDeviceInfoByDateRange(startDate: Date, endDate: Date): Flow<List<DeviceInfo>>
    
    @Query("SELECT * FROM device_info ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestDeviceInfo(): DeviceInfo?
    
    @Insert
    suspend fun insertDeviceInfo(deviceInfo: DeviceInfo)
    
    @Insert
    suspend fun insertDeviceInfoList(deviceInfoList: List<DeviceInfo>)
    
    @Delete
    suspend fun deleteDeviceInfo(deviceInfo: DeviceInfo)
    
    @Query("DELETE FROM device_info WHERE timestamp < :date")
    suspend fun deleteOldDeviceInfo(date: Date)
    
    @Query("SELECT COUNT(*) FROM device_info")
    suspend fun getDeviceInfoCount(): Int
} 