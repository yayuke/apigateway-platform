# 国产化API网关与管理平台

## 🏛️ 技术架构

### 核心技术栈

| 技术领域     | 具体技术                                      | 版本要求      | 备注             |
| :----------- | :-------------------------------------------- | :------------ | :--------------- |
| **开发框架** | Spring Boot 3.x + Spring MVC                  | 3.1.x         | 兼容国产环境     |
| **Web容器**  | Tomcat 9+/东方通TongWeb                       | ≥9.0          | War包部署        |
| **数据库**   | 达梦DM8 + MySQL 8.0                           | DM8.0+ / 8.0+ | 双数据库支持     |
| **ORM框架**  | MyBatis Plus + Dynamic Datasource             | 3.5.3+        | 多数据源动态切换 |
| **安全认证** | Sa-Token + JWT                                | 1.37.0+       | 国产化认证方案   |
| **缓存**     | Redis 6.2+                                    | 6.2.0+        | 哨兵/集群模式    |
| **加密算法** | 国密SM2/SM3/SM4 + Hutool                      | 5.8.0+        | 支持国密标准     |
| **前端框架** | Vue 3 + Element Plus + Vite                   | Vue 3.3+      | 响应式设计       |
| **构建工具** | Maven 3.8+ + JDK dragonwell-17.0.17.0.18+9-GA | 3.8.0+        | 兼容国产JDK      |
| **监控**     | Spring Boot Actuator + Prometheus             | 3.1.0+        | 国产监控对接     |

## 📁 项目结构设计

### 整体项目结构

text

```
apigateway-platform/
├── 📂 api-gateway-war/                    # 网关应用（War包）
├── 📂 api-manager-war/                   # 管理后台（War包）
├── 📂 common-module/                     # 公共模块（Jar）
├── 📂 datasource-module/                 # 数据源模块（Jar）
├── 📂 user-module/                       # 用户模块（Jar）
├── 📂 encrypt-module/                    # 加密模块（Jar）
├── 📂 monitor-module/                    # 监控模块（Jar）
├── 📂 api-generator-module/              # API生成模块（Jar）
├── 📂 frontend-project/                  # 前端项目
├── 📂 deployment/                        # 部署配置
│   ├── tomcat/                          # Tomcat部署配置
│   ├── tongweb/                         # 东方通部署配置
│   ├── sql-scripts/                     # 数据库脚本
│   └── scripts/                         # 运维脚本
├── 📂 config/                            # 配置文件
│   ├── application.yml                  # 主配置
│   ├── application-dm.yml               # 达梦配置
│   ├── application-mysql.yml            # MySQL配置
│   └── application-prod.yml             # 生产配置
├── pom.xml                              # Maven父项目
├── README.md                            # 项目说明
└── DEPLOYMENT.md                        # 部署文档
```



### 模块详细说明

#### 1. 网关应用模块 (`api-gateway-war`)

text

```
api-gateway-war/
├── src/main/
│   ├── java/com/apigateway/gateway/
│   │   ├── config/
│   │   │   ├── WebMvcConfig.java        # Spring MVC配置
│   │   │   ├── ServletInitializer.java  # War包启动类
│   │   │   ├── FilterConfig.java        # 过滤器配置
│   │   │   ├── InterceptorConfig.java   # 拦截器配置
│   │   │   └── TomcatConfig.java        # Tomcat优化配置
│   │   ├── controller/
│   │   │   ├── GatewayController.java   # 网关转发控制器
│   │   │   ├── HealthController.java    # 健康检查接口
│   │   │   └── MonitorController.java   # 监控接口
│   │   ├── filter/
│   │   │   ├── ApiAuthFilter.java       # API认证过滤器
│   │   │   ├── RateLimitFilter.java     # 限流过滤器
│   │   │   ├── RequestLogFilter.java    # 请求日志过滤器
│   │   │   └── ResponseEncryptFilter.java # 响应加密过滤器
│   │   ├── interceptor/
│   │   │   ├── AuthInterceptor.java     # 认证拦截器
│   │   │   └── PermissionInterceptor.java # 权限拦截器
│   │   ├── service/
│   │   │   ├── ApiRouteService.java     # API路由服务
│   │   │   ├── GatewayService.java      # 网关核心服务
│   │   │   └── CacheService.java        # 缓存服务
│   │   ├── Aspect/
│   │   │   └── GatewayLogAspect.java    # 网关日志切面
│   │   └── ApiGatewayApplication.java   # Spring Boot启动类
│   ├── resources/
│   │   ├── config/
│   │   │   ├── application-gateway.yml  # 网关专用配置
│   │   │   └── logback-gateway.xml      # 网关日志配置
│   │   ├── static/                      # 静态资源
│   │   └── templates/                   # 模板文件
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── web.xml                  # Web应用配置
│       │   └── jboss-web.xml            # Jboss配置（可选）
│       └── META-INF/
│           └── MANIFEST.MF              # 清单文件
├── pom.xml                              # Maven配置
└── Dockerfile                           # Docker构建文件
```



