package com.finder.tracker.data.dao

import androidx.room.*
import com.finder.tracker.data.entity.LocationData
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface LocationDao {
    
    @Query("SELECT * FROM location_data ORDER BY timestamp DESC")
    fun getAllLocations(): Flow<List<LocationData>>
    
    @Query("SELECT * FROM location_data WHERE timestamp BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    fun getLocationsByDateRange(startDate: Date, endDate: Date): Flow<List<LocationData>>
    
    @Query("SELECT * FROM location_data ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentLocations(limit: Int): Flow<List<LocationData>>
    
    @Insert
    suspend fun insertLocation(location: LocationData)
    
    @Insert
    suspend fun insertLocations(locations: List<LocationData>)
    
    @Delete
    suspend fun deleteLocation(location: LocationData)
    
    @Query("DELETE FROM location_data WHERE timestamp < :date")
    suspend fun deleteOldLocations(date: Date)
    
    @Query("SELECT COUNT(*) FROM location_data")
    suspend fun getLocationCount(): Int
} 