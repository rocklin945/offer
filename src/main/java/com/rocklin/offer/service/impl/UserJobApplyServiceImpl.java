package com.rocklin.offer.service.impl;

import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.exception.Assert;
import com.rocklin.offer.common.exception.BusinessException;
import com.rocklin.offer.common.response.PageResponse;
import com.rocklin.offer.mapper.UserJobApplyMapper;
import com.rocklin.offer.model.dto.UserJobApplyDTO;
import com.rocklin.offer.model.dto.request.UserJobApplyAddRequest;
import com.rocklin.offer.model.dto.request.UserJobApplyQueryRequest;
import com.rocklin.offer.model.dto.request.UserJobApplyUpdateRequest;
import com.rocklin.offer.model.entity.UserJobApply;
import com.rocklin.offer.service.UserJobApplyService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户投递记录服务实现类
 */
@Service
public class UserJobApplyServiceImpl implements UserJobApplyService {

    @Resource
    private UserJobApplyMapper userJobApplyMapper;

    @Override
    public Long addUserJobApply(UserJobApplyAddRequest req, Long userId) {
        // 检查是否已经投递过
        UserJobApply existingApply = userJobApplyMapper.selectByUserIdAndJobId(userId, req.getJobId());
        Assert.isNull(existingApply, ErrorCode.OPERATION_ERROR, "您已经投递过该职位");

        // 创建投递记录
        UserJobApply userJobApply = new UserJobApply();
        userJobApply.setUserId(userId);
        userJobApply.setJobId(req.getJobId());
        userJobApply.setApplicationStatus(req.getApplicationStatus() != null ?
            req.getApplicationStatus() : "已投递");
        userJobApply.setCreateTime(LocalDateTime.now());
        userJobApply.setUpdateTime(LocalDateTime.now());
        userJobApply.setIsDelete(0);

        int result = userJobApplyMapper.insert(userJobApply);
        Assert.isTrue(result > 0, ErrorCode.INTERNAL_SERVER_ERROR, "数据库异常，用户投递失败");
        return userJobApply.getId();
    }

    @Override
    public boolean deleteUserJobApply(Long id, Long userId) {
        // 检查记录是否存在
        UserJobApply userJobApply = userJobApplyMapper.selectById(id);
        Assert.notNull(userJobApply, ErrorCode.NOT_FOUND, "投递记录不存在");
        Assert.isTrue(userJobApply.getUserId().equals(userId) || userId == null,
                ErrorCode.UNAUTHORIZED, "无权限删除该记录");
        int result = userJobApplyMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public boolean updateUserJobApply(UserJobApplyUpdateRequest updateRequest, Long userId) {
        // 检查记录是否存在且属于当前用户
        UserJobApply existingApply = userJobApplyMapper.selectById(updateRequest.getId());
        Assert.notNull(existingApply, ErrorCode.NOT_FOUND, "投递记录不存在");
        Assert.isTrue(existingApply.getUserId().equals(userId) || userId == null, ErrorCode.UNAUTHORIZED, "无权限修改该记录");

        // 更新记录
        UserJobApply userJobApply = new UserJobApply();
        buildUserJobApply(updateRequest, userJobApply);
        userJobApply.setUpdateTime(LocalDateTime.now());

        int result = userJobApplyMapper.updateById(userJobApply);
        return result > 0;
    }

    private void buildUserJobApply(UserJobApplyUpdateRequest updateRequest, UserJobApply userJobApply) {
        userJobApply.setId(updateRequest.getId());
        userJobApply.setApplicationStatus(updateRequest.getApplicationStatus());
        userJobApply.setPersonalNote(updateRequest.getPersonalNote());
    }

    @Override
    public UserJobApply getUserJobApplyById(Long id) {
        return userJobApplyMapper.selectById(id);
    }

    @Override
    public PageResponse<UserJobApplyDTO> getUserJobApplyPage(UserJobApplyQueryRequest queryRequest) {
        // 设置分页参数
        int offset = (queryRequest.getPageNum() - 1) * queryRequest.getPageSize();
        // 查询数据
        long total = userJobApplyMapper.countByQuery(queryRequest);
        List<UserJobApplyDTO> list = userJobApplyMapper.selectList(offset,queryRequest);

        return new PageResponse<>(list, total, queryRequest.getPageNum(), queryRequest.getPageSize());
    }

    @Override
    public boolean hasApplied(Long userId, Long jobId) {
        UserJobApply apply = userJobApplyMapper.selectByUserIdAndJobId(userId, jobId);
        return apply != null;
    }
}