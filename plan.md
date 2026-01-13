# 国产化API网关与管理平台 - 项目实施计划

## 一、项目概述

### 1.1 项目目标
构建一个支持国产化环境的API网关与管理平台，具备以下特性：
- 支持达梦DM8和MySQL 8.0双数据库
- 支持国密SM2/SM3/SM4加密算法
- 兼容国产操作系统（麒麟、统信、中科方德）
- 支持东方通TongWeb和Tomcat中间件
- 采用War包部署方式

### 1.2 技术架构
- **后端框架**：Spring Boot 2.7.15 + Spring MVC
- **数据库**：达梦DM8 + MySQL 8.0（双数据库兼容）
- **ORM框架**：MyBatis Plus 3.5.3 + Dynamic Datasource
- **安全认证**：Sa-Token 1.37.0 + JWT
- **缓存**：Redis 6.2+
- **加密算法**：国密SM2/SM3/SM4 + Hutool 5.8.21
- **前端框架**：Vue 3 + Element Plus + Vite
- **构建工具**：Maven 3.8+ + JDK 1.8/17（Dragonwell）

---

## 二、项目模块划分

### 2.1 核心模块列表

| 模块名称 | 类型 | 说明 |
|---------|------|------|
| common-module | JAR | 公共模块（基础类、工具类、配置） |
| datasource-module | JAR | 数据源模块（动态数据源、双数据库支持） |
| user-module | JAR | 用户模块（用户管理、权限管理） |
| encrypt-module | JAR | 加密模块（国密算法支持） |
| monitor-module | JAR | 监控模块（系统监控、日志） |
| api-generator-module | JAR | API生成模块（动态API生成） |
| api-gateway-war | WAR | 网关应用（API转发、鉴权、限流） |
| api-manager-war | WAR | 管理后台（API管理、系统配置） |
| frontend-project | - | 前端Vue项目 |

---

## 三、数据库设计

### 3.1 核心表结构

#### 3.1.1 系统用户表（SYS_USER）
| 字段 | 类型 | 说明 |
|------|------|------|
| ID | BIGINT | 用户ID（主键） |
| USERNAME | VARCHAR(50) | 用户名（唯一） |
| PASSWORD | VARCHAR(255) | 密码（加密） |
| REAL_NAME | VARCHAR(50) | 真实姓名 |
| EMAIL | VARCHAR(100) | 邮箱 |
| PHONE | VARCHAR(20) | 手机号 |
| STATUS | INT | 状态：0-禁用，1-启用 |
| LAST_LOGIN_TIME | TIMESTAMP | 最后登录时间 |
| LAST_LOGIN_IP | VARCHAR(50) | 最后登录IP |
| CREATE_TIME | TIMESTAMP | 创建时间 |
| UPDATE_TIME | TIMESTAMP | 更新时间 |

#### 3.1.2 API信息表（API_INFO）
| 字段 | 类型 | 说明 |
|------|------|------|
| ID | BIGINT | API ID（主键） |
| API_NAME | VARCHAR(100) | API名称 |
| API_PATH | VARCHAR(255) | API路径 |
| API_METHOD | VARCHAR(10) | 请求方法（GET/POST等） |
| DATASOURCE_ID | BIGINT | 数据源ID |
| SQL_CONTENT | CLOB/TEXT | SQL内容 |
| NEED_AUTH | INT | 是否需要认证：0-否，1-是 |
| ENCRYPT_TYPE | VARCHAR(20) | 加密类型（SM4/AES等） |
| STATUS | INT | 状态：0-草稿，1-发布，2-下线 |
| VERSION | VARCHAR(20) | 版本号 |
| CREATE_USER_ID | BIGINT | 创建人ID |
| CREATE_TIME | TIMESTAMP | 创建时间 |
| UPDATE_TIME | TIMESTAMP | 更新时间 |

