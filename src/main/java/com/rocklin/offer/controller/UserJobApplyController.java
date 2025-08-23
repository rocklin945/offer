package com.rocklin.offer.controller;

import cn.hutool.core.util.StrUtil;
import com.rocklin.offer.common.annotation.AuthCheck;
import com.rocklin.offer.common.annotation.SlidingWindowRateLimit;
import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.enums.UserRoleEnum;
import com.rocklin.offer.common.exception.Assert;
import com.rocklin.offer.common.request.DeleteRequest;
import com.rocklin.offer.common.response.BaseResponse;
import com.rocklin.offer.common.response.PageResponse;
import com.rocklin.offer.model.dto.UserJobApplyDTO;
import com.rocklin.offer.model.dto.request.UserJobApplyAddRequest;
import com.rocklin.offer.model.dto.request.UserJobApplyQueryRequest;
import com.rocklin.offer.model.dto.request.UserJobApplyUpdateRequest;
import com.rocklin.offer.model.entity.UserJobApply;
import com.rocklin.offer.service.UserJobApplyService;
import com.rocklin.offer.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户投递记录
 */
@Tag(name = "用户投递记录", description = "用户投递记录相关接口")
@RestController
@RequestMapping("/user-job-apply")
@RequiredArgsConstructor
public class UserJobApplyController {

    private final UserService userService;
    private final UserJobApplyService userJobApplyService;

    /**
     * 添加投递记录
     */
    @PostMapping("/add")
    @Operation(summary = "添加投递记录", description = "用户投递某个职位")
    @SlidingWindowRateLimit(windowInSeconds = 10, maxCount = 3)
    public BaseResponse<Long> addUserJobApply(@RequestBody @Validated UserJobApplyAddRequest req) {
        Assert.notNull(req, ErrorCode.PARAMS_ERROR,"参数为空");
        String strUserId = userService.getUserIdFromRequest();
        Assert.isTrue(!StrUtil.isBlank(strUserId), ErrorCode.PARAMS_ERROR, "登录信息失效");
        Long userId = Long.valueOf(strUserId);
        Long id = userJobApplyService.addUserJobApply(req, userId);
        return BaseResponse.success(id);
    }

    /**
     * 删除投递记录
     */
    @PostMapping("/delete")
    @Operation(summary = "删除投递记录", description = "删除用户的投递记录")
    @SlidingWindowRateLimit(windowInSeconds = 10, maxCount = 3)
    public BaseResponse<Boolean> deleteUserJobApply(@RequestBody @Validated DeleteRequest req) {
        Assert.notNull(req, ErrorCode.PARAMS_ERROR,"参数为空");
        String strUserId = userService.getUserIdFromRequest();
        Assert.isTrue(!StrUtil.isBlank(strUserId), ErrorCode.PARAMS_ERROR, "登录信息失效");
        Long userId = Long.valueOf(strUserId);
        boolean result = userJobApplyService.deleteUserJobApply(req.getId(), userId);
        return BaseResponse.success(result);
    }

    /**
     * 更新投递记录
     */
    @PostMapping("/update")
    @Operation(summary = "更新投递记录", description = "更新投递记录的状态等信息")
    @SlidingWindowRateLimit(windowInSeconds = 10, maxCount = 3)
    public BaseResponse<Boolean> updateUserJobApply(@RequestBody @Validated UserJobApplyUpdateRequest req) {
        String strUserId = userService.getUserIdFromRequest();
        Assert.isTrue(!StrUtil.isBlank(strUserId), ErrorCode.PARAMS_ERROR, "登录信息失效");
        Long userId = Long.valueOf(strUserId);
        boolean result = userJobApplyService.updateUserJobApply(req, userId);
        return BaseResponse.success(result);
    }

