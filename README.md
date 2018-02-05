# ApkBatchPackage(Deprecated)

android apk python批量打包脚本


### 1、首先在你的本地安装Python环境（MAC自带环境）

然后把你打包好的、可安装的apk放在和脚本的同一目录，重命名为：source.apk

### 2、编辑channel.txt渠道文件

把需要打包的渠道名放在该文件里，格式为：一行一个渠道

```
360
appChina
wandoujia
91
baidu
QQ
3G
eoe
anzhi
163
hiapk
jifeng
xiaomi
meizu
oppo
lenovo
```

### 3、然后运行脚本，如：

> python batch_apk.py 

在目录apks下就有全部打包好的apk，

### 4、测试
把某个apk解压，在META-INF目录下就有渠道文件。

### 5、最后
获取META-INF目录下就有渠道名
> String channel = ManifestUtil.getChannel(this)

通过代码的方式把渠道设置给UMENG(友盟)：
> AnalyticsConfig.setChannel(ManifestUtil.getChannel(this));