#### 3.1.3 数据源配置表（DATASOURCE_CONFIG）
| 字段 | 类型 | 说明 |
|------|------|------|
| ID | BIGINT | 配置ID（主键） |
| DS_NAME | VARCHAR(100) | 数据源名称 |
| DS_TYPE | VARCHAR(50) | 数据库类型（DM/MYSQL等） |
| JDBC_URL | VARCHAR(500) | JDBC连接URL |
| USERNAME | VARCHAR(100) | 用户名 |
| PASSWORD | VARCHAR(255) | 密码（加密） |
| DRIVER_CLASS | VARCHAR(100) | 驱动类名 |
| INITIAL_SIZE | INT | 初始连接数 |
| MAX_ACTIVE | INT | 最大活跃连接数 |
| MIN_IDLE | INT | 最小空闲连接数 |
| MAX_WAIT | INT | 最大等待时间(ms) |
| TEST_QUERY | VARCHAR(100) | 测试查询SQL |
| STATUS | INT | 状态：0-禁用，1-启用 |
| HEALTH_STATUS | INT | 健康状态：0-异常，1-正常 |
| LAST_CHECK_TIME | TIMESTAMP | 最后检查时间 |
| CREATE_TIME | TIMESTAMP | 创建时间 |
| UPDATE_TIME | TIMESTAMP | 更新时间 |

---

## 四、实施步骤

### 阶段一：项目初始化（第1步）

#### 任务清单
1. **创建父项目POM**
   - 配置Spring Boot 2.7.15
   - 配置Maven多模块结构
   - 配置依赖管理（达梦驱动、MySQL驱动、MyBatis Plus等）

2. **创建公共模块（common-module）**
   - 创建基础实体类（BaseEntity）
   - 创建统一返回结果（Result、PageResult）
   - 创建全局异常处理器
   - 创建工具类（DateUtil、JsonUtil、BeanUtil等）
   - 创建自定义注解（@DataSource、@EncryptField等）

3. **创建配置文件**
   - application.yml（主配置）
   - application-dm.yml（达梦配置）
   - application-mysql.yml（MySQL配置）

4. **初始化Git仓库**
   - 创建.gitignore文件
   - 初始化Git仓库
   - 推送到GitHub

### 阶段二：数据源模块开发（第2步）

#### 任务清单
1. **创建动态数据源配置**
   - DynamicDataSource类
   - DataSourceContextHolder上下文
   - DatabaseType枚举

2. **创建数据源工厂**
   - DataSourceFactory
   - DmDataSourceProvider
   - MysqlDataSourceProvider

3. **创建数据源管理器**
   - DataSourceManager
   - ConnectionManager

4. **实现数据源健康检查**
   - 定时检查数据源连接状态
   - 异常数据源告警

### 阶段三：加密模块开发（第3步）

#### 任务清单
1. **实现国密算法**
   - Sm2Algorithm（SM2非对称加密）
   - Sm3Algorithm（SM3哈希）
   - Sm4Algorithm（SM4对称加密）

2. **创建加密管理器**
   - EncryptManager
   - KeyManager
   - CertificateManager

3. **创建加密工具类**
   - SmUtils
   - KeyPairUtils

### 阶段四：用户模块开发（第4步）

#### 任务清单
1. **创建用户实体和Mapper**
   - User实体
   - UserMapper
   - Role实体
   - Permission实体

2. **实现用户服务**
   - UserService（增删改查）
   - RoleService（角色管理）
   - PermissionService（权限管理）

3. **集成Sa-Token认证**
   - 配置Sa-Token
   - 实现登录/登出接口
   - 实现权限校验

### 阶段五：网关应用开发（第5步）

#### 任务清单
1. **创建网关核心配置**
   - WebMvcConfig
   - ServletInitializer（War包支持）
   - FilterConfig

2. **实现网关过滤器**
   - ApiAuthFilter（API认证）
   - RateLimitFilter（限流）
   - RequestLogFilter（请求日志）
   - ResponseEncryptFilter（响应加密）

