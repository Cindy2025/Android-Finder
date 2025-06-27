# 📱 如何直接获取APK文件

## 🚀 方法1：使用GitHub Actions（推荐）

### 步骤1：创建GitHub仓库
1. 访问 https://github.com
2. 点击 "New repository"
3. 仓库名称：`Finder-Tracker`
4. 选择 "Public"
5. 点击 "Create repository"

### 步骤2：上传代码
```bash
# 在您的项目文件夹中执行
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/您的用户名/Finder-Tracker.git
git push -u origin main
```

### 步骤3：获取APK
1. 上传完成后，GitHub会自动开始构建
2. 点击仓库页面的 "Actions" 标签
3. 等待构建完成（约5-10分钟）
4. 点击构建任务，下载 "app-debug" 文件
5. 这就是可以直接安装的APK文件！

## 🔧 方法2：使用在线构建服务

### 使用GitLab CI
1. 创建GitLab仓库
2. 上传代码
3. GitLab会自动构建并提供下载链接

### 使用Bitbucket Pipelines
1. 创建Bitbucket仓库
2. 上传代码
3. 启用Pipelines功能
4. 自动构建并提供下载

## 💻 方法3：使用Docker（高级用户）

如果您有Docker环境：

```bash
# 创建Dockerfile
FROM openjdk:11-jdk

# 安装Android SDK
RUN apt-get update && apt-get install -y wget unzip
RUN wget https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip
RUN unzip commandlinetools-linux-8512546_latest.zip -d /opt/android-sdk

# 设置环境变量
ENV ANDROID_HOME=/opt/android-sdk
ENV PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin

# 安装必要的SDK组件
RUN yes | sdkmanager --licenses
RUN sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"

# 复制项目文件
COPY . /app
WORKDIR /app

# 构建APK
RUN ./gradlew assembleDebug

# 输出APK文件
VOLUME /app/app/build/outputs/apk/debug
```

## 📱 方法4：使用Android Studio（本地构建）

### 步骤1：下载Android Studio
- 访问：https://developer.android.com/studio
- 下载并安装

### 步骤2：打开项目
1. 启动Android Studio
2. 选择 "Open an existing project"
3. 选择Finder项目文件夹

### 步骤3：构建APK
1. 等待Gradle同步完成
2. 点击菜单：Build → Build Bundle(s) / APK(s) → Build APK(s)
3. APK文件位置：`app/build/outputs/apk/debug/app-debug.apk`

## 📥 方法5：使用在线APK构建器

### 使用Appetize.io
1. 访问：https://appetize.io
2. 上传您的项目代码
3. 自动构建并提供APK下载

### 使用Expo（如果支持）
1. 访问：https://expo.dev
2. 创建新项目
3. 上传代码并构建

## 🔍 方法6：使用GitHub Codespaces

1. 在GitHub仓库页面点击 "Code"
2. 选择 "Codespaces" 标签
3. 点击 "Create codespace on main"
4. 在在线环境中构建APK

## 📋 安装说明

获取APK文件后：

### Android设备安装
1. 将APK文件传输到Android设备
2. 在设备设置中启用"未知来源"应用安装
3. 点击APK文件进行安装
4. 授予必要权限

### 权限说明
应用需要以下权限：
- 位置权限（必需）
- 存储权限（必需）
- 使用统计权限（可选）

## ⚠️ 注意事项

1. **安全性**：APK文件包含所有源代码，请妥善保管
2. **兼容性**：需要Android 7.0或更高版本
3. **权限**：首次运行需要手动授予权限
4. **电池优化**：建议将应用加入电池优化白名单

## 🆘 常见问题

### Q: 构建失败怎么办？
A: 检查网络连接，确保所有依赖都能正常下载

### Q: APK文件太大？
A: 这是正常的，包含了完整的Android运行时

### Q: 安装时提示"解析包时出现问题"？
A: 确保设备Android版本不低于7.0

### Q: 应用无法启动？
A: 检查是否授予了必要权限

## 📞 技术支持

如果遇到问题，可以：
1. 查看GitHub Actions的构建日志
2. 检查Android Studio的错误信息
3. 在GitHub仓库中提交Issue

---

**推荐使用方法1（GitHub Actions）**，这是最简单、最可靠的方法！ 