#### 2. 管理后台模块 (`api-manager-war`)

text

```
api-manager-war/
├── src/main/
│   ├── java/com/apigateway/manager/
│   │   ├── config/
│   │   │   ├── WebMvcConfig.java
│   │   │   ├── ServletInitializer.java
│   │   │   ├── SwaggerConfig.java       # API文档配置
│   │   │   └── SecurityConfig.java      # 安全管理配置
│   │   ├── controller/
│   │   │   ├── ApiManageController.java # API管理
│   │   │   ├── DataSourceController.java # 数据源管理
│   │   │   ├── UserManageController.java # 用户管理
│   │   │   ├── SystemConfigController.java # 系统配置
│   │   │   └── MonitorController.java   # 监控管理
│   │   ├── service/
│   │   │   ├── ApiService.java
│   │   │   ├── DataSourceService.java
│   │   │   ├── UserService.java
│   │   │   └── SystemConfigService.java
│   │   └── ApiManagerApplication.java
│   ├── resources/
│   │   ├── config/
│   │   │   ├── application-manager.yml
│   │   │   └── logback-manager.xml
│   │   └── mapper/                      # MyBatis映射文件
│   └── webapp/
│       └── WEB-INF/
│           └── web.xml
├── pom.xml
└── Dockerfile
```



#### 3. 数据源模块 (`datasource-module`)

text

```
datasource-module/
├── src/main/java/com/apigateway/datasource/
│   ├── config/
│   │   ├── DynamicDataSourceConfig.java # 动态数据源配置
│   │   ├── DmDataSourceConfig.java      # 达梦数据源配置
│   │   ├── MysqlDataSourceConfig.java   # MySQL数据源配置
│   │   └── DataSourceAspect.java        # 数据源切面
│   ├── core/
│   │   ├── DynamicDataSource.java       # 动态数据源实现
│   │   ├── DataSourceContextHolder.java # 数据源上下文
│   │   └── DatabaseType.java            # 数据库类型枚举
│   ├── factory/
│   │   ├── DataSourceFactory.java       # 数据源工厂
│   │   └── ConnectionFactory.java       # 连接工厂
│   ├── provider/
│   │   ├── DmDataSourceProvider.java    # 达梦数据源提供者
│   │   └── MysqlDataSourceProvider.java # MySQL数据源提供者
│   ├── manager/
│   │   ├── DataSourceManager.java       # 数据源管理器
│   │   └── ConnectionManager.java       # 连接管理器
│   ├── entity/
│   │   ├── DataSourceConfig.java        # 数据源配置实体
│   │   ├── DatabaseInfo.java            # 数据库信息实体
│   │   └── ConnectionInfo.java          # 连接信息实体
│   └── util/
│       ├── DmDialect.java               # 达梦方言
│       ├── DatabaseUtils.java           # 数据库工具类
│       └── SqlUtils.java                # SQL工具类
├── src/main/resources/
│   ├── mapper/                          # 数据源Mapper
│   └── sql/                             # SQL脚本
│       ├── dm/                          # 达梦SQL
│       └── mysql/                       # MySQL SQL
└── pom.xml
```



