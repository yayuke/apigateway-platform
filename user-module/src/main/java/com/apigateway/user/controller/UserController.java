package com.apigateway.user.controller;

import com.apigateway.common.core.PageResult;
import com.apigateway.common.core.Result;
import com.apigateway.user.entity.User;
import com.apigateway.user.service.IUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制器
 *
 * @author apigateway
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 根据ID查询用户
     */
    @GetMapping("/{id}")
    @ApiOperation("根据ID查询用户")
    public Result<User> getUserById(@ApiParam("用户ID") @PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        return Result.success(user);
    }

    /**
     * 分页查询用户列表
     */
    @GetMapping("/page")
    @ApiOperation("分页查询用户列表")
    public Result<PageResult<User>> pageUsers(
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @ApiParam("状态") @RequestParam(required = false) Integer status) {

        IPage<User> page = userService.pageUsers(pageNum, pageSize, status);
        PageResult<User> result = PageResult.build(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                page.getRecords()
        );
        return Result.success(result);
    }

    /**
     * 创建用户
     */
    @PostMapping
    @ApiOperation("创建用户")
    public Result<Void> createUser(@RequestBody User user) {
        boolean success = userService.createUser(user);
        return success ? Result.success("创建成功") : Result.error("创建失败");
    }

    /**
     * 更新用户
     */
    @PutMapping
    @ApiOperation("更新用户")
    public Result<Void> updateUser(@RequestBody User user) {
        boolean success = userService.updateUser(user);
        return success ? Result.success("更新成功") : Result.error("更新失败");
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public Result<Void> deleteUser(@ApiParam("用户ID") @PathVariable Long id) {
        boolean success = userService.deleteUser(id);
        return success ? Result.success("删除成功") : Result.error("删除失败");
    }

    /**
     * 修改用户状态
     */
    @PutMapping("/{id}/status")
    @ApiOperation("修改用户状态")
    public Result<Void> updateUserStatus(
            @ApiParam("用户ID") @PathVariable Long id,
            @ApiParam("状态") @RequestParam Integer status) {
        boolean success = userService.updateUserStatus(id, status);
        return success ? Result.success("状态修改成功") : Result.error("状态修改失败");
    }

    /**
     * 重置用户密码
     */
    @PutMapping("/{id}/password")
    @ApiOperation("重置用户密码")
    public Result<Void> resetPassword(
            @ApiParam("用户ID") @PathVariable Long id,
            @ApiParam("新密码") @RequestParam String newPassword) {
        boolean success = userService.resetPassword(id, newPassword);
        return success ? Result.success("密码重置成功") : Result.error("密码重置失败");
    }
}
