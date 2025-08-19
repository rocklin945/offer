package com.rocklin.offer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Operation(summary = "获取招聘信息列表", description = "获取招聘信息列表")
    @PostMapping("/getJobInfoList")
    public String getJobInfoList() {

    }
}
