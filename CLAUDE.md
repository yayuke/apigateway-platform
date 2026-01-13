# SpringBoot项目代码开发规范

你是一个专业的Java后端开发工程师，精通Spring Boot、MyBatis Plus、Sa-Token等技术栈。在开发过程中，必须严格遵守以下规范。

---

## 一、项目技术栈

### 1.1 核心框架
- **Spring Boot**: 2.7.15
- **Spring MVC**: 用于Web层开发
- **MyBatis Plus**: 3.5.3.1（ORM框架）
- **Dynamic Datasource**: 4.2.0（动态数据源）
- **Sa-Token**: 1.37.0（认证授权）
- **Hutool**: 5.8.21（工具类库）
- **Redis**: 缓存中间件

### 1.2 数据库支持
- **达梦DM8**: 国产化数据库
- **MySQL 8.0**: 关系型数据库

### 1.3 加密算法
- **国密SM2**: 非对称加密
- **国密SM3**: 哈希算法
- **国密SM4**: 对称加密

### 1.4 部署方式
- **War包**: 支持外部Tomcat/东方通TongWeb部署

---

## 二、代码规范

### 2.1 命名规范

#### 包命名
```
com.apigateway.{模块名}.{层}
```
示例：
- `com.apigateway.common.core` - 公共核心
- `com.apigateway.user.service` - 用户服务层
- `com.apigateway.gateway.controller` - 网关控制层

#### 类命名
- **实体类**: 使用业务名称 + Entity，如 `UserEntity`
- **DTO类**: 使用业务名称 + DTO，如 `UserDTO`
- **VO类**: 使用业务名称 + VO，如 `UserVO`
- **Controller**: 使用业务名称 + Controller，如 `UserController`
- **Service接口**: 使用业务名称 + Service，如 `IUserService`
- **Service实现**: 使用业务名称 + ServiceImpl，如 `UserServiceImpl`
- **Mapper接口**: 使用业务名称 + Mapper，如 `UserMapper`

#### 方法命名
| 功能 | 命名 | 示例 |
|------|------|------|
| 查询单个 | getXxx | `getUserById` |
| 查询列表 | listXxx | `listUsersByStatus` |
| 查询分页 | pageXxx | `pageUsers` |
| 保存 | saveXxx | `saveUser` |
| 更新 | updateXxx | `updateUser` |
| 删除 | removeXxx | `removeUser` |
| 统计 | countXxx | `countUsers` |

#### 变量命名
```java
// Good - 小驼峰命名
String userName;
Integer userAge;
List<User> userList;

// Bad - 不规范
String user_name;
String UserName;
```

#### 常量命名
```java
// Good - 全大写，下划线分隔
public static final String MAX_SIZE = "100";
public static final Integer DEFAULT_PAGE_SIZE = 10;

// Bad - 不规范
public static final String maxSize = "100";
```

### 2.2 注释规范

#### 类注释
```java
/**
 * 用户服务实现类
 *
 * @author apigateway
 * @since 1.0.0
 */
public class UserServiceImpl implements IUserService {
    // ...
}
```

#### 方法注释
```java
/**
 * 根据用户ID查询用户信息
 *
 * @param userId 用户ID
 * @return 用户信息，不存在返回null
 */
public User getUserById(Long userId) {
    // ...
}
```

#### 关键业务逻辑注释
```java
// 1. 验证用户权限
if (!hasPermission(userId)) {
    throw new BusinessException("无权限访问");
}

// 2. 查询用户信息
User user = userMapper.selectById(userId);

// 3. 加密敏感信息
user.setPhone(SmUtils.encrypt(user.getPhone()));
```

### 2.3 代码结构规范

#### Controller层规范
```java
@RestController
@RequestMapping("/api/users")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 分页查询用户列表
     */
    @GetMapping("/page")
    public Result<PageResult<UserVO>> pageUsers(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        // 参数验证
        if (pageNum < 1 || pageSize < 1) {
            return Result.error("分页参数不合法");
        }

        // 调用服务层
        PageResult<UserVO> result = userService.pageUsers(pageNum, pageSize);

        // 返回结果
        return Result.success(result);
    }
}
```

**Controller层职责**：
- 负责请求参数接收和验证
- 调用Service层处理业务
- 返回统一的响应结果
- 不包含复杂业务逻辑

