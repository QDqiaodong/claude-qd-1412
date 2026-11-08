# 报废汽车拆解厂管理系统

面向报废机动车进场、拆解、总成流向、危废暂存和注销结算的基础业务管理系统。

## 业务模块

- 报废车辆台账
- 拆解工单
- 五大总成台账
- 危废暂存记录
- 注销与结算

## 技术栈

Spring Boot 3.3.0 / Java 17 / Spring Data JPA / MySQL 8 / Redis 7 / Vue 3 / Element Plus / Vite / nginx。

## 启停

```bash
./start.sh
./stop.sh
```

清理容器与数据卷：

```bash
./clean.sh
```

默认地址：

- 前端：`http://127.0.0.1:8362/`
- 后端健康检查：`http://127.0.0.1:8473/actuator/health`
