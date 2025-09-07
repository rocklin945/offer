package com.rocklin.offer.controller;

import com.rocklin.offer.common.annotation.AuthCheck;
import com.rocklin.offer.common.annotation.SlidingWindowRateLimit;
import com.rocklin.offer.common.enums.UserRoleEnum;
import com.rocklin.offer.common.response.BaseResponse;
import com.rocklin.offer.common.response.PageResponse;
import com.rocklin.offer.model.dto.request.MaterialPageQueryRequest;
import com.rocklin.offer.model.entity.Material;
import com.rocklin.offer.service.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "笔试面试资料", description = "笔试面试资料相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/material")
public class MaterialController {

    private final MaterialService materialService;

    @Operation(summary = "根据ID获取资料详情", description = "根据ID获取资料详情")
    @GetMapping("/get/{id}")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 10)
    public BaseResponse<Material> getMaterialById(@PathVariable Long id) {
        Material material = materialService.getMaterialById(id);
        return BaseResponse.success(material);
    }

    @Operation(summary = "获取资料列表", description = "分页查询资料列表")
    @PostMapping("/list")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 10)
    public BaseResponse<PageResponse<Material>> getMaterialList(
            @RequestBody @Valid MaterialPageQueryRequest queryRequest) {
        PageResponse<Material> result = materialService.listMaterialByPageWithFilter(queryRequest);
        return BaseResponse.success(result);
    }

    @Operation(summary = "获取所有分类", description = "获取所有资料分类")
    @GetMapping("/categories")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 10)
    public BaseResponse<List<String>> getAllCategories() {
        List<String> categories = materialService.getAllCategories();
        return BaseResponse.success(categories);
    }

    @Operation(summary = "删除资料", description = "根据ID删除资料（仅管理员）")
    @DeleteMapping("/delete/{id}")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @SlidingWindowRateLimit(windowInSeconds = 10, maxCount = 5)
    public BaseResponse<Boolean> deleteMaterial(@PathVariable Long id) {
        boolean result = materialService.deleteMaterialById(id);
        return BaseResponse.success(result);
    }
}