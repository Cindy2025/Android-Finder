# 🔄 GitHub Actions Workflows

This project includes multiple GitHub Actions workflows for building and releasing the Android app.

## 📋 Available Workflows

### 1. `build.yml` - Basic Build
**File**: `.github/workflows/build.yml`
- **Purpose**: Simple APK build with caching
- **Triggers**: Push to main/master, Pull requests
- **Output**: Debug APK
- **Features**: Gradle caching, build summary

### 2. `android-build.yml` - Advanced Build & Release
**File**: `.github/workflows/android-build.yml`
- **Purpose**: Full-featured build with release options
- **Triggers**: Push to main/master/develop, Pull requests, Manual dispatch
- **Output**: Debug and Release APKs
- **Features**: 
  - Matrix builds (debug + release)
  - GitHub Releases integration
  - Test execution
  - Build notifications
  - Manual release creation

### 3. `quick-build.yml` - Quick Build
**File**: `.github/workflows/quick-build.yml`
- **Purpose**: Fast APK build for development
- **Triggers**: Push to main/master, Pull requests
- **Output**: Debug APK only
- **Features**: 15-minute timeout, minimal steps

## 🚀 How to Use

### Automatic Builds
Workflows automatically trigger when you:
- Push code to main/master branch
- Create pull requests
- Push to develop branch (android-build.yml only)

### Manual Builds
1. Go to your GitHub repository
2. Click "Actions" tab
3. Select the workflow you want to run
4. Click "Run workflow"
5. Configure options (if available)
6. Click "Run workflow"

### Manual Release Creation
Using `android-build.yml`:
1. Go to Actions → Android Build & Release
2. Click "Run workflow"
3. Select build type (debug/release)
4. Check "Upload to GitHub Release"
5. Click "Run workflow"

## 📥 Downloading APKs

### From Actions Artifacts
1. Go to repository "Actions" tab
2. Click on a successful workflow run
3. Scroll to "Artifacts" section
4. Click on the APK file to download

### From GitHub Releases
1. Go to repository "Releases" section
2. Click on the latest release
3. Download the APK file

## ⚙️ Workflow Configuration

### Environment Variables
- `GRADLE_OPTS`: Optimized Gradle settings
- `GITHUB_TOKEN`: Automatically provided by GitHub

### Caching
- Gradle packages cached for faster builds
- Android build cache included
- Cache keys based on Gradle files

### Timeouts
- Quick build: 15 minutes
- Full build: No explicit timeout
- Release creation: Depends on build time

## 🔧 Customization

### Adding New Workflows
1. Create `.yml` file in `.github/workflows/`
2. Define triggers and jobs
3. Use latest action versions
4. Test with manual dispatch

### Modifying Existing Workflows
- Update action versions regularly
- Test changes with pull requests
- Monitor build times and success rates

## 📊 Build Status

### Success Indicators
- ✅ All jobs completed
- 📱 APK artifacts available
- 📦 Download links working

### Common Issues
- ❌ Gradle build failures
- ❌ Missing dependencies
- ❌ Permission issues
- ❌ Timeout errors

## 🆘 Troubleshooting

### Build Fails
1. Check build logs for specific errors
2. Verify all files are committed
3. Ensure Gradle wrapper exists
4. Check Android SDK availability

### Slow Builds
1. Enable Gradle caching
2. Use parallel builds
3. Optimize dependencies
4. Consider build matrix exclusions

### Missing Artifacts
1. Verify build succeeded
2. Check artifact retention settings
3. Ensure correct file paths
4. Wait for upload to complete

---

## 📝 Quick Start

**For immediate APK build:**
1. Push code to main branch
2. Wait for automatic build (5-10 minutes)
3. Download APK from Actions artifacts

**For release version:**
1. Use manual dispatch with android-build.yml
2. Select release build type
3. Enable GitHub Release upload
4. Download from Releases page 