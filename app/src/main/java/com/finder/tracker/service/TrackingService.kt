package com.finder.tracker.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import com.finder.tracker.MainActivity
import com.finder.tracker.R
import com.finder.tracker.data.AppDatabase
import com.finder.tracker.data.entity.AppUsageData
import com.finder.tracker.data.entity.DeviceInfo
import com.finder.tracker.data.entity.LocationData
import com.finder.tracker.repository.TrackingRepository
import com.finder.tracker.util.DeviceInfoCollector
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit

class TrackingService : Service(), LocationListener {
    
    private lateinit var repository: TrackingRepository
    private lateinit var locationManager: LocationManager
    private lateinit var deviceInfoCollector: DeviceInfoCollector
    private lateinit var wakeLock: PowerManager.WakeLock
    
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var isTracking = false
    
    companion object {
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "tracking_service_channel"
        private const val LOCATION_UPDATE_INTERVAL = 30000L // 30 seconds
        private const val DEVICE_INFO_UPDATE_INTERVAL = 60000L // 1 minute
        private const val APP_USAGE_UPDATE_INTERVAL = 300000L // 5 minutes
        
        fun startService(context: Context) {
            val intent = Intent(context, TrackingService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
        
        fun stopService(context: Context) {
            val intent = Intent(context, TrackingService::class.java)
            context.stopService(intent)
        }
    }
    
    override fun onCreate() {
        super.onCreate()
        
        val database = AppDatabase.getDatabase(this)
        repository = TrackingRepository(database)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        deviceInfoCollector = DeviceInfoCollector(this)
        
        // Acquire wake lock
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "Finder::TrackingWakeLock"
        )
        wakeLock.acquire(10 * 60 * 1000L) // 10 minutes
        
        createNotificationChannel()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isTracking) {
            startForeground(NOTIFICATION_ID, createNotification())
            startTracking()
        }
        return START_STICKY
    }
    
    private fun startTracking() {
        isTracking = true
        
        // Start location tracking
        startLocationTracking()
        
        // Start device info collection
        startDeviceInfoCollection()
        
        // Start app usage tracking
        startAppUsageTracking()
    }
    
    private fun startLocationTracking() {
        try {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_UPDATE_INTERVAL,
                    10f, // 10 meters
                    this
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun startDeviceInfoCollection() {
        serviceScope.launch {
            while (isTracking) {
                try {
                    val deviceInfo = deviceInfoCollector.collectDeviceInfo()
                    repository.insertDeviceInfo(deviceInfo)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                delay(DEVICE_INFO_UPDATE_INTERVAL)
            }
        }
    }
    
    private fun startAppUsageTracking() {
        serviceScope.launch {
            while (isTracking) {
                try {
                    val appUsageList = deviceInfoCollector.collectAppUsageStats()
                    if (appUsageList.isNotEmpty()) {
                        repository.insertAppUsageList(appUsageList)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                delay(APP_USAGE_UPDATE_INTERVAL)
            }
        }
    }
    
    override fun onLocationChanged(location: Location) {
        serviceScope.launch {
            try {
                val locationData = LocationData(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    accuracy = location.accuracy,
                    altitude = location.altitude,
                    speed = location.speed,
                    timestamp = Date()
                )
                repository.insertLocation(locationData)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Tracking Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows tracking service status"
                setShowBadge(false)
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Finder 追踪服务")
            .setContentText("正在追踪设备信息...")
            .setSmallIcon(R.drawable.ic_tracking)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        isTracking = false
        
        try {
            locationManager.removeUpdates(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        serviceScope.cancel()
        
        if (wakeLock.isHeld) {
            wakeLock.release()
        }
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
} 