#### Service层规范
```java
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public User getUserById(Long userId) {
        // 1. 参数校验
        if (userId == null) {
            throw new BusinessException("用户ID不能为空");
        }

        // 2. 查询缓存
        String cacheKey = "user:" + userId;
        User user = (User) redisTemplate.opsForValue().get(cacheKey);
        if (user != null) {
            return user;
        }

        // 3. 查询数据库
        user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 4. 写入缓存
        redisTemplate.opsForValue().set(cacheKey, user, 1, TimeUnit.HOURS);

        return user;
    }
}
```

**Service层职责**：
- 处理核心业务逻辑
- 事务控制（使用@Transactional）
- 缓存管理
- 调用Mapper层进行数据操作

#### Mapper层规范
```java
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据状态查询用户列表
     */
    List<User> selectUsersByStatus(@Param("status") Integer status);

    /**
     * 统计用户数量
     */
    Integer countUsers();
}
```

#### 实体类规范
```java
/**
 * 用户实体类
 */
@Data
@TableName("sys_user")
public class User extends BaseEntity {

    /**
     * 用户ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码（加密存储）
     */
    @TableField("password")
    private String password;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 状态：0-禁用，1-启用
     */
    @TableField("status")
    private Integer status;

    /**
     * 最后登录时间
     */
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    @TableField("last_login_ip")
    private String lastLoginIp;
}
```

### 2.4 异常处理规范

#### 自定义业务异常
```java
public class BusinessException extends RuntimeException {
    private Integer code;
    private String message;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public BusinessException(String message) {
        super(message);
        this.code = 500;
        this.message = message;
    }
}
```

#### 全局异常处理器
```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常处理
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        log.error("业务异常：{}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 系统异常处理
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("系统异常", e);
        return Result.error(500, "系统异常，请联系管理员");
    }
}
```

#### 统一返回结果
```java
@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
```

### 2.5 数据库操作规范

#### 使用MyBatis Plus
```java
// 1. 插入数据
User user = new User();
user.setUsername("test");
user.setPassword("123456");
userMapper.insert(user);

// 2. 更新数据
User user = userMapper.selectById(1L);
user.setPassword("654321");
userMapper.updateById(user);

// 3. 删除数据
userMapper.deleteById(1L);

// 4. 查询数据
User user = userMapper.selectById(1L);
List<User> users = userMapper.selectList(null);

// 5. 条件查询
LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
wrapper.eq(User::getUsername, "test")
       .gt(User::getCreateTime, LocalDateTime.now().minusDays(7));
List<User> users = userMapper.selectList(wrapper);
```

#### 动态数据源使用
```java
// 使用@DataSource注解切换数据源
@DataSource(DataSourceType.DM)
public List<User> getUsersFromDM() {
    return userMapper.selectList(null);
}

@DataSource(DataSourceType.MYSQL)
public List<User> getUsersFromMySQL() {
    return userMapper.selectList(null);
}

// 手动切换数据源
public void switchDataSource() {
    DataSourceContextHolder.setDataSourceType(DataSourceType.DM);
    // 操作达梦数据库
    DataSourceContextHolder.clearDataSourceType();
}
```

### 2.6 缓存使用规范

```java
// 使用Spring Cache注解
@Cacheable(value = "user", key = "#userId")
public User getUserById(Long userId) {
    return userMapper.selectById(userId);
}

@CachePut(value = "user", key = "#user.id")
public User updateUser(User user) {
    userMapper.updateById(user);
    return user;
}

@CacheEvict(value = "user", key = "#userId")
public void deleteUser(Long userId) {
    userMapper.deleteById(userId);
}

// 使用RedisTemplate
@Service
public class CacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
```

### 2.7 加密使用规范

```java
// SM4对称加密
public class SmUtils {

    /**
     * SM4加密
     */
    public static String encrypt(String content, String key) {
        SM4 sm4 = SmUtil.sm4(key.getBytes(StandardCharsets.UTF_8));
        return sm4.encryptHex(content);
    }

    /**
     * SM4解密
     */
    public static String decrypt(String encrypted, String key) {
        SM4 sm4 = SmUtil.sm4(key.getBytes(StandardCharsets.UTF_8));
        return sm4.decryptStr(encrypted);
    }

    /**
     * SM3哈希
     */
    public static String hash(String content) {
        return SmUtil.sm3(content);
    }
}
```

### 2.8 配置文件规范

#### application.yml
```yaml
# 应用配置
server:
  port: 8080
  servlet:
    context-path: /api-gateway

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

# 数据源配置
spring:
  datasource:
    dynamic:
      primary: master
      strict: false
      datasource:
        master:
          url: jdbc:mysql://localhost:3306/apigateway
          username: root
          password: 123456
          driver-class-name: com.mysql.cj.jdbc.Driver

# MyBatis Plus配置
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

# Sa-Token配置
sa-token:
  token-name: satoken
  timeout: 2592000
  active-timeout: -1
  is-concurrent: true
  is-share: false
  token-style: uuid
```

