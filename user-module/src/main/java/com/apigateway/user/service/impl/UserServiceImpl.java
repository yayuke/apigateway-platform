package com.apigateway.user.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import com.apigateway.common.exception.BusinessException;
import com.apigateway.user.entity.User;
import com.apigateway.user.mapper.UserMapper;
import com.apigateway.user.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 用户服务实现类
 *
 * @author apigateway
 * @since 1.0.0
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User getUserByUsername(String username) {
        if (!StringUtils.hasText(username)) {
            throw new BusinessException("用户名不能为空");
        }

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return this.getOne(wrapper);
    }

    @Override
    public IPage<User> pageUsers(Integer pageNum, Integer pageSize, Integer status) {
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        if (status != null) {
            wrapper.eq(User::getStatus, status);
        }

        wrapper.orderByDesc(User::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createUser(User user) {
        // 验证用户名
        if (!StringUtils.hasText(user.getUsername())) {
            throw new BusinessException("用户名不能为空");
        }

        // 检查用户名是否存在
        User existingUser = getUserByUsername(user.getUsername());
        if (existingUser != null) {
            throw new BusinessException("用户名已存在");
        }

        // 加密密码
        if (StringUtils.hasText(user.getPassword())) {
            user.setPassword(BCrypt.hashpw(user.getPassword()));
        }

        // 设置默认状态
        if (user.getStatus() == null) {
            user.setStatus(1);
        }

        return this.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(User user) {
        if (user.getId() == null) {
            throw new BusinessException("用户ID不能为空");
        }

        User existingUser = this.getById(user.getId());
        if (existingUser == null) {
            throw new BusinessException("用户不存在");
        }

        // 如果修改了密码，需要加密
        if (StringUtils.hasText(user.getPassword()) &&
            !user.getPassword().equals(existingUser.getPassword())) {
            user.setPassword(BCrypt.hashpw(user.getPassword()));
        }

        return this.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(Long userId) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空");
        }

        User user = this.getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        return this.removeById(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserStatus(Long userId, Integer status) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空");
        }
        if (status == null) {
            throw new BusinessException("状态不能为空");
        }

        User user = this.getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        user.setStatus(status);
        return this.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetPassword(Long userId, String newPassword) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空");
        }
        if (!StringUtils.hasText(newPassword)) {
            throw new BusinessException("新密码不能为空");
        }

        User user = this.getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        user.setPassword(BCrypt.hashpw(newPassword));
        return this.updateById(user);
    }
}
