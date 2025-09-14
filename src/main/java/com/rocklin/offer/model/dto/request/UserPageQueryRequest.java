package com.rocklin.offer.model.dto.request;

import com.rocklin.offer.common.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName UserPageQueryRequest
 * @Description 用户分页查询请求
 * @Author: rocklin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：0:管理员 1:普通用户
     */
    private Integer userRole;

    /**
     * 邀请码
     */
    private String inviterCode;
}