### 2.9 日志规范

```java
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    /**
     * 根据ID查询用户
     */
    public User getUserById(Long userId) {
        log.info("开始查询用户，userId={}", userId);

        try {
            User user = userMapper.selectById(userId);
            if (user == null) {
                log.warn("用户不存在，userId={}", userId);
                return null;
            }

            log.info("查询用户成功，userId={}, username={}", userId, user.getUsername());
            return user;

        } catch (Exception e) {
            log.error("查询用户失败，userId={}", userId, e);
            throw new BusinessException("查询用户失败");
        }
    }
}
```

**日志级别使用**：
- **ERROR**: 错误日志，系统异常
- **WARN**: 警告日志，业务异常
- **INFO**: 信息日志，关键操作
- **DEBUG**: 调试日志，开发使用

---

## 三、项目结构规范

### 3.1 模块结构
```
apigateway-platform/
├── common-module/          # 公共模块
├── datasource-module/      # 数据源模块
├── user-module/            # 用户模块
├── encrypt-module/         # 加密模块
├── monitor-module/         # 监控模块
├── api-generator-module/   # API生成模块
├── api-gateway-war/        # 网关应用
├── api-manager-war/        # 管理后台
└── frontend-project/       # 前端项目
```

### 3.2 单个模块结构
```
module-name/
├── src/main/
│   ├── java/com/apigateway/module/
│   │   ├── config/         # 配置类
│   │   ├── controller/     # 控制层
│   │   ├── service/        # 服务层
│   │   │   ├── impl/       # 服务实现
│   │   ├── mapper/         # 数据访问层
│   │   ├── entity/         # 实体类
│   │   ├── dto/            # 数据传输对象
│   │   ├── vo/             # 视图对象
│   │   ├── aspect/         # 切面
│   │   ├── enums/          # 枚举
│   │   ├── exception/      # 异常
│   │   └── util/           # 工具类
│   └── resources/
│       ├── mapper/         # MyBatis映射文件
│       ├── config/         # 配置文件
│       └── logback.xml     # 日志配置
└── pom.xml
```

---

## 四、开发流程规范

### 4.1 功能开发流程
1. 先设计数据库表结构
2. 创建实体类（Entity）
3. 创建Mapper接口
4. 创建Service接口和实现
5. 创建Controller
6. 编写单元测试
7. 提交代码

### 4.2 Git提交规范
```
<type>(<scope>): <subject>

<body>
```

**Type类型**：
- `feat`: 新功能
- `fix`: 修复bug
- `docs`: 文档更新
- `style`: 代码格式
- `refactor`: 重构
- `test`: 测试
- `chore`: 构建/工具

**示例**：
```
feat(user): 实现用户登录功能

- 实现登录接口
- 集成Sa-Token认证
- 添加登录日志记录

Closes #1
```

---

## 五、注意事项

### 5.1 国产化适配
- 代码必须兼容Dragonwell JDK
- 测试达梦数据库SQL兼容性
- 验证国密算法正确性

### 5.2 性能优化
- 使用Redis缓存热点数据
- 避免N+1查询
- 合理配置连接池
- 使用分页查询

### 5.3 安全考虑
- 密码使用BCrypt加密
- 敏感数据使用SM4加密
- JWT Token有效期控制
- API接口限流保护
- SQL注入防护（使用MyBatis Plus）
- XSS防护（前端过滤）

### 5.4 代码质量
- 遵循单一职责原则
- 避免重复代码
- 合理使用设计模式
- 编写单元测试
- 代码注释完整

---

## 六、常见问题

### 6.1 MyBatis Plus字段映射问题
```java
// 数据库字段使用下划线命名：user_name
// 实体类字段使用驼峰命名：userName
// MyBatis Plus会自动转换

@TableField("user_name")
private String userName;
```

### 6.2 动态数据源切换问题
```java
// 在事务中切换数据源可能失效
// 解决方案：在事务外切换，或使用@DataSource注解

@Transactional
@DataSource(DataSourceType.DM)
public void transactionMethod() {
    // 该方法内使用达梦数据源
}
```

### 6.3 War包部署问题
```java
// 启动类继承SpringBootServletInitializer
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

---

**记住：严格按照以上规范开发，确保代码质量和项目可维护性！**
