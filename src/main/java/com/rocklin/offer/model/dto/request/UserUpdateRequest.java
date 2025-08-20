package com.rocklin.offer.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName UpdateUserRequest
 * @Description 更新用户请求
 * @Author: rocklin
 */
@Data
public class UserUpdateRequest implements Serializable {
    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：0:管理员 1:普通用户
     */
    private Integer userRole;
}
