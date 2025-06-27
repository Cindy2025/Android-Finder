# 🚀 Quick APK Download - 3 Simple Methods

## Method 1: GitHub Auto Build (Most Recommended)

### Steps:
1. **Install Git**: https://git-scm.com/downloads
2. **Run Setup Script**: Double-click `setup-github.bat`
3. **Create GitHub Repository**: Visit https://github.com → New repository
4. **Enter Repository URL**: Input your GitHub repository URL when prompted
5. **Wait for Build**: GitHub will automatically build APK (5-10 minutes)
6. **Download APK**: Download from Actions page in GitHub repository

### Available Workflows:
- **Quick Build** (`quick-build.yml`): Fast debug APK build
- **Full Build** (`android-build.yml`): Debug + Release APKs with GitHub Releases
- **Basic Build** (`build.yml`): Simple build with caching

## Method 2: Android Studio (Local Build)

### Steps:
1. **Download Android Studio**: https://developer.android.com/studio
2. **Open Project**: Android Studio → Open → Select Finder folder
3. **Build APK**: Build → Build Bundle(s) / APK(s) → Build APK(s)
4. **Find APK**: `app/build/outputs/apk/debug/app-debug.apk`

## Method 3: Online Build Services

### Using GitLab:
1. Create GitLab repository
2. Upload code
3. Auto build and provide download

### Using Bitbucket:
1. Create Bitbucket repository
2. Upload code
3. Enable Pipelines feature

---

## 📱 Install on Phone

After getting APK:
1. Transfer APK to Android device
2. Enable "Unknown sources" app installation
3. Click APK file to install
4. Grant necessary permissions

## ⚡ Recommended Process

**Simplest method**:
1. Double-click `setup-github.bat`
2. Follow prompts
3. Wait for GitHub auto build
4. Download APK and install

**For Release Version**:
1. Go to GitHub repository Actions tab
2. Select "Android Build & Release" workflow
3. Click "Run workflow"
4. Select "release" build type
5. Enable "Upload to GitHub Release"
6. Download from Releases page

**Total time**: About 10-15 minutes

## 🔄 Workflow Options

### Quick Build (Recommended for Development)
- **File**: `quick-build.yml`
- **Time**: ~5-8 minutes
- **Output**: Debug APK only
- **Auto-trigger**: Push to main/master

### Full Build (Recommended for Releases)
- **File**: `android-build.yml`
- **Time**: ~8-12 minutes
- **Output**: Debug + Release APKs
- **Features**: GitHub Releases, manual dispatch
- **Auto-trigger**: Push to main/master/develop

### Basic Build
- **File**: `build.yml`
- **Time**: ~6-10 minutes
- **Output**: Debug APK with caching
- **Auto-trigger**: Push to main/master 