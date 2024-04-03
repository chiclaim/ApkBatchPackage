# 批量生成Apk渠道包（支持 V1/V2/V3/V4 签名）

本库的实现原理非常简单，不需要额外的集成依赖。只需要提供签名或未签名的 APK 即可.

## 使用方法

1. 安装 Python 环境
2. 编辑你的 channel 文件:

```
huawei
xiaomi
oppo
vivo
```

3. 运行脚本

```
// 第一个参数是渠道文件
// 第二个参数是用于签名的配置文件
// 第三个参数是你提供的 APK
python3 batch_apk.py channel.txt keystore_config.txt your.apk
```

4. 程序中获取渠道

```
String channel = ChannelUtil.getChannel(context)
```

5. 你可以使用 apksigner 打印生成的渠道包签名信息

```
Verified using v1 scheme (JAR signing): true
Verified using v2 scheme (APK Signature Scheme v2): true
Verified using v3 scheme (APK Signature Scheme v3): true
Verified using v3.1 scheme (APK Signature Scheme v3.1): false
Verified using v4 scheme (APK Signature Scheme v4): false
```

## 市面上主流的多渠道打包工具

市面上主流的多渠道打包工具主要有：腾讯的 WasDolly 和美团的 Walle。主要缺点有：

1. 侵入性大
   需要依赖各自的 plugin，对最低的 agp 最低版本有要求。例如 WasDolly 最低支持 4.2.0

2. 兼容性问题
   issue 中有些 闪退问题未解决

## 本库的实现原理

一开始打算使用 WasDolly ，主要是 issue 中存在可能闪退问题，目前还未解决，所以放弃了

后面打算使用 [apktool](https://apktool.org) 反编译，然后添加渠道，然后重新签名，存在两个问题：
1. 生成的apk比原先大了 500多kb；
2. 时间也比较长

apktool 也是第三方维护的，也存在很多 issue 未解决。所以 apktool 的方案也放弃了

后面想能不能往apk中，写入渠道文件（和V1渠道包方案一样），然后将apk前面文件删除，在重新签名不就好了么。
apk 本质上就是一个 zip 文件，删除签名文件需要重新压缩，这也可能存在问题:
1. 不同的压缩算法生成的文件和原apk可能不一致了
2. 安卓的有些资源文件不能参与压缩

所以删除签名文件，在重新签名的方式也放弃

既然要删除签名文件，直接生成一个未签名的APK，然后往 META-INF 写渠道文件不就可以了么，这样当然可以，只不过要先打一个未签名的包，一般我们通过
Jenkins 打的 release 包都是已经签名可用的包

那能不能直接对已经签名的包，再次签名呢，尝试是可以的。

所以原理很简单提供一个已签名或未签名的 apk，然后往 META-INF 写渠道文件，然后再签名即可。这样既没有侵入性，也没有兼容性问题。理论上是支持后续的签名版本如，V4/V5
等等（因为没有对代码有任何侵入的改动）







