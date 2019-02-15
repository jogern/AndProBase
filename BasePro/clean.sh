#!/bin/sh
echo 开始删除文件

rm -rf output *.zip *.apk

rm -rf .gradle/* .gradle
rm -rf .idea/libraries 
rm -rf .idea/workspace.xml
rm -rf build/* build 
rm -rf *.iml 
rm -rf local.properties

module=app
rm -rf ${module}/.externalNativeBuild
rm -rf ${module}/build/* ${module}/build
rm -rf ${module}/*.iml

module=CommonLib
rm -rf ${module}/.externalNativeBuild
rm -rf ${module}/build/* ${module}/build
rm -rf ${module}/*.iml

echo 删除文件结束