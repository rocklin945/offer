package com.rocklin.offer.model.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户登录响应
 */
@Data
public class UserLoginResponse implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色
     */
    private Integer userRole;

    /**
     * 会员过期时间
     */
    private LocalDateTime memberExpireTime;

    /**
     * JWT token
     */
    private String token;

    /**
     * 邀请码
     */
    private String inviterCode;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}