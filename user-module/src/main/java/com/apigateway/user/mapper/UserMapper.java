package com.apigateway.user.mapper;

import com.apigateway.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 *
 * @author apigateway
 * @since 1.0.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
