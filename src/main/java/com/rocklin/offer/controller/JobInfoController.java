package com.rocklin.offer.controller;

import com.rocklin.offer.common.request.DeleteRequest;
import com.rocklin.offer.common.response.BaseResponse;
import com.rocklin.offer.common.response.PageResponse;
import com.rocklin.offer.model.dto.JobInfoAddRequest;
import com.rocklin.offer.model.dto.JobInfoQueryRequest;
import com.rocklin.offer.model.dto.JobInfoUpdateRequest;
import com.rocklin.offer.model.entity.JobInfo;
import com.rocklin.offer.service.JobInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
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
    public BaseResponse<Boolean> addJobInfo(@RequestBody @Valid JobInfoAddRequest addRequest) {
        JobInfo jobInfo = new JobInfo();
        BeanUtils.copyProperties(addRequest, jobInfo);
        boolean result = jobInfoService.addJobInfo(jobInfo);
        return BaseResponse.success(result);
    }

    @Operation(summary = "删除招聘信息", description = "根据ID删除招聘信息")
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteJobInfo(@RequestBody @Valid DeleteRequest deleteRequest) {
        boolean result = jobInfoService.deleteJobInfo(deleteRequest.getId());
        return BaseResponse.success(result);
    }

    @Operation(summary = "更新招聘信息", description = "更新招聘信息")
    @PostMapping("/update")
    public BaseResponse<Boolean> updateJobInfo(@RequestBody @Valid JobInfoUpdateRequest updateRequest) {
        JobInfo jobInfo = new JobInfo();
        BeanUtils.copyProperties(updateRequest, jobInfo);
        boolean result = jobInfoService.updateJobInfo(jobInfo);
        return BaseResponse.success(result);
    }

    @Operation(summary = "根据ID获取招聘信息", description = "根据ID获取招聘信息详情")
    @GetMapping("/get/{id}")
    public BaseResponse<JobInfo> getJobInfoById(@PathVariable Long id) {
        JobInfo jobInfo = jobInfoService.getJobInfoById(id);
        return BaseResponse.success(jobInfo);
    }

    @Operation(summary = "获取招聘信息列表", description = "分页查询招聘信息列表")
    @PostMapping("/list")
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
}
