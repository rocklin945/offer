package com.rocklin.offer.service;

import com.rocklin.offer.common.response.PageResponse;
import com.rocklin.offer.model.entity.Material;
import com.rocklin.offer.model.dto.request.MaterialPageQueryRequest;

import java.util.List;

public interface MaterialService {

    /**
     * 根据ID获取资料详情
     */
    Material getMaterialById(Long id);

    /**
     * 分页获取资料列表（带过滤条件）
     */
    PageResponse<Material> listMaterialByPageWithFilter(MaterialPageQueryRequest request);

    /**
     * 获取所有分类
     */
    List<String> getAllCategories();

    /**
     * 增加查看次数
     */
    void incrementViewCount(String uuid);

    /**
     * 添加资料
     */
    void addMaterial(String fileUuid, String name, String category, Long fileSize, Integer totalPages);

    /**
     * 删除资料
     */
    boolean deleteMaterialById(Long id);
}