package com.rocklin.offer.controller;

import com.rocklin.offer.common.annotation.AuthCheck;
import com.rocklin.offer.common.annotation.SlidingWindowRateLimit;
import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.enums.UserRoleEnum;
import com.rocklin.offer.common.exception.Assert;
import com.rocklin.offer.common.response.BaseResponse;
import com.rocklin.offer.common.response.PageResponse;
import com.rocklin.offer.model.dto.request.UserLoginRequest;
import com.rocklin.offer.model.dto.request.UserPageQueryRequest;
import com.rocklin.offer.model.dto.request.UserRegisterRequest;
import com.rocklin.offer.model.dto.request.UserUpdateRequest;
import com.rocklin.offer.model.dto.response.UserLoginResponse;
import com.rocklin.offer.model.entity.User;
import com.rocklin.offer.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.rocklin.offer.common.constants.Constants.USER_ID;


/**
 * @ClassName UserController
 * @Description 用户相关模块
 * @Author: rocklin
 * @Date 2025/8/3 19:08
 * @Version 1.0
 */
@Tag(name = "用户", description = "用户相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /**
     * 注册
     */
    @Operation(summary = "注册", description = "注册用户")
    @PostMapping("/register")
    @SlidingWindowRateLimit()
    public BaseResponse<Long> register(@RequestBody @Validated UserRegisterRequest req, HttpServletRequest  httpServletRequest) {
        Assert.notNull(req, ErrorCode.PARAMS_ERROR, "参数为空");
        Assert.isTrue(req.getUserPassword().equals(req.getCheckPassword()),
                ErrorCode.PARAMS_ERROR, "密码和校验密码不一致");
        userService.register(req, httpServletRequest);
        return BaseResponse.success();
    }

    /**
     * 登录
     */
    @Operation(summary = "登录", description = "用户登录")
    @PostMapping("/login")
    @SlidingWindowRateLimit(windowInSeconds = 10, maxCount = 3)
    public BaseResponse<UserLoginResponse> login(@RequestBody @Validated UserLoginRequest req, HttpServletRequest  httpServletRequest) {
        Assert.notNull(req, ErrorCode.PARAMS_ERROR, "参数为空");
        UserLoginResponse userLoginResponse = userService.login(req, httpServletRequest);
        return BaseResponse.success(userLoginResponse);
    }

    /**
     * 获取当前登录用户
     */
    @Operation(summary = "获取当前登录用户", description = "获取当前登录用户")
    @PostMapping("/getCurrentUser")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 3)
    public BaseResponse<UserLoginResponse> getCurrentUser(@RequestAttribute(USER_ID) String userId) {
        Assert.notNull(userId, ErrorCode.NOT_LOGIN_ERROR, "用户未登录");
        UserLoginResponse currentUser = userService.getCurrentUser(Long.valueOf(userId));
        return BaseResponse.success(currentUser);
    }

    /**
     * 登出
     */
    @Operation(summary = "登出", description = "用户登出")
    @PostMapping("/logout")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 3)
    public BaseResponse<Void> logout(@RequestAttribute(USER_ID) String userId) {
        Assert.notNull(userId, ErrorCode.PARAMS_ERROR, "用户ID不能为空");
        userService.logout(Long.valueOf(userId));
        return BaseResponse.success();
    }

    /**
     * 管理员接口
     * 根据 id 获取用户
     */
    @Operation(summary = "根据 id 获取用户", description = "管理员接口，根据 id 获取用户")
    @GetMapping("/getById")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 3)
    public BaseResponse<User> getById(@RequestParam Long id) {
        Assert.notNull(id, ErrorCode.PARAMS_ERROR, "用户ID不能为空");
        return BaseResponse.success(userService.getUserById(id));
    }

    /**
     * 管理员接口
     * 删除用户
     */
    @Operation(summary = "删除用户", description = "管理员接口，删除用户")
    @PostMapping("/delete")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 3)
    public BaseResponse<Boolean> deleteUser(@RequestParam Long id) {
        Assert.notNull(id, ErrorCode.PARAMS_ERROR, "用户ID不能为空");
        User userById = userService.getUserById(id);
        Assert.notNull(userById, ErrorCode.OPERATION_ERROR, "用户不存在");
        userService.deleteUser(id);
        return BaseResponse.success();
    }

    /**
     * 管理员接口
     * 更新用户
     */
    @Operation(summary = "更新用户", description = "管理员接口，更新用户")
    @PostMapping("/update")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 3)
    public BaseResponse<Boolean> updateUser(@RequestBody @Validated UserUpdateRequest req) {
        Assert.notNull(req, ErrorCode.PARAMS_ERROR, "用户更新数据不能为空");
        User userById = userService.getUserById(req.getId());
        Assert.notNull(userById, ErrorCode.OPERATION_ERROR, "用户不存在");
        userService.updateUser(req);
        return BaseResponse.success();
    }

    /**
     * 管理员接口
     * 分页获取用户封装列表
     */
    @Operation(summary = "分页获取用户列表", description = "管理员接口，分页获取用户列表")
    @PostMapping("/list/page")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @SlidingWindowRateLimit(windowInSeconds = 1, maxCount = 3)
    public BaseResponse<PageResponse<UserLoginResponse>> listUserByPage(@RequestBody @Validated UserPageQueryRequest req) {
        return BaseResponse.success(userService.listUserByPageWithFilter(req));
    }
}
