package com.finder.tracker.util

import android.app.AppOpsManager
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import com.finder.tracker.data.entity.AppUsageData
import com.finder.tracker.data.entity.DeviceInfo
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.*

class DeviceInfoCollector(private val context: Context) {
    
    fun collectDeviceInfo(): DeviceInfo {
        val batteryIntent = context.registerReceiver(null, android.content.IntentFilter(android.content.Intent.ACTION_BATTERY_CHANGED))
        
        val batteryLevel = batteryIntent?.let { intent ->
            val level = intent.getIntExtra(android.os.BatteryManager.EXTRA_LEVEL, -1)
            val scale = intent.getIntExtra(android.os.BatteryManager.EXTRA_SCALE, -1)
            if (level != -1 && scale != -1) {
                (level * 100 / scale.toFloat()).toInt()
            } else {
                0
            }
        } ?: 0
        
        val batteryTemperature = batteryIntent?.getIntExtra(android.os.BatteryManager.EXTRA_TEMPERATURE, 0)?.div(10f) ?: 0f
        val batteryVoltage = batteryIntent?.getIntExtra(android.os.BatteryManager.EXTRA_VOLTAGE, 0)?.toFloat() ?: 0f
        val isCharging = batteryIntent?.getIntExtra(android.os.BatteryManager.EXTRA_STATUS, -1) == android.os.BatteryManager.BATTERY_STATUS_CHARGING
        
        val storageInfo = getStorageInfo()
        val memoryInfo = getMemoryInfo()
        val cpuUsage = getCpuUsage()
        val networkInfo = getNetworkInfo()
        val wifiInfo = getWifiInfo()
        
        return DeviceInfo(
            timestamp = Date(),
            batteryLevel = batteryLevel,
            batteryTemperature = batteryTemperature,
            batteryVoltage = batteryVoltage,
            isCharging = isCharging,
            totalStorage = storageInfo.first,
            availableStorage = storageInfo.second,
            totalMemory = memoryInfo.first,
            availableMemory = memoryInfo.second,
            cpuUsage = cpuUsage,
            networkType = networkInfo,
            wifiStrength = wifiInfo,
            deviceModel = Build.MODEL,
            androidVersion = Build.VERSION.RELEASE
        )
    }
    
    fun collectAppUsageStats(): List<AppUsageData> {
        if (!hasUsageStatsPermission()) {
            return emptyList()
        }
        
        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val packageManager = context.packageManager
        
        val calendar = Calendar.getInstance()
        val endTime = calendar.timeInMillis
        calendar.add(Calendar.HOUR, -1) // 获取过去1小时的数据
        val startTime = calendar.timeInMillis
        
        val usageEvents = usageStatsManager.queryEvents(startTime, endTime)
        val appUsageMap = mutableMapOf<String, Long>()
        
        val event = UsageEvents.Event()
        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(event)
            if (event.eventType == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                val packageName = event.packageName
                appUsageMap[packageName] = appUsageMap.getOrDefault(packageName, 0L) + 1
            }
        }
        
        return appUsageMap.mapNotNull { (packageName, usageTime) ->
            try {
                val appInfo = packageManager.getApplicationInfo(packageName, 0)
                val appName = packageManager.getApplicationLabel(appInfo).toString()
                val isSystemApp = (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0
                
                AppUsageData(
                    packageName = packageName,
                    appName = appName,
                    usageTime = usageTime * 1000, // 转换为毫秒
                    timestamp = Date(),
                    isSystemApp = isSystemApp
                )
            } catch (e: PackageManager.NameNotFoundException) {
                null
            }
        }
    }
    
    private fun getStorageInfo(): Pair<Long, Long> {
        val stat = StatFs(Environment.getExternalStorageDirectory().path)
        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        val availableBlocks = stat.availableBlocksLong
        
        val totalStorage = totalBlocks * blockSize
        val availableStorage = availableBlocks * blockSize
        
        return Pair(totalStorage, availableStorage)
    }
    
    private fun getMemoryInfo(): Pair<Long, Long> {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
        val memoryInfo = android.app.ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        
        return Pair(memoryInfo.totalMem, memoryInfo.availMem)
    }
    
    private fun getCpuUsage(): Float {
        return try {
            val reader = BufferedReader(FileReader("/proc/stat"))
            val line = reader.readLine()
            reader.close()
            
            val parts = line.split("\\s+".toRegex())
            if (parts.size >= 5) {
                val user = parts[1].toLong()
                val nice = parts[2].toLong()
                val system = parts[3].toLong()
                val idle = parts[4].toLong()
                
                val total = user + nice + system + idle
                val used = total - idle
                
                (used.toFloat() / total.toFloat()) * 100
            } else {
                0f
            }
        } catch (e: IOException) {
            0f
        }
    }
    
    private fun getNetworkInfo(): String {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return "无网络"
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return "无网络"
        
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WiFi"
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "移动数据"
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "以太网"
            else -> "其他"
        }
    }
    
    private fun getWifiInfo(): Int {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        return wifiInfo.rssi
    }
    
    private fun hasUsageStatsPermission(): Boolean {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(),
            context.packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }
} 