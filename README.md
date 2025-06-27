# Finder - Android Phone Tracking App

A completely offline Android phone tracking application that works without internet connection.

## Features

### 🔍 Location Tracking
- GPS location recording
- Location accuracy, altitude, speed information
- Historical location data viewing
- Offline map support

### 📱 App Usage Statistics
- App usage time tracking
- System apps and user apps distinction
- Usage frequency analysis
- App usage history records

### 🔋 Device Information Monitoring
- Battery status monitoring (level, temperature, voltage, charging status)
- Storage space monitoring (total capacity, available space)
- Memory usage monitoring
- CPU usage monitoring
- Network connection status
- WiFi signal strength
- Device model and Android version

### 💾 Local Data Storage
- Uses Room database for local storage
- No internet connection required
- Data encryption protection
- Automatic data cleanup

## Technical Architecture

- **Language**: Kotlin
- **Architecture Pattern**: MVVM (Model-View-ViewModel)
- **Database**: Room Database
- **UI Framework**: Material Design 3
- **Asynchronous Processing**: Kotlin Coroutines
- **Dependency Injection**: Manual dependency injection
- **Permission Management**: Runtime permission requests

## Permission Requirements

The app requires the following permissions:

### Required Permissions
- `ACCESS_FINE_LOCATION` - Precise location permission
- `ACCESS_COARSE_LOCATION` - Approximate location permission
- `WRITE_EXTERNAL_STORAGE` - Storage permission
- `FOREGROUND_SERVICE` - Foreground service permission
- `FOREGROUND_SERVICE_LOCATION` - Location foreground service permission

### Optional Permissions
- `PACKAGE_USAGE_STATS` - App usage statistics permission
- `QUERY_ALL_PACKAGES` - Query all apps permission
- `ACCESS_NETWORK_STATE` - Network state permission
- `ACCESS_WIFI_STATE` - WiFi state permission

## Installation Instructions

1. Ensure your device runs Android 7.0 (API 24) or higher
2. Download and install the APK file
3. Grant necessary permissions on first launch
4. Click "Start Tracking" button to start the tracking service

## Usage Instructions

### Start Tracking
1. Open the app
2. Click "Start Tracking" button
3. Grant location permissions
4. The tracking service will run in the background

### View Data
- **Location Tab**: View GPS location records
- **App Usage Tab**: View app usage statistics
- **Device Info Tab**: View device status information

### Stop Tracking
- Click "Stop Tracking" button
- Or force stop the app through system settings

## Data Security

- All data is stored locally on the device only
- No data is sent to any server
- The app works completely offline
- Data is stored using SQLite encryption

## Important Notes

1. **Battery Optimization**: It's recommended to add the app to battery optimization whitelist
2. **Permission Management**: Usage statistics permission needs to be granted manually
3. **Storage Space**: Long-term use may occupy significant storage space
4. **Privacy Protection**: Please ensure device security to avoid data leakage

## Development Environment

- Android Studio Arctic Fox or higher
- Kotlin 1.9.22
- Android Gradle Plugin 8.2.2
- Minimum SDK: API 24 (Android 7.0)
- Target SDK: API 34 (Android 14)

## Build Instructions

1. Clone the project to local
2. Open the project in Android Studio
3. Sync Gradle dependencies
4. Connect Android device or start emulator
5. Click the run button to build and install the app

## License

This project is for learning and research purposes only. Please comply with local laws and regulations.

## Disclaimer

This app is only for device management and personal data tracking. The developer is not responsible for any misuse. Users should ensure compliance with relevant laws and regulations and privacy policies.
