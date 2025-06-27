package com.finder.tracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.finder.tracker.data.entity.DeviceInfo
import com.finder.tracker.databinding.ItemDeviceInfoBinding
import java.text.SimpleDateFormat
import java.util.*

class DeviceInfoAdapter : ListAdapter<DeviceInfo, DeviceInfoAdapter.DeviceInfoViewHolder>(DeviceInfoDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceInfoViewHolder {
        val binding = ItemDeviceInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeviceInfoViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: DeviceInfoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class DeviceInfoViewHolder(private val binding: ItemDeviceInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        
        fun bind(deviceInfo: DeviceInfo) {
            binding.tvTimestamp.text = "时间: ${dateFormat.format(deviceInfo.timestamp)}"
            binding.tvBatteryLevel.text = "电池电量: ${deviceInfo.batteryLevel}%"
            binding.tvBatteryTemperature.text = "电池温度: ${deviceInfo.batteryTemperature}°C"
            binding.tvBatteryVoltage.text = "电池电压: ${deviceInfo.batteryVoltage}mV"
            binding.tvIsCharging.text = "充电状态: ${if (deviceInfo.isCharging) "充电中" else "未充电"}"
            binding.tvStorage.text = "存储: ${formatBytes(deviceInfo.availableStorage)}/${formatBytes(deviceInfo.totalStorage)}"
            binding.tvMemory.text = "内存: ${formatBytes(deviceInfo.availableMemory)}/${formatBytes(deviceInfo.totalMemory)}"
            binding.tvCpuUsage.text = "CPU使用率: ${String.format("%.1f", deviceInfo.cpuUsage)}%"
            binding.tvNetworkType.text = "网络类型: ${deviceInfo.networkType}"
            binding.tvWifiStrength.text = "WiFi信号: ${deviceInfo.wifiStrength}dBm"
            binding.tvDeviceModel.text = "设备型号: ${deviceInfo.deviceModel}"
            binding.tvAndroidVersion.text = "Android版本: ${deviceInfo.androidVersion}"
        }
        
        private fun formatBytes(bytes: Long): String {
            val units = arrayOf("B", "KB", "MB", "GB", "TB")
            var size = bytes.toDouble()
            var unitIndex = 0
            
            while (size >= 1024 && unitIndex < units.size - 1) {
                size /= 1024
                unitIndex++
            }
            
            return String.format("%.1f %s", size, units[unitIndex])
        }
    }
    
    class DeviceInfoDiffCallback : DiffUtil.ItemCallback<DeviceInfo>() {
        override fun areItemsTheSame(oldItem: DeviceInfo, newItem: DeviceInfo): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: DeviceInfo, newItem: DeviceInfo): Boolean {
            return oldItem == newItem
        }
    }
} 