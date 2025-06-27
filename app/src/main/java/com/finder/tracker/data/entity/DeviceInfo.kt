package com.finder.tracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "device_info")
data class DeviceInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Date,
    val batteryLevel: Int,
    val batteryTemperature: Float,
    val batteryVoltage: Float,
    val isCharging: Boolean,
    val totalStorage: Long,
    val availableStorage: Long,
    val totalMemory: Long,
    val availableMemory: Long,
    val cpuUsage: Float,
    val networkType: String,
    val wifiStrength: Int,
    val deviceModel: String,
    val androidVersion: String
) 