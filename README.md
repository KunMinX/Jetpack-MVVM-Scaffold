![](https://images.xiaozhuanlan.com/photo/2021/30ce2eb5fbfc71775ca39981537f7471.png)


# 版权声明

我们就本项目 "被卖课" 一事，在掘金发表一期专访 [《开源项目被人拿去做课程卖了 1000 多万是什么体验》](https://juejin.im/post/5ecb4950518825431a669897)

本项目系我为方便开发者们 **无痛理解 Google 开源 Jetpack MVVM 中每个架构组件的 存在缘由、职责边界**，而 **精心设计的高频应用场景**，

与此同时，本项目是作为 [《重学安卓》](https://xiaozhuanlan.com/topic/6017825943)专栏 Jetpack MVVM 系列文章 “配套项目” 而存在，**文章内容和项目代码设计均涉及本人对 Jetpack MVVM 独家理解，本人对此享有著作权**。

任何组织或个人，未经与作者本人沟通，不得将本项目代码设计和本人对 Jetpack MVVM 独家理解用于 "**打包贩卖、引流、出书 和 卖课**" 等商业用途。

&nbsp;

## 背景

人，不是机器，人注定会犯错。

尤其在 **多人协作软件工程背景下** 快速版本迭代时。

有限注意力应始终放刀刃上，因而机械重复模板代码，应在后台自己默默安排好一切、**免除手工操作失误造成不可预期后果**。

脚手架项目应运而生。

&nbsp;

## 架构图总览

脚手架项目不仅仅是我一人创作，也是集诸多优秀开发者参与演化结果。

该项目包含 表现层、领域层、数据层 三层：

![](https://images.xiaozhuanlan.com/photo/2022/e387184aceb3ecfbe6ceff41ab1e4fa4.png)

脚手架项目有 3 宝。考虑到 “按需” 选用原则，现已抽取为 “依赖库” 独立维护。

- 福利 1：DataBinding 严格模式

> [GitHub：Strict-DataBinding](https://github.com/KunMinX/Strict-DataBinding)

- 福利 2：UnPeekLiveData 回推一次性消息

> [GitHub：UnPeek-LiveData](https://github.com/KunMinX/UnPeek-LiveData)

- 福利 3：Smooth-Navigation 使转场顺滑

> [GitHub：Smooth-Navigation](https://github.com/KunMinX/Smooth-Navigation)

- 福利 4：MVI-Dispatcher 自动消除 mutable 样板代码 + 支持 LiveData 连发 + 规避 setValue 滥用

> [GitHub：MVI-Dispatcher](https://github.com/KunMinX/MVI-Dispatcher)

- 福利 5：KeyValueX 消除 KV 样板代码，让 key、value、get、put、init 缩减为一，不再 KV 爆炸。

> [Github: KeyValueX](https://github.com/KunMinX/KeyValueX)

- 通过 Request 来复用转发逻辑

- 通过 UseCase 管理可叫停业务

- 通过 DataResult 回调数据层结果

如对具体设计缘由感兴趣，可参见源码中注释的解析，此处不做累述。

&nbsp;

## 综上

任何技术的存在，皆是于特定背景下解决特定问题，架构模式也不例外。

透过过去一年高频场景总结，我们已为 “页面开发” 场景下高频隐患提供 “相应解”。

随着背景因素不断变化，技术亦处于不断发展中，因而[《Jetpack MVVM 脚手架》](https://github.com/KunMinX/Jetpack-MVVM-Scaffold)仍朝着趋于成熟方向不断完善。

今后如在个人项目中用到该脚手架项目依赖库，请在 issue 区提供项目信息，**如此可让更多开发者有机会认识你开源作品**。


&nbsp;

## 使用情况统计

根据小伙伴们对 “开源库使用情况” 匿名调查问卷参与，截至 2021年4月25日，我们了解到

包括 “腾讯音乐、BMW、TCL” 在内诸多知名厂商软件，都参考过我们开源的 [Jetpack MVVM Scaffold](https://github.com/KunMinX/Jetpack-MVVM-Scaffold) 架构模式，及正在使用我们维护的 [UnPeek-LiveData](https://github.com/KunMinX/UnPeek-LiveData) 等框架。

目前已将具体统计数据更新到相关开源库 ReadMe 中，问卷调查我们也继续保持开放，不定期将小伙伴们登记的公司和产品更新到表格，以吸引到更多小伙伴参与到对这些架构组件的使用和反馈，集众人之所长，让架构组件得以不断演化和升级。

https://wj.qq.com/s2/8362688/124a/


| 集团 / 公司 / 品牌 / 团队                             | 产品               |
| ----------------------------------------------------- | ------------------ |
| 腾讯音乐                                              | QQ 音乐       |
| TCL                                                   | 内置应用，暂时保密 |
| 贵州广电网络                                          | 乐播播             |
|                                                       | 小辣椒             |
| BMW                                                   | Speech             |
| 上海互教信息有限公司                                  | 知心慧学教师       |
| 美术宝                                                | 弹唱宝             |
|                                                       | 网安               |
| 字节跳动直播                                          | 直播 SDK           |
| 一加手机                                              | OPNote             |



刚刚在 GitHub 看到有小伙伴基于《最佳实践》架构设计，重构多个项目，感兴趣小伙伴可前往查阅

[GitHub - Jetpack开发短视频应用实战 - 重构](https://github.com/zion223/Jetpack-MVVM-PPJoke)

[GitHub - 仿网易云音乐安卓客户端 - 重构](https://github.com/zion223/NeteaseCloudMusic-MVVM)

&nbsp;

## 版权声明

本文以 [CC 署名-非商业性使用-禁止演绎 4.0 国际协议](https://creativecommons.org/licenses/by-nc-nd/4.0/deed.zh) 发行。

Copyright © 2019-present KunMinX

![](https://images.xiaozhuanlan.com/photo/2020/8fc6f51263babeb544bb4a7dae6cde59.jpg)

&nbsp;

## License

```
Copyright 2019-present KunMinX

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
