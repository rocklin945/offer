package com.rocklin.offer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.rocklin.offer.common.constants.Constants.SUCCESS;

/**
 * @ClassName HealthCheckController
 * @Description 健康检查
 * @Author: rocklin
 * @Date 2025/8/19 19:25
 * @Version 1.0
 */
@Tag(name = "健康检查", description = "健康检查相关接口")
@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @Operation(summary = "健康检查", description = "检查服务是否正常运行")
    @GetMapping("/check")
    public String healthCheck() {
        return SUCCESS;
    }
}
