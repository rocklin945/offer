package com.rocklin.offer.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @ClassName UserUpdateInfoRequest
 * @Description 修改用户信息请求
 * @Author: rocklin
 * @Date 2025/9/14 22:45
 * @Version 1.0
 */
@Data
public class UserUpdateInfoRequest {
    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 原账号
     */
    @NotBlank(message = "账号不能为空")
    private String oldUserAccount;

    /**
     * 原密码
     */
    @NotBlank(message = "密码不能为空")
    private String oldUserPassword;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 16, message = "密码长度不能小于8位，不能大于16位")
    private String userPassword;

    /**
     * 用户账号
     */
    @NotBlank(message = "账号不能为空")
    @Size(min = 3, max = 12, message = "账号长度不能小于3位，不能大于12位")
    private String userAccount;

    /**
     * 简介
     */
    private String userProfile;
}
