@echo off
echo ========================================
echo    Finder 安卓追踪应用构建脚本
echo ========================================
echo.

REM 检查Java
echo 检查Java环境...
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [错误] 未找到Java环境
    echo 请安装Java JDK 11或更高版本
    echo 下载地址: https://adoptium.net/
    pause
    exit /b 1
)
echo [成功] Java环境已找到

REM 检查Android SDK
echo 检查Android SDK...
if not defined ANDROID_HOME (
    echo [警告] 未找到ANDROID_HOME环境变量
    echo 请安装Android SDK并设置ANDROID_HOME环境变量
    echo 或者使用Android Studio打开项目进行构建
    echo.
    echo 是否继续尝试构建? (Y/N)
    set /p choice=
    if /i "%choice%" NEQ "Y" (
        echo 构建已取消
        pause
        exit /b 1
    )
) else (
    echo [成功] Android SDK路径: %ANDROID_HOME%
)

REM 检查Gradle
echo 检查Gradle...
gradle --version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [警告] 未找到Gradle命令
    echo 请安装Gradle或使用Android Studio
    echo.
    echo 是否尝试使用gradlew? (Y/N)
    set /p choice=
    if /i "%choice%" NEQ "Y" (
        echo 构建已取消
        pause
        exit /b 1
    )
    
    REM 尝试使用gradlew
    if exist "gradlew.bat" (
        echo 使用gradlew.bat构建...
        call gradlew.bat clean assembleDebug
    ) else (
        echo [错误] 未找到gradlew.bat文件
        echo 请使用Android Studio打开项目
        pause
        exit /b 1
    )
) else (
    echo [成功] Gradle已找到
    echo 清理项目...
    gradle clean
    echo 构建Debug版本...
    gradle assembleDebug
)

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo           构建成功！
    echo ========================================
    echo APK文件位置: app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo 请将APK文件安装到Android设备上进行测试
    echo.
    echo 安装方法:
    echo 1. 将APK文件传输到Android设备
    echo 2. 在设备上启用"未知来源"应用安装
    echo 3. 点击APK文件进行安装
) else (
    echo.
    echo ========================================
    echo           构建失败！
    echo ========================================
    echo 请检查错误信息并解决后重试
    echo.
    echo 建议使用Android Studio打开项目进行构建
)

echo.
pause 