    /**
     * 根据ID获取投递记录
     */
    @GetMapping("/get/{id}")
    @Operation(summary = "获取投递记录", description = "根据ID获取投递记录详情")
    @SlidingWindowRateLimit(windowInSeconds = 10, maxCount = 3)
    public BaseResponse<UserJobApply> getUserJobApplyById(@PathVariable Long id) {
        Assert.notNull(id, ErrorCode.PARAMS_ERROR, "ID不能为空");
        UserJobApply userJobApply = userJobApplyService.getUserJobApplyById(id);
        return BaseResponse.success(userJobApply);
    }

    /**
     * 检查是否已投递
     */
    @GetMapping("/check")
    @Operation(summary = "检查是否已投递", description = "检查用户是否已投递某个职位")
    @SlidingWindowRateLimit(windowInSeconds = 10, maxCount = 3)
    public BaseResponse<Boolean> hasApplied(@RequestParam Long jobId) {
        String strUserId = userService.getUserIdFromRequest();
        Assert.isTrue(!StrUtil.isBlank(strUserId), ErrorCode.PARAMS_ERROR, "登录信息失效");
        Long userId = Long.valueOf(strUserId);
        boolean hasApplied = userJobApplyService.hasApplied(userId, jobId);
        return BaseResponse.success(hasApplied);
    }

    /**
     * 分页查询投递记录
     */
    @PostMapping("/page")
    @Operation(summary = "分页查询投递记录", description = "分页查询用户投递记录")
    @SlidingWindowRateLimit(windowInSeconds = 1, maxCount = 3)
    public BaseResponse<PageResponse<UserJobApplyDTO>> getUserJobApplyPage(@RequestBody @Validated UserJobApplyQueryRequest req) {
        String strUserId = userService.getUserIdFromRequest();
        Assert.isTrue(!StrUtil.isBlank(strUserId), ErrorCode.PARAMS_ERROR, "登录信息失效");
        Long userId = Long.valueOf(strUserId);
        // 普通用户只能查看自己的投递记录
        req.setUserId(userId);
        PageResponse<UserJobApplyDTO> pageResponse = userJobApplyService.getUserJobApplyPage(req);
        return BaseResponse.success(pageResponse);
    }

    /**
     * 管理员分页查询所有投递记录
     */
    @PostMapping("/admin/page")
    @Operation(summary = "管理员分页查询投递记录", description = "管理员分页查询所有用户的投递记录")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @SlidingWindowRateLimit(windowInSeconds = 1, maxCount = 3)
    public BaseResponse<PageResponse<UserJobApplyDTO>> getAdminUserJobApplyPage(@RequestBody @Validated UserJobApplyQueryRequest req) {
        PageResponse<UserJobApplyDTO> pageResponse = userJobApplyService.getUserJobApplyPage(req);
        return BaseResponse.success(pageResponse);
    }

    /**
     * 管理员更新投递记录
     */
    @PostMapping("/admin/update")
    @Operation(summary = "管理员更新投递记录", description = "管理员更新任意投递记录的状态")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @SlidingWindowRateLimit(windowInSeconds = 10, maxCount = 3)
    public BaseResponse<Boolean> adminUpdateUserJobApply(@RequestBody @Validated UserJobApplyUpdateRequest req) {
        // 管理员更新时不需要检查用户权限，直接更新
        boolean result = userJobApplyService.updateUserJobApply(req, null);
        return BaseResponse.success(result);
    }

    /**
     * 管理员删除投递记录
     */
    @PostMapping("/admin/delete")
    @Operation(summary = "管理员删除投递记录", description = "管理员删除任意投递记录")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @SlidingWindowRateLimit(windowInSeconds = 10, maxCount = 3)
    public BaseResponse<Boolean> adminDeleteUserJobApply(@RequestBody @Validated DeleteRequest req) {
        // 管理员删除时不需要检查用户权限，直接删除
        boolean result = userJobApplyService.deleteUserJobApply(req.getId(), null);
        return BaseResponse.success(result);
    }
}