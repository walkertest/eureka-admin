# 跑起来&&部署
- run起来  --done

# 支持多个eureka集群  --基本done
- 支持下拉框选择； --done
- 选择之后，显示集群名字  --done
- 选择之后，数据存在cookie当中保存，30天有效期  --done
- 打开页面的时候，如果cookie中已经有记录，展示对应集群的数据   --done
- 切换集群后，自动刷新页面数据  --done
- 切换集群后，没办法继续切换集群 --done @zenglongfei

# 功能优化
- 默认界面为管理界面   --done
- 用url进来之后，读取url hash值，定位到对应tab页面  --done
- serviceid界面，支持模糊搜索  --done, 遇到一个数据填入的问题，@zenglongfei
- 保证摘流量和放流量操作，正确执行 --done
- 摘流量和放流量操作前置一下  --done
- 获取out of serice的数据状态有问题，json序列号导致的 --done
- inactive之后，状态刷新页面以及状态值.   --done
- 支持测试环境和线上环境切换   --done
- 导航栏支持自定义显示组织名字.   --done
- 两个eureka的缓存问题   --done
- active和inactive按钮的颜色设置  --done
- 增加eureka默认查询页面的跳转链接    --done
- 获取和展示全量服务列表   --done
- 支持集群配置化，后端接口返回.  --done
- 最下面的列表没办法向右拖动   --todo

# 功能优化二期
- eureka服务器端的缓存问题，改为接口直接获取. --done
- 添加zone  --done
- 去除冗余代码  --done
- springboot 2.7.18 --done
- springboot 3.4.5&&jdk25   --done

# 功能优化三期
- 前端使用vue框架重写页面 --todo
- 整理成开源服务代码 --todo
- 分享技术博客 --todo

# 后续
- taf服务部署   --todo
