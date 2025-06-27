@echo off
echo ========================================
echo    Finder 项目 GitHub 设置脚本
echo ========================================
echo.

REM 检查Git是否安装
git --version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [错误] 未找到Git
    echo 请先安装Git: https://git-scm.com/downloads
    pause
    exit /b 1
)
echo [成功] Git已安装

REM 初始化Git仓库
echo 初始化Git仓库...
if not exist ".git" (
    git init
    echo [成功] Git仓库已初始化
) else (
    echo [信息] Git仓库已存在
)

REM 添加所有文件
echo 添加文件到Git...
git add .
echo [成功] 文件已添加到Git

REM 提交更改
echo 提交更改...
git commit -m "Initial commit: Finder Android Tracker App"
echo [成功] 更改已提交

REM 询问GitHub仓库URL
echo.
echo 请输入您的GitHub仓库URL (例如: https://github.com/用户名/Finder-Tracker.git)
echo 或者按Enter跳过此步骤
set /p github_url=

if not "%github_url%"=="" (
    echo 添加远程仓库...
    git remote add origin "%github_url%"
    echo [成功] 远程仓库已添加
    
    echo 推送到GitHub...
    git branch -M main
    git push -u origin main
    if %ERRORLEVEL% EQU 0 (
        echo.
        echo ========================================
        echo           设置完成！
        echo ========================================
        echo.
        echo 您的代码已推送到GitHub
        echo GitHub Actions将自动开始构建APK
        echo.
        echo 获取APK的步骤:
        echo 1. 访问您的GitHub仓库页面
        echo 2. 点击 "Actions" 标签
        echo 3. 等待构建完成 (约5-10分钟)
        echo 4. 下载 "app-debug" 文件
        echo 5. 将APK文件安装到Android设备
    ) else (
        echo [错误] 推送到GitHub失败
        echo 请检查网络连接和仓库URL
    )
) else (
    echo.
    echo ========================================
    echo           本地设置完成！
    echo ========================================
    echo.
    echo 您需要手动创建GitHub仓库并推送代码
    echo.
    echo 手动步骤:
    echo 1. 访问 https://github.com
    echo 2. 创建新仓库
    echo 3. 运行: git remote add origin 仓库URL
    echo 4. 运行: git push -u origin main
)

echo.
pause 