#### 4. 加密模块 (`encrypt-module`)

text

```
encrypt-module/
├── src/main/java/com/apigateway/encrypt/
│   ├── algorithm/
│   │   ├── Sm2Algorithm.java           # SM2算法实现
│   │   ├── Sm3Algorithm.java           # SM3算法实现
│   │   ├── Sm4Algorithm.java           # SM4算法实现
│   │   ├── AesAlgorithm.java           # AES算法实现
│   │   └── RsaAlgorithm.java           # RSA算法实现
│   ├── manager/
│   │   ├── EncryptManager.java         # 加密管理器
│   │   ├── KeyManager.java             # 密钥管理器
│   │   └── CertificateManager.java     # 证书管理器
│   ├── factory/
│   │   └── AlgorithmFactory.java       # 算法工厂
│   ├── config/
│   │   └── EncryptConfig.java          # 加密配置
│   └── util/
│       ├── SmUtils.java                # 国密工具类
│       └── KeyPairUtils.java           # 密钥对工具
└── pom.xml
```



#### 5. 公共模块 (`common-module`)

text

```
common-module/
├── src/main/java/com/apigateway/common/
│   ├── core/
│   │   ├── BaseEntity.java             # 基础实体
│   │   ├── BaseController.java         # 基础控制器
│   │   ├── BaseService.java            # 基础服务
│   │   ├── Result.java                 # 统一返回结果
│   │   └── PageResult.java             # 分页结果
│   ├── constant/
│   │   ├── ResponseCode.java           # 响应码常量
│   │   ├── SystemConstant.java         # 系统常量
│   │   ├── DbConstant.java             # 数据库常量
│   │   └── CacheKey.java               # 缓存键常量
│   ├── exception/
│   │   ├── BusinessException.java      # 业务异常
│   │   ├── GlobalExceptionHandler.java # 全局异常处理
│   │   └── ErrorCode.java              # 错误码枚举
│   ├── annotation/
│   │   ├── DataSource.java             # 数据源注解
│   │   ├── EncryptField.java           # 加密字段注解
│   │   ├── ApiLog.java                 # API日志注解
│   │   └── RateLimit.java              # 限流注解
│   ├── util/
│   │   ├── DateUtil.java               # 日期工具
│   │   ├── JsonUtil.java               # JSON工具
│   │   ├── IpUtil.java                 # IP工具
│   │   ├── ValidatorUtil.java          # 验证工具
│   │   └── BeanUtil.java               # Bean工具
│   └── config/
│       ├── MybatisPlusConfig.java      # MyBatis配置
│       ├── RedisConfig.java            # Redis配置
│       └── CorsConfig.java             # 跨域配置
└── pom.xml
```

## 🗄️ 数据库设计（双数据库兼容）

### 系统数据库表设计

#### 1. 用户与权限表（通用设计）

sql

```
-- 达梦DM8语法
CREATE TABLE "SYS_USER" (
    "ID" BIGINT NOT NULL,
    "USERNAME" VARCHAR(50) NOT NULL,
    "PASSWORD" VARCHAR(255) NOT NULL,
    "REAL_NAME" VARCHAR(50),
    "EMAIL" VARCHAR(100),
    "PHONE" VARCHAR(20),
    "STATUS" INT DEFAULT 1,
    "LAST_LOGIN_TIME" TIMESTAMP,
    "LAST_LOGIN_IP" VARCHAR(50),
    "CREATE_TIME" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "UPDATE_TIME" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY ("ID")
);
COMMENT ON TABLE "SYS_USER" IS '系统用户表';
COMMENT ON COLUMN "SYS_USER"."ID" IS '用户ID';
COMMENT ON COLUMN "SYS_USER"."USERNAME" IS '用户名';
COMMENT ON COLUMN "SYS_USER"."PASSWORD" IS '密码';
COMMENT ON COLUMN "SYS_USER"."STATUS" IS '状态:0-禁用,1-启用';

-- MySQL语法（差异处理）
CREATE TABLE sys_user (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    real_name VARCHAR(50),
    email VARCHAR(100),
    phone VARCHAR(20),
    status INT DEFAULT 1,
    last_login_time DATETIME,
    last_login_ip VARCHAR(50),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';
```



