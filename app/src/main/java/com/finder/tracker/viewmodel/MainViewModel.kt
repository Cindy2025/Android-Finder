package com.finder.tracker.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.finder.tracker.data.AppDatabase
import com.finder.tracker.data.entity.AppUsageData
import com.finder.tracker.data.entity.DeviceInfo
import com.finder.tracker.data.entity.LocationData
import com.finder.tracker.repository.TrackingRepository
import com.finder.tracker.service.TrackingService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: TrackingRepository
    private val _isTracking = MutableLiveData<Boolean>()
    private val _locations = MutableLiveData<List<LocationData>>()
    private val _appUsageList = MutableLiveData<List<AppUsageData>>()
    private val _deviceInfoList = MutableLiveData<List<DeviceInfo>>()
    
    val isTracking: LiveData<Boolean> = _isTracking
    val locations: LiveData<List<LocationData>> = _locations
    val appUsageList: LiveData<List<AppUsageData>> = _appUsageList
    val deviceInfoList: LiveData<List<DeviceInfo>> = _deviceInfoList
    
    init {
        val database = AppDatabase.getDatabase(application)
        repository = TrackingRepository(database)
        checkTrackingStatus()
    }
    
    fun setTrackingStatus(tracking: Boolean) {
        _isTracking.value = tracking
    }
    
    fun checkTrackingStatus() {
        // 检查服务是否正在运行
        val isServiceRunning = isServiceRunning(TrackingService::class.java)
        _isTracking.value = isServiceRunning
    }
    
    fun loadRecentLocations(limit: Int) {
        viewModelScope.launch {
            repository.getRecentLocations(limit).collect { locations ->
                _locations.value = locations
            }
        }
    }
    
    fun loadRecentAppUsage(limit: Int) {
        viewModelScope.launch {
            repository.getAllAppUsage().collect { appUsageList ->
                _appUsageList.value = appUsageList.take(limit)
            }
        }
    }
    
    fun loadRecentDeviceInfo(limit: Int) {
        viewModelScope.launch {
            repository.getAllDeviceInfo().collect { deviceInfoList ->
                _deviceInfoList.value = deviceInfoList.take(limit)
            }
        }
    }
    
    fun loadLocationsByDateRange(startDate: Date, endDate: Date) {
        viewModelScope.launch {
            repository.getLocationsByDateRange(startDate, endDate).collect { locations ->
                _locations.value = locations
            }
        }
    }
    
    fun loadAppUsageByDateRange(startDate: Date, endDate: Date) {
        viewModelScope.launch {
            repository.getAppUsageByDateRange(startDate, endDate).collect { appUsageList ->
                _appUsageList.value = appUsageList
            }
        }
    }
    
    fun loadDeviceInfoByDateRange(startDate: Date, endDate: Date) {
        viewModelScope.launch {
            repository.getDeviceInfoByDateRange(startDate, endDate).collect { deviceInfoList ->
                _deviceInfoList.value = deviceInfoList
            }
        }
    }
    
    fun deleteOldData(daysToKeep: Int) {
        viewModelScope.launch {
            val cutoffDate = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, -daysToKeep)
            }.time
            
            repository.deleteOldLocations(cutoffDate)
            repository.deleteOldAppUsage(cutoffDate)
            repository.deleteOldDeviceInfo(cutoffDate)
        }
    }
    
    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getApplication<Application>().getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
} 