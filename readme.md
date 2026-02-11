[toc]
# 介绍
管理eureka的通用界面

## 特性
- 选择eureka集群的能力
- 查询eureka上服务节点列表
- 刷新页面
- 搜索eureka上服务节点列表（服务名、ip、状态）
- 批量摘除节点流量和放节点流量
- 查询总的服务数量
- 跳转到eureka自带的管理界面

## 技术关键点
- springboot 3.4.5
- jdk25
- api实现而不是eurkea client(降低依赖更加灵活)
- 前端框架vue(todo)

# 使用
- 本地访问：http://127.0.0.1:11111/ 或者：http://eureka-local.huya.info:11111/eurekaindex.html
- 测试环境：https://eurekaadmin.test.huya.info
- 线上环境：https://eurekaadmin.huya.info

# 参考
- eureka server库：https://github.com/Netflix/eureka
- api:https://github.com/Netflix/eureka/wiki/Eureka-REST-operations
- 开源eureka admin：https://github.com/SpringCloud/eureka-admin

