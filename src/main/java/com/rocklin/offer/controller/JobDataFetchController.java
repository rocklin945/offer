package com.rocklin.offer.controller;

import com.rocklin.offer.common.response.BaseResponse;
import com.rocklin.offer.service.JobDataFetchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *招数据获取控制器
 * 用于手动触发数据获取和导入任务
 * 
 * @author rocklin
 * @date 2026-03-01
 */
@Tag(name = "招聘数据获取", description = "手动触发数据获取和导入任务")
@RestController
@RequestMapping("/job-data")
public class JobDataFetchController {

    @Autowired
    private JobDataFetchService jobDataFetchService;

    /**
     *手动触发从目标网站获取数据并导入数据库
     * 
     * @return导入结果信息
     */
    @Operation(summary = "手动获取网站数据", description = "手动触发从目标网站获取数据并导入数据库")
    @PostMapping("/fetch-and-import")
    public BaseResponse<String> fetchAndImport() {
        try {
            String result = jobDataFetchService.fetchDataAndImport();
            return BaseResponse.success(result);
        } catch (Exception e) {
            return BaseResponse.error(500, "任务执行失败：" + e.getMessage());
        }
    }
}