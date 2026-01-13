# 国产化API网关与管理平台

<div align="center">

![Version](https://img.shields.io/badge/version-1.0.0-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.15-green)
![Java](https://img.shields.io/badge/Java-1.8-orange)
![License](https://img.shields.io/badge/license-MIT-blue)

支持国产化环境的API网关平台，具备达梦/MySQL双数据库、国密算法加密等功能

</div>

---

## 📖 项目简介

国产化API网关与管理平台是一个基于Spring Boot开发的API网关系统，支持国产化环境部署，具备以下特性：

- ✅ 支持达梦DM8和MySQL 8.0双数据库
- ✅ 支持国密SM2/SM3/SM4加密算法
- ✅ 兼容国产操作系统（麒麟、统信、中科方德）
- ✅ 支持东方通TongWeb和Tomcat中间件
- ✅ 采用War包部署方式
- ✅ 动态数据源切换
- ✅ API自动生成与执行
- ✅ 系统监控与日志

---

## 🏛️ 技术架构

### 核心技术栈

| 技术领域 | 具体技术 | 版本 |
|---------|---------|------|
| 开发框架 | Spring Boot + Spring MVC | 2.7.15 |
| Web容器 | Tomcat 9+ / 东方通TongWeb | ≥9.0 |
| 数据库 | 达梦DM8 + MySQL 8.0 | DM8.0+ / 8.0+ |
| ORM框架 | MyBatis Plus + Dynamic Datasource | 3.5.3+ |
| 安全认证 | Sa-Token + JWT | 1.37.0+ |
| 缓存 | Redis 6.2+ | 6.2.0+ |
| 加密算法 | 国密SM2/SM3/SM4 + Hutool | 5.8.0+ |
| JDK | JDK 1.8/17（兼容Dragonwell） | 1.8+ |

---

## 📁 项目结构

```
apigateway-platform/
├── common-module/          # 公共模块（基础类、工具类）
├── datasource-module/      # 数据源模块（动态数据源）
├── encrypt-module/         # 加密模块（国密算法）
├── user-module/            # 用户模块（用户管理）
├── monitor-module/         # 监控模块
├── api-generator-module/   # API生成模块
├── api-gateway-war/        # 网关应用（War包）
├── api-manager-war/        # 管理后台（War包）
├── deployment/             # 部署配置
│   └── sql-scripts/        # 数据库脚本
├── pom.xml                 # Maven父项目
└── README.md               # 项目说明
```

---

## 🚀 快速开始

### 环境要求

- JDK 1.8+ 或 Dragonwell 17
- Maven 3.8+
- MySQL 8.0+ 或 达梦DM8
- Redis 6.2+
- Tomcat 9+ 或 东方通TongWeb

### 1. 克隆项目

```bash
git clone https://github.com/yayuke/apigateway-platform.git
cd apigateway-platform
```

### 2. 初始化数据库

#### MySQL

```bash
mysql -uroot -p123456 < deployment/sql-scripts/mysql/01_tables_mysql.sql
```

#### 达梦

```bash
sqlplus SYSDBA/SYSDBA@localhost:5236 @deployment/sql-scripts/dm/01_tables_dm.sql
```

### 3. 修改配置

编辑 `api-gateway-war/src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    dynamic:
      datasource:
        master:
          url: jdbc:mysql://localhost:3306/apigateway
          username: root
          password: your_password

  redis:
    host: localhost
    port: 6379
```

### 4. 编译打包

```bash
# MySQL环境
mvn clean package -Dspring.profiles.active=mysql -DskipTests

# 达梦环境
mvn clean package -Dspring.profiles.active=dm -DskipTests
```

### 5. 部署运行

#### Tomcat部署

```bash
cp api-gateway-war/target/api-gateway.war $TOMCAT_HOME/webapps/
cp api-manager-war/target/api-manager.war $TOMCAT_HOME/webapps/

$TOMCAT_HOME/bin/startup.sh
```

#### 东方通部署

```bash
cp api-gateway-war/target/api-gateway.war $TONGWEB_HOME/webapps/
cp api-manager-war/target/api-manager.war $TONGWEB_HOME/webapps/

$TONGWEB_HOME/bin/startup.sh
```

### 6. 访问应用

- **网关应用**: http://localhost:8080/gateway/health
- **管理后台**: http://localhost:8081/manager/
- **默认账号**: admin / admin123

---

## 📊 功能特性

### 1. 用户管理

- 用户增删改查
- 用户状态管理
- 密码加密存储（BCrypt）
- 登录日志记录

### 2. API管理

- API动态创建
- SQL在线编辑
- API发布/下线
- 请求参数配置
- 响应结果配置

### 3. 数据源管理

- 多数据源配置
- 数据源健康检查
- 动态数据源切换
- 支持达梦/MySQL/Oracle/SQLServer/PostgreSQL

### 4. 系统监控

- JVM内存监控
- CPU使用率监控
- 系统信息监控
- 数据源连接池监控
- Redis连接监控

### 5. 国密支持

- SM2非对称加密
- SM3哈希算法
- SM4对称加密
- 敏感数据加密存储

---

## 🔧 配置说明

### 数据库切换

#### MySQL配置

```yaml
spring:
  datasource:
    dynamic:
      datasource:
        master:
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://localhost:3306/apigateway?useUnicode=true&characterEncoding=utf8
```

#### 达梦配置

```yaml
spring:
  datasource:
    dynamic:
      datasource:
        master:
          driver-class-name: dm.jdbc.driver.DmDriver
          url: jdbc:dm://localhost:5236/APIGATEWAY
```

### 国密配置

```yaml
encrypt:
  algorithm: SM4
  sm4:
    key: 1234567890abcdef  # 16字节密钥
```

---

## 📝 API文档

### 网关接口

| 接口 | 方法 | 说明 |
|------|------|------|
| /gateway/health | GET | 健康检查 |
| /gateway/info | GET | 网关信息 |
| /api/apis/** | POST | API执行 |

### 管理接口

| 模块 | 路径 | 说明 |
|------|------|------|
| 用户管理 | /api/users | 用户CRUD |
| API管理 | /api/apis | API管理 |
| 数据源管理 | /api/datasources | 数据源管理 |
| 系统监控 | /api/monitor | 系统监控 |

---

## 📄 许可证

本项目采用 MIT 许可证 - 详见 [LICENSE](LICENSE) 文件

---

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

---

## 📮 联系方式

- 作者: yayuke
- GitHub: https://github.com/yayuke/apigateway-platform

---

## 🙏 致谢

感谢以下开源项目：

- [Spring Boot](https://spring.io/projects/spring-boot)
- [MyBatis Plus](https://baomidou.com/)
- [Sa-Token](https://sa-token.dev33.cn/)
- [Hutool](https://hutool.cn/)
