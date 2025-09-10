package com.rocklin.offer.service.impl;

import com.rocklin.offer.common.convert.ResumeConverter;
import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.exception.Assert;
import com.rocklin.offer.mapper.ResumeMapper;
import com.rocklin.offer.model.dto.request.ResumeAddRequest;
import com.rocklin.offer.model.dto.request.ResumeUpdateRequest;
import com.rocklin.offer.model.entity.Resume;
import com.rocklin.offer.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @ClassName ResumeServiceImpl
 * @Description 简历服务实现类
 * @Author: rocklin
 * @Date 2025/8/31
 * @Version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeMapper resumeMapper;

    @Override
    public Long addResume(ResumeAddRequest request, Long userId) {
        // 检查用户是否已有简历
        Resume existingResume = resumeMapper.selectByUserId(userId);
        Assert.isNull(existingResume, ErrorCode.OPERATION_ERROR, "您已有简历，请选择更新操作");

        // 创建简历实体
        Resume resume = ResumeConverter.requestToEntity(request);
        resume.setUserId(userId);

        Long result = resumeMapper.insert(resume);
        Assert.isTrue(result > 0, ErrorCode.OPERATION_ERROR, "数据库异常，简历添加失败");
        return resume.getId();
    }

    @Override
    public Resume getResumeById(Long id) {
        Resume resume = resumeMapper.selectById(id);
        Assert.notNull(resume, ErrorCode.PARAMS_ERROR, "简历不存在");
        return resume;
    }

    @Override
    public Resume getResumeByUserId(Long userId) {
        return resumeMapper.selectByUserId(userId);
    }

    @Override
    public boolean updateResume(ResumeUpdateRequest request, Long userId) {
        // 检查简历是否存在
        Resume existingResume = resumeMapper.selectById(request.getId());
        Assert.notNull(existingResume, ErrorCode.PARAMS_ERROR, "简历不存在");

        // 检查用户权限（用户只能更新自己的简历）
        Assert.isTrue(existingResume.getUserId().equals(userId),
                ErrorCode.UNAUTHORIZED, "无权限修改此简历");

        // 更新简历
        Resume resume = ResumeConverter.requestToEntity(request);

        Long result = resumeMapper.updateById(resume);
        Assert.isTrue(result > 0, ErrorCode.OPERATION_ERROR, "数据库异常，简历更新失败");

        return true;
    }

    @Override
    public boolean deleteResume(Long id, Long userId) {
        // 检查简历是否存在
        Resume existingResume = resumeMapper.selectById(id);
        Assert.notNull(existingResume, ErrorCode.PARAMS_ERROR, "简历不存在");

        // 检查用户权限
        Assert.isTrue(existingResume.getUserId().equals(userId),
                ErrorCode.UNAUTHORIZED, "无权限删除此简历");

        Long result = resumeMapper.deleteById(id);
        Assert.isTrue(result > 0, ErrorCode.OPERATION_ERROR, "数据库异常，简历删除失败");

        return result > 0;
    }

    @Override
    public boolean deleteResumeByUserId(Long userId) {
        Long result = resumeMapper.deleteByUserId(userId);
        // 删除不存在的记录也算成功
        return result > 0;
    }
}