#### 2. API管理表

sql

```
-- 达梦语法
CREATE TABLE "API_INFO" (
    "ID" BIGINT NOT NULL,
    "API_NAME" VARCHAR(100) NOT NULL,
    "API_PATH" VARCHAR(255) NOT NULL,
    "API_METHOD" VARCHAR(10) NOT NULL,
    "DATASOURCE_ID" BIGINT,
    "SQL_CONTENT" CLOB,
    "NEED_AUTH" INT DEFAULT 1,
    "ENCRYPT_TYPE" VARCHAR(20),
    "STATUS" INT DEFAULT 0,
    "VERSION" VARCHAR(20) DEFAULT '1.0',
    "CREATE_USER_ID" BIGINT,
    "CREATE_TIME" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "UPDATE_TIME" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY ("ID")
);

-- MySQL语法
CREATE TABLE api_info (
    id BIGINT NOT NULL AUTO_INCREMENT,
    api_name VARCHAR(100) NOT NULL,
    api_path VARCHAR(255) NOT NULL,
    api_method VARCHAR(10) NOT NULL,
    datasource_id BIGINT,
    sql_content TEXT,
    need_auth INT DEFAULT 1,
    encrypt_type VARCHAR(20),
    status INT DEFAULT 0,
    version VARCHAR(20) DEFAULT '1.0',
    create_user_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_api_path (api_path),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='API信息表';
```



#### 3. 数据源配置表

sql

```
-- 达梦语法
CREATE TABLE "DATASOURCE_CONFIG" (
    "ID" BIGINT NOT NULL,
    "DS_NAME" VARCHAR(100) NOT NULL,
    "DS_TYPE" VARCHAR(50) NOT NULL,
    "JDBC_URL" VARCHAR(500) NOT NULL,
    "USERNAME" VARCHAR(100) NOT NULL,
    "PASSWORD" VARCHAR(255) NOT NULL,
    "DRIVER_CLASS" VARCHAR(100),
    "INITIAL_SIZE" INT DEFAULT 5,
    "MAX_ACTIVE" INT DEFAULT 20,
    "MIN_IDLE" INT DEFAULT 5,
    "MAX_WAIT" INT DEFAULT 60000,
    "TEST_QUERY" VARCHAR(100),
    "STATUS" INT DEFAULT 1,
    "HEALTH_STATUS" INT DEFAULT 0,
    "LAST_CHECK_TIME" TIMESTAMP,
    "CREATE_TIME" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "UPDATE_TIME" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY ("ID")
);

-- 数据库类型枚举值：DM、MYSQL、ORACLE、SQLSERVER、POSTGRESQL
```



## ⚙️ 配置文件设计

### 1. 主配置文件 (`application.yml`)

yaml

```
# 应用基础配置
server:
  port: 8080
  servlet:
    context-path: /api-gateway
  tomcat:
    max-threads: 200
    min-spare-threads: 10
    max-connections: 10000
    connection-timeout: 30000

# Spring配置
spring:
  application:
    name: api-gateway-platform
  profiles:
    active: @spring.profiles.active@
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 100MB
  mvc:
    throw-exception-if-no-handler-found: true
    static-path-pattern: /static/**
  resources:
    add-mappings: false

# 数据源配置（动态加载）
datasource:
  primary:
    type: DM  # 主数据库类型：DM/MYSQL
    config-id: 1  # 默认数据源配置ID
  dynamic: true   # 是否启用动态数据源
  health-check:
    enabled: true
    interval: 30000  # 健康检查间隔(ms)

# 加密配置
encrypt:
  algorithm: SM4  # 默认加密算法：SM4/AES/RSA
  mode: ECB      # 加密模式：ECB/CBC
  key-length: 128
  sm4:
    key: ${ENCRYPT_SM4_KEY:1234567890abcdef}
  sm2:
    public-key: ${ENCRYPT_SM2_PUB_KEY}
    private-key: ${ENCRYPT_SM2_PRI_KEY}

# 安全配置
security:
  jwt:
    secret: ${JWT_SECRET:api-gateway-secret-key-2024}
    expiration: 7200000  # token过期时间(ms)
    header: Authorization
  sa-token:
    token-name: satoken
    timeout: 2592000
    active-timeout: -1
    is-concurrent: true
    is-share: false
    token-style: uuid

# 缓存配置
cache:
  type: redis
  redis:
    key-prefix: api:gateway:
    ttl: 3600
    max-idle: 8
    min-idle: 0

# 监控配置
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
  metrics:
    export:
      prometheus:
        enabled: true

# 日志配置
logging:
  level:
    com.apigateway: INFO
    org.springframework.web: WARN
  file:
    path: logs/
    name: gateway.log
  logback:
    rollingpolicy:
      max-file-size: 10MB
      max-history: 30
```



