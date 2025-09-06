package com.rocklin.offer.service.impl;

import com.rocklin.offer.common.response.PageResponse;
import com.rocklin.offer.mapper.MaterialMapper;
import com.rocklin.offer.model.entity.Material;
import com.rocklin.offer.model.dto.request.MaterialPageQueryRequest;
import com.rocklin.offer.service.MaterialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialMapper materialMapper;

    @Override
    public Material getMaterialById(Long id) {
        return materialMapper.selectById(id);
    }

    @Override
    public PageResponse<Material> listMaterialByPageWithFilter(MaterialPageQueryRequest request) {
        long total = materialMapper.countByCondition(request);
        int offset = (request.getPageNum() - 1) * request.getPageSize();
        List<Material> records = materialMapper.selectByCondition(request,offset);
        return new PageResponse<>(records, total, request.getPageSize(), request.getPageNum());
    }

    @Override
    public List<String> getAllCategories() {
        return materialMapper.selectAllCategories();
    }

    @Override
    public void incrementViewCount(String uuid) {
        materialMapper.incrementViewCount(uuid);
    }

    @Override
    public void addMaterial(String fileUuid, String name, String category, Long fileSize, Integer totalPages) {
        Material material = new Material();
        material.setFileName(name);
        material.setCategory(category);
        material.setFileUuid(fileUuid);
        material.setFileSize(fileSize);
        material.setTotalPages(totalPages);
        material.setViewCount(0);
        materialMapper.insert(material);
    }
}