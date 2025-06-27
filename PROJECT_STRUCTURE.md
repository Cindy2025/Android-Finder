# 项目结构说明

```
Finder/
├── app/                                    # 应用模块
│   ├── build.gradle                       # 应用级构建配置
│   └── src/main/
│       ├── java/com/finder/tracker/
│       │   ├── MainActivity.kt            # 主活动
│       │   ├── adapter/                   # RecyclerView适配器
│       │   │   ├── LocationAdapter.kt     # 位置数据适配器
│       │   │   ├── AppUsageAdapter.kt     # 应用使用适配器
│       │   │   └── DeviceInfoAdapter.kt   # 设备信息适配器
│       │   ├── data/                      # 数据层
│       │   │   ├── AppDatabase.kt         # Room数据库
│       │   │   ├── dao/                   # 数据访问对象
│       │   │   │   ├── LocationDao.kt     # 位置数据DAO
│       │   │   │   ├── AppUsageDao.kt     # 应用使用DAO
│       │   │   │   └── DeviceInfoDao.kt   # 设备信息DAO
│       │   │   ├── entity/                # 数据实体
│       │   │   │   ├── LocationData.kt    # 位置数据实体
│       │   │   │   ├── AppUsageData.kt    # 应用使用实体
│       │   │   │   └── DeviceInfo.kt      # 设备信息实体
│       │   │   └── util/                  # 数据工具
│       │   │       └── Converters.kt      # Room类型转换器
│       │   ├── repository/                # 仓库层
│       │   │   └── TrackingRepository.kt  # 追踪数据仓库
│       │   ├── service/                   # 服务层
│       │   │   └── TrackingService.kt     # 前台追踪服务
│       │   ├── util/                      # 工具类
│       │   │   └── DeviceInfoCollector.kt # 设备信息收集器
│       │   └── viewmodel/                 # 视图模型
│       │       └── MainViewModel.kt       # 主视图模型
│       ├── res/                           # 资源文件
│       │   ├── drawable/                  # 图标和图片
│       │   │   ├── ic_tracking.xml        # 追踪图标
│       │   │   ├── ic_launcher_foreground.xml # 启动器图标
│       │   │   └── ic_launcher_background.xml # 启动器背景
│       │   ├── layout/                    # 布局文件
│       │   │   ├── activity_main.xml      # 主活动布局
│       │   │   ├── item_location.xml      # 位置项布局
│       │   │   ├── item_app_usage.xml     # 应用使用项布局
│       │   │   └── item_device_info.xml   # 设备信息项布局
│       │   ├── mipmap-anydpi-v26/         # 启动器图标
│       │   │   ├── ic_launcher.xml        # 启动器图标
│       │   │   └── ic_launcher_round.xml  # 圆形启动器图标
│       │   ├── values/                    # 值资源
│       │   │   ├── colors.xml             # 颜色定义
│       │   │   ├── strings.xml            # 字符串资源
│       │   │   ├── themes.xml             # 主题定义
│       │   │   └── ic_launcher_background.xml # 启动器背景色
│       │   └── xml/                       # XML配置文件
│       │       ├── backup_rules.xml       # 备份规则
│       │       └── data_extraction_rules.xml # 数据提取规则
│       └── AndroidManifest.xml            # 应用清单文件
├── build.gradle                           # 项目级构建配置
├── settings.gradle                        # 项目设置
├── gradle/wrapper/                        # Gradle包装器
│   └── gradle-wrapper.properties          # Gradle版本配置
├── app/proguard-rules.pro                 # 代码混淆规则
├── build.bat                              # Windows构建脚本
├── build.sh                               # Linux/Mac构建脚本
├── README.md                              # 项目说明文档
└── PROJECT_STRUCTURE.md                   # 项目结构说明

```

## 架构说明

### 1. 数据层 (Data Layer)
- **Entity**: 定义数据库表结构
- **DAO**: 数据访问对象，定义数据库操作
- **Database**: Room数据库配置
- **Repository**: 数据仓库，统一数据访问接口

### 2. 业务层 (Business Layer)
- **ViewModel**: 视图模型，处理业务逻辑
- **Service**: 后台服务，执行追踪任务
- **Util**: 工具类，提供辅助功能

### 3. 表现层 (Presentation Layer)
- **Activity**: 用户界面活动
- **Adapter**: RecyclerView适配器
- **Layout**: 界面布局文件

### 4. 资源层 (Resource Layer)
- **Drawable**: 图标和图片资源
- **Layout**: 界面布局定义
- **Values**: 颜色、字符串、主题等资源
- **XML**: 配置文件

## 数据流

```
用户操作 → Activity → ViewModel → Repository → DAO → Database
                ↓
            Adapter → RecyclerView → 用户界面
```

## 主要组件

### 核心服务
- **TrackingService**: 前台服务，负责数据收集
- **DeviceInfoCollector**: 设备信息收集工具
- **TrackingRepository**: 数据仓库，统一数据访问

### 数据实体
- **LocationData**: 位置信息
- **AppUsageData**: 应用使用统计
- **DeviceInfo**: 设备状态信息

### 用户界面
- **MainActivity**: 主界面，包含三个标签页
- **LocationAdapter**: 位置数据显示
- **AppUsageAdapter**: 应用使用数据显示
- **DeviceInfoAdapter**: 设备信息显示

## 技术栈

- **Kotlin**: 主要开发语言
- **Room**: 本地数据库
- **Coroutines**: 异步处理
- **ViewModel**: 架构组件
- **Material Design**: UI设计规范
- **RecyclerView**: 列表显示
- **Service**: 后台服务 