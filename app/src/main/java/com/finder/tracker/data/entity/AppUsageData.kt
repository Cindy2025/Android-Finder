package com.finder.tracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "app_usage_data")
data class AppUsageData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val packageName: String,
    val appName: String,
    val usageTime: Long, // 使用时间（毫秒）
    val timestamp: Date,
    val isSystemApp: Boolean = false
) 