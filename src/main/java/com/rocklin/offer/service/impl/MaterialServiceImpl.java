package com.rocklin.offer.service.impl;

import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.exception.Assert;
import com.rocklin.offer.common.response.PageResponse;
import com.rocklin.offer.mapper.MaterialMapper;
import com.rocklin.offer.model.entity.Material;
import com.rocklin.offer.model.dto.request.MaterialPageQueryRequest;
import com.rocklin.offer.service.MaterialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

import static com.rocklin.offer.common.constants.Constants.USER_DIR;

@Service
@Slf4j
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialMapper materialMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public Material getMaterialById(Long id) {
        return materialMapper.selectById(id);
    }

    @Override
    public PageResponse<Material> listMaterialByPageWithFilter(MaterialPageQueryRequest request) {
        long total = materialMapper.countByCondition(request);
        int offset = (request.getPageNum() - 1) * request.getPageSize();
        List<Material> records = materialMapper.selectByCondition(request, offset);
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

    @Override
    public boolean deleteMaterialById(Long id) {
        Material material = materialMapper.selectById(id);
        Assert.notNull(material, ErrorCode.NOT_FOUND, "未找到该资料");
        String fileUuid = material.getFileUuid();
        // 先删数据库
        int result = materialMapper.deleteById(id);

        if (result > 0) {
            // 再删文件夹
            File baseDir = new File(uploadDir);
            if (!baseDir.isAbsolute()) {
                baseDir = new File(System.getProperty(USER_DIR), uploadDir);
            }

            File bookDir = new File(baseDir, fileUuid);
            deleteDirectoryRecursively(bookDir);
            log.info("删除文件成功：{}", bookDir.getAbsolutePath());
            return true;
        }
        return false;
    }

    /**
     * 递归删除文件夹
     */
    private void deleteDirectoryRecursively(File dir) {
        if (dir == null || !dir.exists()) {
            return;
        }
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectoryRecursively(file);
                }
            }
        }
        dir.delete(); // 删除文件或空目录
    }
}