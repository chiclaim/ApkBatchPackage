# ApkBatchPackage（支持 V2 签名）

android apk python 批量打包脚本


## 准备工作

1. 安装 Python 环境
2. 编辑你的 channel 文件

## 渠道文件

channel.txt

```text
huawei
xiaomi
oppo
vivo
```


## 运行脚本

```
python3 batch_apk.py channel.txt [your_apk]
```

## 程序中获取渠道

```
String channel = ManifestUtil.getChannel(context)
```
