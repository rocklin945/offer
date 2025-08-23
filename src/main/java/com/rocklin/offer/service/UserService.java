package com.rocklin.offer.service;


import com.rocklin.offer.common.response.PageResponse;
import com.rocklin.offer.model.dto.request.UserLoginRequest;
import com.rocklin.offer.model.dto.request.UserPageQueryRequest;
import com.rocklin.offer.model.dto.request.UserRegisterRequest;
import com.rocklin.offer.model.dto.request.UserUpdateRequest;
import com.rocklin.offer.model.dto.response.UserLoginResponse;
import com.rocklin.offer.model.entity.User;

public interface UserService {
    Long register(UserRegisterRequest req);

    UserLoginResponse login(UserLoginRequest req);

    /**
     * 根据用户ID获取当前用户
     */
    UserLoginResponse getCurrentUser(Long userId);

    /**
     * 获取当前登录用户（从JWT中获取用户ID）
     */
    UserLoginResponse getCurrentUser();

    void logout(Long userId);

    User getUserById(Long id);

    void deleteUser(Long id);

    void updateUser(UserUpdateRequest req);

    /**
     * 分页获取用户列表（带过滤条件）
     */
    PageResponse<UserLoginResponse> listUserByPageWithFilter(UserPageQueryRequest request);

    String getUserIdFromRequest();
}
