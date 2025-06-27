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
