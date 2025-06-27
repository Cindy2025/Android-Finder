package com.finder.tracker.repository

import com.finder.tracker.data.AppDatabase
import com.finder.tracker.data.dao.AppUsageSummary
import com.finder.tracker.data.entity.AppUsageData
import com.finder.tracker.data.entity.DeviceInfo
import com.finder.tracker.data.entity.LocationData
import kotlinx.coroutines.flow.Flow
import java.util.Date

class TrackingRepository(private val database: AppDatabase) {
    
    // Location related operations
    fun getAllLocations(): Flow<List<LocationData>> {
        return database.locationDao().getAllLocations()
    }
    
    fun getLocationsByDateRange(startDate: Date, endDate: Date): Flow<List<LocationData>> {
        return database.locationDao().getLocationsByDateRange(startDate, endDate)
    }
    
    fun getRecentLocations(limit: Int): Flow<List<LocationData>> {
        return database.locationDao().getRecentLocations(limit)
    }
    
    suspend fun insertLocation(location: LocationData) {
        database.locationDao().insertLocation(location)
    }
    
    suspend fun insertLocations(locations: List<LocationData>) {
        database.locationDao().insertLocations(locations)
    }
    
    suspend fun deleteOldLocations(date: Date) {
        database.locationDao().deleteOldLocations(date)
    }
    
    // App usage related operations
    fun getAllAppUsage(): Flow<List<AppUsageData>> {
        return database.appUsageDao().getAllAppUsage()
    }
    
    fun getAppUsageByDateRange(startDate: Date, endDate: Date): Flow<List<AppUsageData>> {
        return database.appUsageDao().getAppUsageByDateRange(startDate, endDate)
    }
    
    fun getTopAppsByDateRange(startDate: Date, endDate: Date): Flow<List<AppUsageSummary>> {
        return database.appUsageDao().getTopAppsByDateRange(startDate, endDate)
    }
    
    suspend fun insertAppUsage(appUsage: AppUsageData) {
        database.appUsageDao().insertAppUsage(appUsage)
    }
    
    suspend fun insertAppUsageList(appUsageList: List<AppUsageData>) {
        database.appUsageDao().insertAppUsageList(appUsageList)
    }
    
    suspend fun deleteOldAppUsage(date: Date) {
        database.appUsageDao().deleteOldAppUsage(date)
    }
    
    // Device info related operations
    fun getAllDeviceInfo(): Flow<List<DeviceInfo>> {
        return database.deviceInfoDao().getAllDeviceInfo()
    }
    
    fun getDeviceInfoByDateRange(startDate: Date, endDate: Date): Flow<List<DeviceInfo>> {
        return database.deviceInfoDao().getDeviceInfoByDateRange(startDate, endDate)
    }
    
    suspend fun getLatestDeviceInfo(): DeviceInfo? {
        return database.deviceInfoDao().getLatestDeviceInfo()
    }
    
    suspend fun insertDeviceInfo(deviceInfo: DeviceInfo) {
        database.deviceInfoDao().insertDeviceInfo(deviceInfo)
    }
    
    suspend fun insertDeviceInfoList(deviceInfoList: List<DeviceInfo>) {
        database.deviceInfoDao().insertDeviceInfoList(deviceInfoList)
    }
    
    suspend fun deleteOldDeviceInfo(date: Date) {
        database.deviceInfoDao().deleteOldDeviceInfo(date)
    }
} 