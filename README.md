# ApkBatchPackage（支持 V2 签名）

android apk python 批量打包脚本


## 准备工作

1. 安装 Python 环境
2. 编辑你的 channel 文件


## 运行脚本

```
python3 batch_apk.py [your_apk]
```

## 测试
把某个apk解压，在META-INF目录下就有渠道文件。

## 程序中获取渠道

```
获取META-INF目录下就有渠道名
String channel = ManifestUtil.getChannel(this)
```