### 2. 达梦数据库配置 (`application-dm.yml`)

yaml

```
# 达梦数据库配置
spring:
  datasource:
    # 系统数据库（用于存储配置信息）
    system:
      driver-class-name: dm.jdbc.driver.DmDriver
      url: jdbc:dm://${DM_HOST:localhost}:${DM_PORT:5236}/${DM_DATABASE:APIGATEWAY}?schema=${DM_SCHEMA:APIGATEWAY}
      username: ${DM_USERNAME:SYSDBA}
      password: ${DM_PASSWORD:SYSDBA}
      initial-size: 5
      max-active: 20
      min-idle: 5
      max-wait: 60000
      validation-query: SELECT 1 FROM DUAL
    # MyBatis配置
    mybatis-plus:
      configuration:
        map-underscore-to-camel-case: true
        log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
      global-config:
        db-config:
          id-type: ASSIGN_ID
          logic-delete-field: deleted
          logic-delete-value: 1
          logic-not-delete-value: 0
      mapper-locations: classpath*:mapper/**/*.xml
      type-aliases-package: com.apigateway.**.entity

# 达梦方言配置
mybatis-plus:
  dialect:
    dm:
      keywords: "USER,KEY,VALUE,DATE,TIME,TIMESTAMP"
      wrap-symbol: '"'
```



### 3. MySQL数据库配置 (`application-mysql.yml`)

yaml

```
# MySQL数据库配置
spring:
  datasource:
    # 系统数据库
    system:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:apigateway}?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
      username: ${MYSQL_USERNAME:root}
      password: ${MYSQL_PASSWORD:123456}
      initial-size: 5
      max-active: 20
      min-idle: 5
      max-wait: 60000
      validation-query: SELECT 1
    # MyBatis配置
    mybatis-plus:
      configuration:
        map-underscore-to-camel-case: true
      global-config:
        db-config:
          id-type: ASSIGN_ID
          logic-delete-field: deleted
          logic-delete-value: 1
          logic-not-delete-value: 0

# MySQL方言配置
mybatis-plus:
  dialect:
    mysql:
      engine: InnoDB
      charset: utf8mb4
      collation: utf8mb4_general_ci
```



## 📦 Maven配置

### 父项目POM (`pom.xml`)

