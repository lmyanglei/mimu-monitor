# 米木监控 mimu monitor

米木监控 mimu monitor 是一个简单实用的 http 服务监控工具。仅需要简单的配置项即可完成服务的监控。

## 支持

* 发送通知到钉钉群，并 @ 相关人员
* 发送通知到邮箱
* 按照 cron 表达式定义监控时间点和时间间隔
* 使用 Web 页面增加、配置监控项
* 使用 SQLite 数据库，不需要额外提供数据
* 后端：springboot
* 前端：vue3 + elementplus
* 前后端统一部署，无需多余服务器、容器

## 页面预览

* 列表页 (http://39.101.201.146:9000/publicimages/monitor-example-list.png)

* 新增页 (http://39.101.201.146:9000/publicimages/monitor-example-add.png)

* 编辑页 (http://39.101.201.146:9000/publicimages/monitor-example-edit.png)


## 配置方法

| 字段编码                | 字段名称              | 说明                                       | 示例                             |
|:--------------------|:------------------|:-----------------------------------------|:-------------------------------|
| delete_status       | 删除标志              | 删除标志：0=未删除；1=已删除                         | 0                              |
| service_name        | 环境名称              | 待监控的服务名称，在发送消息时显示                        | 百度（baidu）                      |
| service_url         | 服务URL             | 待监控的url，监控程序访问此url                       | http://www.baidu.com           |
| service_check_type  | 监控方式              | 监控方式：1=看url返回是否是200；2=看url返回值是否包含 ok 字符串 | 1                              |
| service_environment | 环境名称              | 环境标志：dev=开发；test=测试，prod=正式              | dev                            |
| notify_emails       | 通知邮箱              | 被通知邮箱，多个以英文逗号分割                          | lmyanglei@gmail.com            |
| notify_mobiles      | 通知手机号             | 钉钉被at人的手机号，多个以英文逗号分割                     | 13800138000                    |
| webhook_url         | 钉钉接口地址            | 钉钉群接受通知的机器人的url                          | http://oapi.dingtalk.com/robot |
| webhook_keyword     | 钉钉关键词             | 钉钉通知所需的关键词                               | 服务监控                           |
| webhook_group_name  | 钉钉群名称             | 钉钉群名称                                    | 自己测试                           |
| cron                | cron表达式           | cron表达式，按corn表达式定义的时间点和时间间隔触发监控          | 0 0/10 * * * ?                 |
| timeout             | 监控超时时长            | 监控访问url的超时时间，单位毫秒                        | 10000                          |
| network             | 网络环境              | 区分内网、外网，只监控同网段服务:inner=内网；outter=外网      | inner                          |
| enable_status       | 是否启用              | 启用停用：1=启用；0=停用                           | 0                              |

## 在线体验

TODO

## LICENSE

mimu monitor is licenced under [MIT]() or [MulanPSL-2.0](),
and note that [MulanPSL-2.0 is compatible with Apache-2.0](),
but some third-party libraries are distributed using their [own licenses]().

## Releases
* 2023-03-18, [Release v0.1.0](), monitor.
* 2023-03-18, Created.

## Dockers
TODO

Beijing, 2023.03

lmyanglei

