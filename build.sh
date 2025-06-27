#!/bin/bash

echo "正在构建 Finder 安卓追踪应用..."
echo

# 检查是否安装了Android SDK
if [ -z "$ANDROID_HOME" ]; then
    echo "错误: 未找到ANDROID_HOME环境变量"
    echo "请确保已安装Android SDK并设置ANDROID_HOME环境变量"
    exit 1
fi

echo "清理项目..."
./gradlew clean

echo "构建Debug版本..."
./gradlew assembleDebug

if [ $? -eq 0 ]; then
    echo
    echo "构建成功！"
    echo "APK文件位置: app/build/outputs/apk/debug/app-debug.apk"
    echo
    echo "请将APK文件安装到Android设备上进行测试"
else
    echo
    echo "构建失败，请检查错误信息"
fi 