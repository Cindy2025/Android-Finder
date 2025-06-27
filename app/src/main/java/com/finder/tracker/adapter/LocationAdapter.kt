package com.finder.tracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.finder.tracker.data.entity.LocationData
import com.finder.tracker.databinding.ItemLocationBinding
import java.text.SimpleDateFormat
import java.util.*

class LocationAdapter : ListAdapter<LocationData, LocationAdapter.LocationViewHolder>(LocationDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class LocationViewHolder(private val binding: ItemLocationBinding) : RecyclerView.ViewHolder(binding.root) {
        
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        
        fun bind(location: LocationData) {
            binding.tvLatitude.text = "纬度: ${location.latitude}"
            binding.tvLongitude.text = "经度: ${location.longitude}"
            binding.tvAccuracy.text = "精度: ${location.accuracy}m"
            binding.tvAltitude.text = "海拔: ${location.altitude}m"
            binding.tvSpeed.text = "速度: ${location.speed}m/s"
            binding.tvTimestamp.text = "时间: ${dateFormat.format(location.timestamp)}"
            location.address?.let { binding.tvAddress.text = "地址: $it" }
        }
    }
    
    class LocationDiffCallback : DiffUtil.ItemCallback<LocationData>() {
        override fun areItemsTheSame(oldItem: LocationData, newItem: LocationData): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: LocationData, newItem: LocationData): Boolean {
            return oldItem == newItem
        }
    }
} 