3. **实现网关服务**
   - ApiRouteService（API路由）
   - GatewayService（网关核心）
   - CacheService（缓存服务）

4. **创建网关控制器**
   - GatewayController（转发控制器）
   - HealthController（健康检查）
   - MonitorController（监控接口）

### 阶段六：管理后台开发（第6步）

#### 任务清单
1. **创建管理后台控制器**
   - ApiManageController（API管理）
   - DataSourceController（数据源管理）
   - UserManageController（用户管理）
   - SystemConfigController（系统配置）

2. **实现管理服务**
   - ApiService（API CRUD）
   - DataSourceService（数据源CRUD）
   - SystemConfigService（系统配置）

### 阶段七：前端开发（第7步）

#### 任务清单
1. **初始化Vue3项目**
   - 使用Vite创建项目
   - 集成Element Plus
   - 配置路由

2. **开发核心页面**
   - 登录页
   - API管理页
   - 数据源管理页
   - 用户管理页
   - 系统配置页

### 阶段八：部署与测试（第8步）

#### 任务清单
1. **编写数据库脚本**
   - 达梦建表脚本
   - MySQL建表脚本
   - 初始化数据脚本

2. **配置部署文件**
   - Tomcat部署配置
   - 东方通部署配置
   - Docker配置

3. **编写部署文档**
   - README.md
   - DEPLOYMENT.md

---

## 五、代码规范

### 5.1 命名规范
- **包名**：全小写，使用点分隔，如 `com.apigateway.common`
- **类名**：大驼峰，如 `UserService`
- **方法名**：小驼峰，如 `getUserById`
- **常量名**：全大写，下划线分隔，如 `MAX_SIZE`
- **变量名**：小驼峰，如 `userName`

### 5.2 注释规范
- 类注释：说明类的功能和职责
- 方法注释：说明方法功能、参数、返回值
- 关键业务逻辑添加行内注释

### 5.3 代码结构规范
- Controller层：负责请求处理，不包含业务逻辑
- Service层：负责业务逻辑
- Mapper层：负责数据访问
- 实体类：使用Lombok简化代码

### 5.4 异常处理规范
- 使用全局异常处理器
- 业务异常使用BusinessException
- 返回统一的错误码和错误信息

---

## 六、Git提交规范

### 6.1 提交信息格式
```
<type>(<scope>): <subject>

<body>

<footer>
```

### 6.2 Type类型
- **feat**：新功能
- **fix**：修复bug
- **docs**：文档更新
- **style**：代码格式调整
- **refactor**：重构
- **test**：测试相关
- **chore**：构建/工具变动

### 6.3 示例
```
feat(datasource): 实现动态数据源切换功能

- 创建DynamicDataSource类
- 实现数据源上下文管理
- 添加数据源健康检查

Closes #1
```

---

## 七、项目时间规划

| 阶段 | 预计任务数 | 说明 |
|------|-----------|------|
| 阶段一 | 4 | 项目初始化 |
| 阶段二 | 4 | 数据源模块 |
| 阶段三 | 3 | 加密模块 |
| 阶段四 | 3 | 用户模块 |
| 阶段五 | 4 | 网关应用 |
| 阶段六 | 2 | 管理后台 |
| 阶段七 | 2 | 前端开发 |
| 阶段八 | 3 | 部署与测试 |
| **总计** | **25** | - |

---

## 八、注意事项

### 8.1 国产化适配
- 确保代码在国产JDK（Dragonwell）上正常运行
- 测试达梦数据库的SQL兼容性
- 验证国密算法的正确性

### 8.2 性能优化
- 使用Redis缓存热点数据
- 数据库连接池合理配置
- 避免N+1查询问题

### 8.3 安全考虑
- 密码使用BCrypt加密
- 敏感数据使用SM4加密
- JWT Token有效期控制
- API接口限流保护

### 8.4 日志规范
- 使用Logback日志框架
- 日志级别：ERROR/WARN/INFO/DEBUG
- 重要操作记录审计日志