xml

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.apigateway</groupId>
    <artifactId>apigateway-platform</artifactId>
    <version>1.0.0</version>
    <packaging>pom</packaging>
    <name>国产化API网关平台</name>
    <description>支持达梦和MySQL双数据库的API网关平台</description>

    <properties>
        <!-- JDK版本 -->
        <java.version>1.8</java.version>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        
        <!-- Spring Boot版本 -->
        <spring-boot.version>2.7.15</spring-boot.version>
        
        <!-- 数据库驱动版本 -->
        <dm.jdbc.version>8.1.1.193</dm.jdbc.version>
        <mysql.jdbc.version>8.0.33</mysql.jdbc.version>
        
        <!-- 工具版本 -->
        <mybatis-plus.version>3.5.3.1</mybatis-plus.version>
        <dynamic-datasource.version>4.2.0</dynamic-datasource.version>
        <hutool.version>5.8.21</hutool.version>
        <sa-token.version>1.37.0</sa-token.version>
    </properties>

    <modules>
        <module>common-module</module>
        <module>datasource-module</module>
        <module>user-module</module>
        <module>encrypt-module</module>
        <module>monitor-module</module>
        <module>api-generator-module</module>
        <module>api-gateway-war</module>
        <module>api-manager-war</module>
    </modules>

    <dependencyManagement>
        <dependencies>
            <!-- Spring Boot BOM -->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            
            <!-- 达梦数据库驱动 -->
            <dependency>
                <groupId>com.dameng</groupId>
                <artifactId>DmJdbcDriver18</artifactId>
                <version>${dm.jdbc.version}</version>
            </dependency>
            
            <!-- MySQL数据库驱动 -->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql.jdbc.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <pluginManagement>
            <plugins>
                <!-- Maven编译插件 -->
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.11.0</version>
                    <configuration>
                        <source>${java.version}</source>
                        <target>${java.version}</target>
                        <encoding>UTF-8</encoding>
                    </configuration>
                </plugin>
                
                <!-- Spring Boot Maven插件 -->
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                    <version>${spring-boot.version}</version>
                    <configuration>
                        <!-- 排除内嵌的Tomcat，使用外部容器 -->
                        <excludes>
                            <exclude>
                                <groupId>org.springframework.boot</groupId>
                                <artifactId>spring-boot-starter-tomcat</artifactId>
                            </exclude>
                        </excludes>
                    </configuration>
                </plugin>
                
                <!-- War包打包插件 -->
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-war-plugin</artifactId>
                    <version>3.3.2</version>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>
</project>
```



### War模块POM示例 (`api-gateway-war/pom.xml`)

xml

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>com.apigateway</groupId>
        <artifactId>apigateway-platform</artifactId>
        <version>1.0.0</version>
    </parent>
    
    <modelVersion>4.0.0</modelVersion>
    <artifactId>api-gateway-war</artifactId>
    <packaging>war</packaging>
    <name>API Gateway War Module</name>

    <dependencies>
        <!-- Spring Boot Web (排除内嵌Tomcat) -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        
        <!-- 依赖模块 -->
        <dependency>
            <groupId>com.apigateway</groupId>
            <artifactId>common-module</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>com.apigateway</groupId>
            <artifactId>datasource-module</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>com.apigateway</groupId>
            <artifactId>encrypt-module</artifactId>
            <version>${project.version}</version>
        </dependency>
        
        <!-- 数据库驱动（可选依赖） -->
        <dependency>
            <groupId>com.dameng</groupId>
            <artifactId>DmJdbcDriver18</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <optional>true</optional>
        </dependency>
        
        <!-- Servlet API (provided scope) -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <finalName>api-gateway</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <mainClass>com.apigateway.gateway.ApiGatewayApplication</mainClass>
                </configuration>
            </plugin>
            
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <configuration>
                    <failOnMissingWebXml>false</failOnMissingWebXml>
                    <webResources>
                        <resource>
                            <directory>src/main/webapp</directory>
                        </resource>
                    </webResources>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```



## 🚀 部署方案

### 1. Tomcat部署结构

text

```
tomcat/
├── bin/                    # Tomcat启动脚本
├── conf/                  # Tomcat配置
│   ├── server.xml        # 服务器配置
│   ├── context.xml       # 上下文配置
│   └── catalina.properties # Catalina配置
├── webapps/              # 应用部署目录
│   ├── api-gateway.war  # 网关应用
│   ├── api-gateway/     # 解压后的网关应用
│   ├── api-manager.war  # 管理后台
│   └── api-manager/     # 解压后的管理后台
├── logs/                 # 日志目录
└── lib/                  # 库文件目录
```



### 2. 东方通TongWeb部署结构

text

```
tongweb/
├── bin/                    # TongWeb启动脚本
├── conf/                  # TongWeb配置
│   ├── server.xml
│   ├── tongweb.xml
│   └── application.xml    # 应用配置
├── webapps/              # 应用部署目录
├── logs/                 # 日志目录
└── lib/                  # 库文件目录
    ├── dm-jdbc-driver.jar # 达梦驱动
    └── mysql-driver.jar   # MySQL驱动
```



