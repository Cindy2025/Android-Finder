package com.finder.tracker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.finder.tracker.adapter.DeviceInfoAdapter
import com.finder.tracker.adapter.LocationAdapter
import com.finder.tracker.adapter.AppUsageAdapter
import com.finder.tracker.databinding.ActivityMainBinding
import com.finder.tracker.service.TrackingService
import com.finder.tracker.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var appUsageAdapter: AppUsageAdapter
    private lateinit var deviceInfoAdapter: DeviceInfoAdapter
    
    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            startTrackingService()
        } else {
            Toast.makeText(this, "需要位置权限才能开始追踪", Toast.LENGTH_SHORT).show()
        }
    }
    
    private val usageStatsPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (hasUsageStatsPermission()) {
            Toast.makeText(this, "使用统计权限已授予", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        
        setupUI()
        setupObservers()
        checkPermissions()
    }
    
    private fun setupUI() {
        // 设置TabLayout
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("位置"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("应用使用"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("设备信息"))
        
        // 设置RecyclerViews
        locationAdapter = LocationAdapter()
        appUsageAdapter = AppUsageAdapter()
        deviceInfoAdapter = DeviceInfoAdapter()
        
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = locationAdapter
        }
        
        // Tab切换监听
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        binding.recyclerView.adapter = locationAdapter
                        viewModel.loadRecentLocations(20)
                    }
                    1 -> {
                        binding.recyclerView.adapter = appUsageAdapter
                        viewModel.loadRecentAppUsage(20)
                    }
                    2 -> {
                        binding.recyclerView.adapter = deviceInfoAdapter
                        viewModel.loadRecentDeviceInfo(20)
                    }
                }
            }
            
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        
        // 开始/停止追踪按钮
        binding.btnStartTracking.setOnClickListener {
            if (viewModel.isTracking.value == true) {
                stopTrackingService()
            } else {
                requestPermissionsAndStart()
            }
        }
        
        // 刷新按钮
        binding.btnRefresh.setOnClickListener {
            refreshData()
        }
    }
    
    private fun setupObservers() {
        viewModel.isTracking.observe(this) { isTracking ->
            binding.btnStartTracking.text = if (isTracking) "停止追踪" else "开始追踪"
            binding.btnStartTracking.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    if (isTracking) android.R.color.holo_red_light else android.R.color.holo_green_light
                )
            )
        }
        
        viewModel.locations.observe(this) { locations ->
            locationAdapter.submitList(locations)
        }
        
        viewModel.appUsageList.observe(this) { appUsageList ->
            appUsageAdapter.submitList(appUsageList)
        }
        
        viewModel.deviceInfoList.observe(this) { deviceInfoList ->
            deviceInfoAdapter.submitList(deviceInfoList)
        }
    }
    
    private fun checkPermissions() {
        val permissions = mutableListOf<String>()
        
        // 检查位置权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        
        // 检查存储权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        
        if (permissions.isNotEmpty()) {
            locationPermissionLauncher.launch(permissions.toTypedArray())
        }
    }
    
    private fun requestPermissionsAndStart() {
        val permissions = mutableListOf<String>()
        
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        
        if (permissions.isNotEmpty()) {
            locationPermissionLauncher.launch(permissions.toTypedArray())
        } else {
            startTrackingService()
        }
    }
    
    private fun startTrackingService() {
        TrackingService.startService(this)
        viewModel.setTrackingStatus(true)
        Toast.makeText(this, "追踪服务已启动", Toast.LENGTH_SHORT).show()
    }
    
    private fun stopTrackingService() {
        TrackingService.stopService(this)
        viewModel.setTrackingStatus(false)
        Toast.makeText(this, "追踪服务已停止", Toast.LENGTH_SHORT).show()
    }
    
    private fun refreshData() {
        when (binding.tabLayout.selectedTabPosition) {
            0 -> viewModel.loadRecentLocations(20)
            1 -> viewModel.loadRecentAppUsage(20)
            2 -> viewModel.loadRecentDeviceInfo(20)
        }
    }
    
    private fun hasUsageStatsPermission(): Boolean {
        val appOps = getSystemService(APP_OPS_SERVICE) as android.app.AppOpsManager
        val mode = appOps.checkOpNoThrow(
            android.app.AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(),
            packageName
        )
        return mode == android.app.AppOpsManager.MODE_ALLOWED
    }
    
    fun requestUsageStatsPermission() {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        usageStatsPermissionLauncher.launch(intent)
    }
    
    override fun onResume() {
        super.onResume()
        viewModel.checkTrackingStatus()
        refreshData()
    }
} 