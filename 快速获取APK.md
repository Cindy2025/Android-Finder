# 🚀 快速获取APK - 3种简单方法

## 方法1：GitHub自动构建（最推荐）

### 步骤：
1. **安装Git**：https://git-scm.com/downloads
2. **运行设置脚本**：双击 `setup-github.bat`
3. **创建GitHub仓库**：访问 https://github.com → New repository
4. **输入仓库URL**：在脚本提示时输入您的GitHub仓库URL
5. **等待构建**：GitHub会自动构建APK（5-10分钟）
6. **下载APK**：在GitHub仓库的Actions页面下载

## 方法2：Android Studio（本地构建）

### 步骤：
1. **下载Android Studio**：https://developer.android.com/studio
2. **打开项目**：Android Studio → Open → 选择Finder文件夹
3. **构建APK**：Build → Build Bundle(s) / APK(s) → Build APK(s)
4. **找到APK**：`app/build/outputs/apk/debug/app-debug.apk`

## 方法3：在线构建服务

### 使用GitLab：
1. 创建GitLab仓库
2. 上传代码
3. 自动构建并提供下载

### 使用Bitbucket：
1. 创建Bitbucket仓库
2. 上传代码
3. 启用Pipelines功能

---

## 📱 安装到手机

获取APK后：
1. 将APK传输到Android设备
2. 启用"未知来源"应用安装
3. 点击APK文件安装
4. 授予必要权限

## ⚡ 推荐流程

**最简单的方法**：
1. 双击 `setup-github.bat`
2. 按提示操作
3. 等待GitHub自动构建
4. 下载APK并安装

**总时间**：约10-15分钟 