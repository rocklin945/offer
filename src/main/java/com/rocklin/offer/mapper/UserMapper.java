package com.rocklin.offer.mapper;

import com.rocklin.offer.model.dto.request.UserPageQueryRequest;
import com.rocklin.offer.model.dto.request.UserUpdateInfoRequest;
import com.rocklin.offer.model.dto.request.UserUpdateRequest;
import com.rocklin.offer.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName UserMapper
 * @Description 用户数据库操作
 * @Author: rocklin
 * @Date 2025/8/3 19:21
 * @Version 1.0
 */
@Mapper
public interface UserMapper {
    User query(@Param("userAccount") String userAccount);

    Long insert(User user);

    User queryByPassword(User user);

    User selectById(@Param("id") Long id);

    Long deleteById(Long id);

    Long updateById(UserUpdateRequest req);

    /**
     * 分页查询用户列表（带过滤条件）
     */
    List<User> selectListWithLimit(@Param("req") UserPageQueryRequest req,
            @Param("offset") int offset,
            @Param("limit") int limit);

    /**
     * 查询总记录数（带过滤条件）
     */
    long countTotal(@Param("req") UserPageQueryRequest req);

    /**
     * 根据邀请码查询用户
     */
    User selectByInviteCode(@Param("inviteCode") String inviteCode);

    Long updateUserInfo(UserUpdateInfoRequest req);
}