### 3. 数据库初始化脚本位置

text

```
deployment/sql-scripts/
├── dm/                    # 达梦数据库脚本
│   ├── 01_schema_dm.sql  # 建库脚本
│   ├── 02_tables_dm.sql  # 建表脚本
│   ├── 03_data_dm.sql    # 初始化数据
│   └── 04_index_dm.sql   # 索引脚本
└── mysql/                # MySQL数据库脚本
    ├── 01_schema_mysql.sql
    ├── 02_tables_mysql.sql
    ├── 03_data_mysql.sql
    └── 04_index_mysql.sql
```



## 🔧 构建与部署流程

### 1. 项目构建

bash

```
# 克隆项目
git clone <repository-url>
cd apigateway-platform

# 编译整个项目
mvn clean install -DskipTests

# 指定配置文件构建（达梦环境）
mvn clean package -Dspring.profiles.active=dm -DskipTests

# 指定配置文件构建（MySQL环境）
mvn clean package -Dspring.profiles.active=mysql -DskipTests
```



### 2. 应用打包

生成的War包位置：

- `api-gateway-war/target/api-gateway.war`
- `api-manager-war/target/api-manager.war`

### 3. 数据库初始化

bash

```
# 达梦数据库初始化
sqlplus SYSDBA/SYSDBA@localhost:5236 @deployment/sql-scripts/dm/01_schema_dm.sql
sqlplus SYSDBA/SYSDBA@localhost:5236 @deployment/sql-scripts/dm/02_tables_dm.sql

# MySQL数据库初始化
mysql -uroot -p123456 < deployment/sql-scripts/mysql/01_schema_mysql.sql
mysql -uroot -p123456 < deployment/sql-scripts/mysql/02_tables_mysql.sql
```



### 4. 应用部署

bash

```
# Tomcat部署
cp api-gateway-war/target/api-gateway.war $TOMCAT_HOME/webapps/
cp api-manager-war/target/api-manager.war $TOMCAT_HOME/webapps/

# 启动Tomcat
$TOMCAT_HOME/bin/startup.sh

# 东方通部署
cp api-gateway-war/target/api-gateway.war $TONGWEB_HOME/webapps/
cp api-manager-war/target/api-manager.war $TONGWEB_HOME/webapps/

# 启动东方通
$TONGWEB_HOME/bin/startup.sh
```



## 📊 监控与运维

### 监控端点

| 端点       | 路径                     | 说明           |
| :--------- | :----------------------- | :------------- |
| 健康检查   | `/actuator/health`       | 应用健康状态   |
| 应用信息   | `/actuator/info`         | 应用基本信息   |
| 性能指标   | `/actuator/metrics`      | 性能指标数据   |
| 数据库检查 | `/actuator/health/db`    | 数据库连接状态 |
| Redis检查  | `/actuator/health/redis` | Redis连接状态  |
| 线程信息   | `/actuator/threaddump`   | 线程堆栈信息   |

### 日志文件结构

text

```
logs/
├── gateway/              # 网关日志
│   ├── gateway.log      # 主日志文件
│   ├── gateway-error.log # 错误日志
│   ├── gateway-access.log # 访问日志
│   └── gateway-sql.log  # SQL日志
├── manager/             # 管理后台日志
│   ├── manager.log
│   └── manager-error.log
├── audit/               # 审计日志
│   └── audit.log
└── system/              # 系统日志
    └── system.log
```



## ✅ 国产化适配检查清单

### 已完成适配项

- **操作系统**：支持麒麟、统信、中科方德
- **中间件**：Tomcat 9+、东方通TongWeb
- **数据库**：达梦DM8、MySQL 8.0
- **JDK版本**：JDK 1.8/11/17兼容
- **加密算法**：国密SM2/SM3/SM4
- **打包方式**：War包，支持外部容器部署
- **字符编码**：UTF-8全支持
- **时区配置**：Asia/Shanghai

