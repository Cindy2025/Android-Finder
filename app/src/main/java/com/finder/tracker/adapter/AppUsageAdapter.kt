package com.finder.tracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.finder.tracker.data.entity.AppUsageData
import com.finder.tracker.databinding.ItemAppUsageBinding
import java.text.SimpleDateFormat
import java.util.*

class AppUsageAdapter : ListAdapter<AppUsageData, AppUsageAdapter.AppUsageViewHolder>(AppUsageDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppUsageViewHolder {
        val binding = ItemAppUsageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppUsageViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: AppUsageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class AppUsageViewHolder(private val binding: ItemAppUsageBinding) : RecyclerView.ViewHolder(binding.root) {
        
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        
        fun bind(appUsage: AppUsageData) {
            binding.tvAppName.text = appUsage.appName
            binding.tvPackageName.text = "包名: ${appUsage.packageName}"
            binding.tvUsageTime.text = "使用时间: ${formatDuration(appUsage.usageTime)}"
            binding.tvTimestamp.text = "时间: ${dateFormat.format(appUsage.timestamp)}"
            binding.tvIsSystemApp.text = "系统应用: ${if (appUsage.isSystemApp) "是" else "否"}"
        }
        
        private fun formatDuration(milliseconds: Long): String {
            val seconds = milliseconds / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            
            return when {
                hours > 0 -> "${hours}小时${minutes % 60}分钟"
                minutes > 0 -> "${minutes}分钟${seconds % 60}秒"
                else -> "${seconds}秒"
            }
        }
    }
    
    class AppUsageDiffCallback : DiffUtil.ItemCallback<AppUsageData>() {
        override fun areItemsTheSame(oldItem: AppUsageData, newItem: AppUsageData): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: AppUsageData, newItem: AppUsageData): Boolean {
            return oldItem == newItem
        }
    }
} 