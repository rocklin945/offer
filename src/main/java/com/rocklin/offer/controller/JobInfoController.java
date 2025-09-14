package com.rocklin.offer.controller;

import com.rocklin.offer.common.annotation.AuthCheck;
import com.rocklin.offer.common.annotation.MemberCheck;
import com.rocklin.offer.common.annotation.SlidingWindowRateLimit;
import com.rocklin.offer.common.enums.UserRoleEnum;
import com.rocklin.offer.common.request.DeleteRequest;
import com.rocklin.offer.common.response.BaseResponse;
import com.rocklin.offer.common.response.PageResponse;
import com.rocklin.offer.model.dto.request.JobInfoAddRequest;
import com.rocklin.offer.model.dto.request.JobInfoQueryRequest;
import com.rocklin.offer.model.dto.request.JobInfoUpdateRequest;
import com.rocklin.offer.model.entity.JobInfo;
import com.rocklin.offer.service.JobInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName JobInfoController
 * @Description 招聘信息接口
 * @Author: rocklin
 * @Date 2025/8/19 20:50
 * @Version 1.0
 */
@Tag(name = "招聘信息", description = "招聘信息相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/jobInfo")
public class JobInfoController {

    private final JobInfoService jobInfoService;

    @Operation(summary = "添加招聘信息", description = "添加招聘信息")
    @PostMapping("/add")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 3)
    public BaseResponse<Boolean> addJobInfo(@RequestBody @Valid JobInfoAddRequest addRequest) {
        JobInfo jobInfo = new JobInfo();
        BeanUtils.copyProperties(addRequest, jobInfo);
        boolean result = jobInfoService.addJobInfo(jobInfo);
        return BaseResponse.success(result);
    }

    @Operation(summary = "删除招聘信息", description = "根据ID删除招聘信息")
    @PostMapping("/delete")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 3)
    public BaseResponse<Boolean> deleteJobInfo(@RequestBody @Valid DeleteRequest deleteRequest) {
        boolean result = jobInfoService.deleteJobInfo(deleteRequest.getId());
        return BaseResponse.success(result);
    }

    @Operation(summary = "更新招聘信息", description = "更新招聘信息")
    @PostMapping("/update")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 3)
    public BaseResponse<Boolean> updateJobInfo(@RequestBody @Valid JobInfoUpdateRequest updateRequest) {
        JobInfo jobInfo = new JobInfo();
        BeanUtils.copyProperties(updateRequest, jobInfo);
        boolean result = jobInfoService.updateJobInfo(jobInfo);
        return BaseResponse.success(result);
    }

    @Operation(summary = "根据ID获取招聘信息", description = "根据ID获取招聘信息详情")
    @GetMapping("/get/{id}")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 3)
    public BaseResponse<JobInfo> getJobInfoById(@PathVariable Long id) {
        JobInfo jobInfo = jobInfoService.getJobInfoById(id);
        return BaseResponse.success(jobInfo);
    }

    @Operation(summary = "获取招聘信息列表", description = "分页查询招聘信息列表")
    @PostMapping("/list")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 10)
    @MemberCheck(maxPage = 2, maxPageSize = 10, message = "普通用户免费查看前2页，成为会员查看所有")
    public BaseResponse<PageResponse<JobInfo>> getJobInfoList(@RequestBody JobInfoQueryRequest queryRequest) {
        List<JobInfo> jobInfoList = jobInfoService.getJobInfoList(queryRequest);
        int total = jobInfoService.getJobInfoCount(queryRequest);
        
        PageResponse<JobInfo> pageResponse = new PageResponse<>(
                jobInfoList, 
                total, 
                queryRequest.getPageNum(), 
                queryRequest.getPageSize()
        );
        
        return BaseResponse.success(pageResponse);
    }
    @Operation(summary = "批量导入招聘信息", description = "通过Excel文件批量导入招聘信息，根据公司名+招聘类型+招聘对象去重")
    @PostMapping("/batch-import")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @SlidingWindowRateLimit(windowInSeconds = 30, maxCount = 2)
    public BaseResponse<String> batchImportJobInfo(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return BaseResponse.error(40000, "请选择要上传的Excel文件");
        }

        // 检查文件类型
        String fileName = file.getOriginalFilename();
        if (fileName == null || (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls"))) {
            return BaseResponse.error(40000, "仅支持Excel格式文件（.xlsx或.xls）");
        }

        try {
            String result = jobInfoService.batchImportJobInfo(file);
            return BaseResponse.success(result);
        } catch (Exception e) {
            return BaseResponse.error(50000, "导入失败：" + e.getMessage());
        }
    }
}
