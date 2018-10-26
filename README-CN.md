## 目录
1. 在可玩广告平台申请应用ID及广告位ID
2. 添加MoPub SDK和ZPLAY Ads SDK
3. 将以下文件添加到工程里面 
4. 在MoPub平台为可玩广告配置广告位 
5. 在MoPub平台增加可玩广告为新的广告源 
6. 在MoPub平台打开可玩广告广告源 
7. 确认可玩广告配置成功 
8. 在工程中使用MoPub请求可玩广告 
9. 调试

---

## 1. 在可玩广告平台申请应用ID及广告位ID
### 1.1 进入“[应用管理](https://sellers.zplayads.com/#/app/appList/)”页面，点击添加“添加应用”按钮
![“应用管理”页面](imgs/img01.png)

### 1.2 填写相关信息，点击“保存”按钮，返回应用管理列表页 
![保存](imgs/img02.png)

![保存](imgs/img02-2.png)

### 1.3 在应用管理列表页，获取应用的ID
![应用管理列表页](imgs/img03.png)

### 1.4 点击应用右侧的“创建广告位”按钮或者进入“[广告位管理页面](https://sellers.zplayads.com/#/ad/placeList/)”点击“添加广告位”按钮
![创建广告位](imgs/img04.png)

### 1.5填写相关信息，点击“保存”按钮，返回广告位管理列表页
![添加](imgs/img05.png)

### 1.6在广告位管理列表页，获取广告位的ID
![获取广告位](imgs/img06.png)

注：您在测试中可使用如下id进行测试，测试id不会产生收益，应用上线时请使用您申请的正式id。

|广告形式|  App_ID  |  Ad_Unit_id|
|---|----------|------------|
|激励视频|5C5419C7-A2DE-88BC-A311-C3E7A646F6AF|3FBEFA05-3A8B-2122-24C7-A87D0BC9FEEC|
|插屏广告|5C5419C7-A2DE-88BC-A311-C3E7A646F6AF|19393189-C4EB-3886-60B9-13B39407064E|

## 2. 添加MoPub SDK和ZPLAY Ads SDK，步骤如下：
```
dependencies {
    ...
    // 可玩广告依赖
    compile 'com.playableads:playableads:2.2.1'
    // mopub广告依赖
    compile('com.mopub:mopub-sdk:4.20.0@aar') {
        transitive = true
    }
}
```
### 2.1 添加Android工程依赖


### 2.2 在Manifest文件下添加MoPub使用到的组件
```
<application>
    <activity
        android:name="com.mopub.mobileads.MoPubActivity"
        android:configChanges="keyboardHidden|orientation|screenSize" />
    <activity
        android:name="com.mopub.mobileads.MraidActivity"
        android:configChanges="keyboardHidden|orientation|screenSize" />
    <activity
        android:name="com.mopub.common.MoPubBrowser"
        android:configChanges="keyboardHidden|orientation|screenSize" />
    <activity
        android:name="com.mopub.mobileads.MraidVideoPlayerActivity"
        android:configChanges="keyboardHidden|orientation|screenSize" />
    <activity
        android:name="com.mopub.mobileads.RewardedMraidActivity"
        android:configChanges="keyboardHidden|orientation|screenSize" />
</application>
```


## 3. 将以下文件添加到工程里面
[ZPLAYAdsRewardedVideo.java](app/src/main/java/com/zplay/playable/mediationmopub/ZPLAYAdsRewardedVideo.java)
确保导入相关的类之后没有错误提示，记录该文件包路径，例如：（com.zplay.playable.mediationmopub.ZPLAYAdsRewardedVideo），这个路径用于在MoPub平台配置ZPLAYAds信息。

## 4. 在[MoPub平台](https://app.mopub.com/apps)为可玩广告配置广告位
### 4.1 为可玩广告新建广告位
- a. 进入应用，点击“New ad unit”按钮

![new add unit](imgs/img07.png)

- b. 创建广告位，请注意Format应该为Rewarded video或Fullscreen，点击“save”按钮，此处以Rewarded video为例

![Rewarded video](imgs/img08.png) 

- c. 获取新创建广告位的ad unit ID

![创建广告位](imgs/img09.png)

### 4.2 获取已有广告位的ID
- a. 选择应用，进入广告位列表，选择要接入的广告位，点击进入。点击“Edit ad unit”按钮，点击“view code integration”按钮

![view code integration](imgs/img10.png)

- b. 获取广告位的ad unit ID

![获取广告位](imgs/img11.png)

## 5. 在[MoPub平台](https://app.mopub.com/networks)增加可玩广告为新的广告源
### 5.1 进入“Networks”页面，点击“New network”按钮
![add a network](imgs/img12.png)


### 5.2 点击“Custom SDK network“链接
![custom native network](imgs/img13.png)

### 5.3 添加可玩广告平台名称为ZPLAY Ads Network，并且在步骤3中申请的广告位中配置可玩广告（图示1和图示2）。

![配置](imgs/img14.png)
![配置](imgs/img14-2.png)
![配置](imgs/img14-3.png)

- a. 请在图示1的位置添加如下信息：
```
com.zplay.playable.mediationmopub.ZPLAYAdsRewardedVideo
```

注：(填写的是ZPLAYAdsRewardedVideo在项目中实际的位置)
```
{
    "APPID": "5C5419C7-A2DE-88BC-A311-C3E7A646F6AF",
    "AdUnitId": "3FBEFA05-3A8B-2122-24C7-A87D0BC9FEEC"
}
```
- b. 请在图示2的位置添加在可玩广告平台申请的广告位信息

注意：将5C5419C7-A2DE-88BC-A311-C3E7A646F6AF替换成您在可玩广告平台申请的APPID（步骤1.3），将3FBEFA05-3A8B-2122-24C7-A87D0BC9FEEC替换成您在可玩广告平台申请的AdUnitId（步骤1.6）。

## 6. 在[MoPub平台](https://app.mopub.com/segments)打开可玩广告广告源
### 6.1 进入“segments”页面，点击“Global Segment”链接
![Global Segment](imgs/img15.png)

### 6.2 找到接入可玩广告的应用（如示例中MediationMopub）及广告位（示例中的新可玩广告），打开可玩广告广告源（图中的turn on所示的按钮）
![turn on](imgs/img16.png)


## 7. 确认可玩广告配置成功
进入6.2 中已经打开可玩广告源的广告位管理页面，如果成功配置，则会在ad sources列表中会显示如下信息。如果未显示，请参照以上步骤进行检查。

![确认可玩广告配置成功](imgs/img17.png)

## 8. 在工程中使用MoPub请求可玩广告
配置信息如下：

![配置信息](imgs/img18.png)

图示1：导入MoPub需要的文件
图示2：初始化MoPub SDK
图示3：请求广告，请正确填入在MoPub平台申请的广告位ID（详见第4步）
图示4：展示广告，请正确填入在MoPub平台申请的广告位ID（详见第4步）

## 9. 调试
查看MoPubRewardedVideoListener回调中的信息，可判断是否加载成功，以及定位问题。

![调试信息](imgs/img19.png)
