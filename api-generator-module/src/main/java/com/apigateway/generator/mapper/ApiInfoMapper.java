package com.apigateway.generator.mapper;

import com.apigateway.generator.entity.ApiInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * API信息Mapper
 *
 * @author apigateway
 * @since 1.0.0
 */
@Mapper
public interface ApiInfoMapper extends BaseMapper<ApiInfo> {

    /**
     * 根据API路径和方法查询API
     */
    @Select("SELECT * FROM api_info WHERE api_path = #{apiPath} AND api_method = #{apiMethod} AND status = 1")
    ApiInfo selectByPathAndMethod(@Param("apiPath") String apiPath, @Param("apiMethod") String apiMethod);

    /**
     * 执行查询SQL
     */
    @Select("${sql}")
    List<Map<String, Object>> executeQuery(@Param("sql") String sql);
}
