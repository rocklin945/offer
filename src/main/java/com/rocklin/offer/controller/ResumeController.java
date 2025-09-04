package com.rocklin.offer.controller;

import cn.hutool.core.util.StrUtil;
import com.rocklin.offer.common.annotation.SlidingWindowRateLimit;
import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.exception.Assert;
import com.rocklin.offer.common.request.DeleteRequest;
import com.rocklin.offer.common.response.BaseResponse;
import com.rocklin.offer.model.dto.request.ResumeAddRequest;
import com.rocklin.offer.model.dto.request.ResumeUpdateRequest;
import com.rocklin.offer.model.entity.Resume;
import com.rocklin.offer.service.ResumeService;
import com.rocklin.offer.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ResumeController
 * @Description 简历相关接口
 * @Author: rocklin
 * @Date 2025/8/31
 * @Version 1.0
 */
@Tag(name = "个人简历", description = "个人简历相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService resumeService;
    private final UserService userService;

    @Operation(summary = "添加简历", description = "用户添加个人简历")
    @PostMapping("/add")
    @SlidingWindowRateLimit(windowInSeconds = 30, maxCount = 5)
    public BaseResponse<Long> addResume(@RequestBody @Valid ResumeAddRequest request) {
        String strUserId = userService.getUserIdFromRequest();
        Assert.isTrue(!StrUtil.isBlank(strUserId), ErrorCode.NOT_LOGIN_ERROR, "用户未登录");
        Long userId = Long.valueOf(strUserId);

        Long resumeId = resumeService.addResume(request, userId);
        return BaseResponse.success(resumeId);
    }

    @Operation(summary = "获取我的简历", description = "获取当前用户的简历")
    @GetMapping("/my")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 3)
    public BaseResponse<Resume> getMyResume() {
        String strUserId = userService.getUserIdFromRequest();
        Assert.isTrue(!StrUtil.isBlank(strUserId), ErrorCode.NOT_LOGIN_ERROR, "用户未登录");
        Long userId = Long.valueOf(strUserId);

        Resume resume = resumeService.getResumeByUserId(userId);
        return BaseResponse.success(resume);
    }

    @Operation(summary = "根据ID获取简历", description = "根据简历ID获取简历详情")
    @GetMapping("/get/{id}")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 3)
    public BaseResponse<Resume> getResumeById(@PathVariable Long id) {
        String strUserId = userService.getUserIdFromRequest();
        Assert.isTrue(!StrUtil.isBlank(strUserId), ErrorCode.NOT_LOGIN_ERROR, "用户未登录");

        Resume resume = resumeService.getResumeById(id);
        return BaseResponse.success(resume);
    }

    @Operation(summary = "更新简历", description = "更新个人简历信息")
    @PostMapping("/update")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 3)
    public BaseResponse<Boolean> updateResume(@RequestBody @Valid ResumeUpdateRequest request) {
        String strUserId = userService.getUserIdFromRequest();
        Assert.isTrue(!StrUtil.isBlank(strUserId), ErrorCode.NOT_LOGIN_ERROR, "用户未登录");
        Long userId = Long.valueOf(strUserId);

        boolean result = resumeService.updateResume(request, userId);
        return BaseResponse.success(result);
    }

    @Operation(summary = "删除简历", description = "删除个人简历")
    @PostMapping("/delete")
    @SlidingWindowRateLimit(windowInSeconds = 30, maxCount = 3)
    public BaseResponse<Boolean> deleteResume(@RequestBody @Valid DeleteRequest request) {
        String strUserId = userService.getUserIdFromRequest();
        Assert.isTrue(!StrUtil.isBlank(strUserId), ErrorCode.NOT_LOGIN_ERROR, "用户未登录");
        Long userId = Long.valueOf(strUserId);

        boolean result = resumeService.deleteResume(request.getId(), userId);
        return BaseResponse.success(result);
    }
}