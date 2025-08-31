package com.rocklin.offer.service;

import com.rocklin.offer.model.dto.request.ResumeAddRequest;
import com.rocklin.offer.model.dto.request.ResumeUpdateRequest;
import com.rocklin.offer.model.entity.Resume;

/**
 * @ClassName ResumeService
 * @Description 简历服务接口
 * @Author: rocklin
 * @Date 2025/8/31
 * @Version 1.0
 */
public interface ResumeService {

    /**
     * 添加简历
     */
    Long addResume(ResumeAddRequest request, Long userId);

    /**
     * 根据ID获取简历
     */
    Resume getResumeById(Long id);

    /**
     * 根据用户ID获取简历
     */
    Resume getResumeByUserId(Long userId);

    /**
     * 更新简历
     */
    boolean updateResume(ResumeUpdateRequest request, Long userId);

    /**
     * 删除简历
     */
    boolean deleteResume(Long id, Long userId);

    /**
     * 根据用户ID删除简历
     */
    boolean deleteResumeByUserId(Long userId);
}