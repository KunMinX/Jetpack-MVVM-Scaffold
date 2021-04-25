![](https://tva1.sinaimg.cn/large/008i3skNly1gpw0wgl2vrj318u0bgt9y.jpg)



## 公告：

刚刚就本项目 "被卖课" 一事，在掘金发表了一期专访 [《开源项目被人拿去做课程卖了 1000 多万是什么体验》](https://juejin.im/post/5ecb4950518825431a669897)

本项目系我为了方便开发者们 **无痛理解** Google 开源的 Jetpack MVVM 中 **每个架构组件的 存在缘由、职责边界**，而 **精心设计的一个又一个高频应用场景**，

与此同时，本项目是作为 [《重学安卓》](https://xiaozhuanlan.com/kunminx)专栏 Jetpack MVVM 系列文章的配套项目而存在，**文章内容和项目中的代码设计均涉及本人对 Jetpack MVVM 的独家理解，本人对此享有著作权**。

任何组织或个人，未经与作者本人沟通，不得将本项目的代码设计和本人对 Jetpack MVVM 的独家理解用于出书和卖课的商业用途。

&nbsp;

## 背景

人，不是机器，人注定会犯错。

尤其是在 **多人协作的软件工程背景下** 快速版本迭代的时候。

有限的注意力应始终放在刀刃上，因而那些机械重复的模板代码，应在后台自己默默安排好一切、**免除因各种手工操作的失误 而造成的不可预期的后果**。

脚手架项目应运而生。

&nbsp;

## 架构图总览

脚手架项目不仅仅是我一个人的创作，也是集许许多多优秀开发者参与演化的结果。

该项目主要包含 表现层、领域层、数据层 三层：

![](https://images.xiaozhuanlan.com/photo/2020/710c5e1be25296c9b513c17bacaadfee.gif)

脚手架项目有 3 宝。考虑到 “按需” 选用的原则，现已抽取为 “依赖库” 独立维护。

- 福利 1：DataBinding 严格模式

> [GitHub：Strict-DataBinding](https://github.com/KunMinX/Strict-DataBinding)

- 福利 2：UnPeekLiveData 发送一次性事件

> [GitHub：UnPeek-LiveData](https://github.com/KunMinX/UnPeek-LiveData)

- 福利 3：Smooth-Navigation 使转场顺滑

> [GitHub：Smooth-Navigation](https://github.com/KunMinX/Smooth-Navigation)

- 通过 Request 来复用转发逻辑

- 通过 UseCase 管理可叫停的业务

- 通过 DataResult 回调数据层结果

如对具体的设计缘由感兴趣，可参见源码中注释的解析，此处不做累述。

&nbsp;

## 综上

任何技术的存在，都是旨在特定背景下解决特定问题，架构模式也不例外。

透过过去一年的开源和对高频场景的总结，我们已为 “页面开发” 场景下存在的高频隐患提供了 “相应解”。

随着背景因素的不断变化，技术亦处于不断的发展中，因而[《Jetpack MVVM 脚手架》](https://github.com/KunMinX/Jetpack-MVVM-Scaffold)仍朝着趋于成熟的方向不断完善。

今后如在个人项目中 用到了该脚手架项目的依赖库，可随时在 issue 区提供项目信息，**如此可以让更多的开发者 有机会认识你的开源作品**。


&nbsp;

## 使用情况统计

感谢小伙伴们对 “开源库使用情况” 匿名调查问卷的参与，截至 2021年4月25日，我们了解到

包括 “腾讯音乐、BMW、TCL” 在内的诸多知名厂商的软件，都参考过我们开源的 [Jetpack MVVM Scaffold](https://github.com/KunMinX/Jetpack-MVVM-Scaffold) 架构模式，以及正在使用我们维护的 UnPeek-LiveData 等框架。

目前我们已将具体的统计数据更新到 相关的开源库 ReadMe 中，问卷调查我们也继续保持开放，不定期将小伙伴们登记的公司和产品更新到表格，以便吸引到更多的小伙伴 参与到对这些架构组件的 使用、反馈，集众人之所长，让架构组件得以不断演化和升级。

https://wj.qq.com/s2/8362688/124a/

| 集团 / 公司                                           | 产品               | 状态   |
| ----------------------------------------------------- | ------------------ | ------ |
| 腾讯音乐                                              | 即将上线，暂时保密 | 参考过 |
| TCL                                                   | 内置应用，暂时保密 | 使用中 |
| 贵州广电网络                                          | 乐播播             | 使用中 |
| 福建树叶网络科技有限公司<br/>福建天奖网络科技有限公司 | 天奖谱林           | 使用中 |
|                                                       | 小辣椒             | 使用中 |
| BMW                                                   | Speech             | 参考过 |
| 上海互教信息有限公司                                  | 知心慧学教师       | 参考过 |
| 美术宝                                                | 弹唱宝             | 参考过 |
|                                                       | 网安               | 参考过 |

刚刚在 GitHub 看到有小伙伴基于《最佳实践》项目的架构设计重构了多个项目，感兴趣的小伙伴可前往查阅 😉

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
