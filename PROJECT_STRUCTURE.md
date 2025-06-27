# Project Structure Documentation

```
Finder/
├── app/                                    # Application module
│   ├── build.gradle                       # App-level build configuration
│   └── src/main/
│       ├── java/com/finder/tracker/
│       │   ├── MainActivity.kt            # Main activity
│       │   ├── adapter/                   # RecyclerView adapters
│       │   │   ├── LocationAdapter.kt     # Location data adapter
│       │   │   ├── AppUsageAdapter.kt     # App usage adapter
│       │   │   └── DeviceInfoAdapter.kt   # Device info adapter
│       │   ├── data/                      # Data layer
│       │   │   ├── AppDatabase.kt         # Room database
│       │   │   ├── dao/                   # Data access objects
│       │   │   │   ├── LocationDao.kt     # Location data DAO
│       │   │   │   ├── AppUsageDao.kt     # App usage DAO
│       │   │   │   └── DeviceInfoDao.kt   # Device info DAO
│       │   │   ├── entity/                # Data entities
│       │   │   │   ├── LocationData.kt    # Location data entity
│       │   │   │   ├── AppUsageData.kt    # App usage entity
│       │   │   │   └── DeviceInfo.kt      # Device info entity
│       │   │   └── util/                  # Data utilities
│       │   │       └── Converters.kt      # Room type converters
│       │   ├── repository/                # Repository layer
│       │   │   └── TrackingRepository.kt  # Tracking data repository
│       │   ├── service/                   # Service layer
│       │   │   └── TrackingService.kt     # Foreground tracking service
│       │   ├── util/                      # Utility classes
│       │   │   └── DeviceInfoCollector.kt # Device info collector
│       │   └── viewmodel/                 # View models
│       │       └── MainViewModel.kt       # Main view model
│       ├── res/                           # Resource files
│       │   ├── drawable/                  # Icons and images
│       │   │   ├── ic_tracking.xml        # Tracking icon
│       │   │   ├── ic_launcher_foreground.xml # Launcher icon
│       │   │   └── ic_launcher_background.xml # Launcher background
│       │   ├── layout/                    # Layout files
│       │   │   ├── activity_main.xml      # Main activity layout
│       │   │   ├── item_location.xml      # Location item layout
│       │   │   ├── item_app_usage.xml     # App usage item layout
│       │   │   └── item_device_info.xml   # Device info item layout
│       │   ├── mipmap-anydpi-v26/         # Launcher icons
│       │   │   ├── ic_launcher.xml        # Launcher icon
│       │   │   └── ic_launcher_round.xml  # Round launcher icon
│       │   ├── values/                    # Value resources
│       │   │   ├── colors.xml             # Color definitions
│       │   │   ├── strings.xml            # String resources
│       │   │   ├── themes.xml             # Theme definitions
│       │   │   └── ic_launcher_background.xml # Launcher background color
│       │   └── xml/                       # XML configuration files
│       │       ├── backup_rules.xml       # Backup rules
│       │       └── data_extraction_rules.xml # Data extraction rules
│       └── AndroidManifest.xml            # Application manifest file
├── build.gradle                           # Project-level build configuration
├── settings.gradle                        # Project settings
├── gradle/wrapper/                        # Gradle wrapper
│   └── gradle-wrapper.properties          # Gradle version configuration
├── app/proguard-rules.pro                 # Code obfuscation rules
├── build.bat                              # Windows build script
├── build.sh                               # Linux/Mac build script
├── README.md                              # Project documentation
└── PROJECT_STRUCTURE.md                   # Project structure documentation

```

## Architecture Overview

### 1. Data Layer
- **Entity**: Defines database table structures
- **DAO**: Data access objects, defines database operations
- **Database**: Room database configuration
- **Repository**: Data repository, unified data access interface

### 2. Business Layer
- **ViewModel**: View models, handles business logic
- **Service**: Background services, executes tracking tasks
- **Util**: Utility classes, provides helper functions

### 3. Presentation Layer
- **Activity**: User interface activities
- **Adapter**: RecyclerView adapters
- **Layout**: Interface layout files

### 4. Resource Layer
- **Drawable**: Icons and image resources
- **Layout**: Interface layout definitions
- **Values**: Colors, strings, themes and other resources
- **XML**: Configuration files

## Data Flow

```
User Action → Activity → ViewModel → Repository → DAO → Database
                ↓
            Adapter → RecyclerView → User Interface
```

## Main Components

### Core Services
- **TrackingService**: Foreground service, responsible for data collection
- **DeviceInfoCollector**: Device information collection tool
- **TrackingRepository**: Data repository, unified data access

### Data Entities
- **LocationData**: Location information
- **AppUsageData**: App usage statistics
- **DeviceInfo**: Device status information

### User Interface
- **MainActivity**: Main interface, contains three tabs
- **LocationAdapter**: Location data display
- **AppUsageAdapter**: App usage data display
- **DeviceInfoAdapter**: Device information display

## Technology Stack

- **Kotlin**: Primary development language
- **Room**: Local database
- **Coroutines**: Asynchronous processing
- **ViewModel**: Architecture components
- **Material Design**: UI design specifications
- **RecyclerView**: List display
- **Service**: Background services 