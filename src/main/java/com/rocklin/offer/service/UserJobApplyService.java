package com.rocklin.offer.service;

import com.rocklin.offer.common.response.PageResponse;
import com.rocklin.offer.model.dto.UserJobApplyDTO;
import com.rocklin.offer.model.dto.request.UserJobApplyAddRequest;
import com.rocklin.offer.model.dto.request.UserJobApplyQueryRequest;
import com.rocklin.offer.model.dto.request.UserJobApplyUpdateRequest;
import com.rocklin.offer.model.entity.UserJobApply;

import java.util.List;

/**
 * 用户投递记录服务接口
 */
public interface UserJobApplyService {

    /**
     * 添加用户投递记录
     */
    Long addUserJobApply(UserJobApplyAddRequest addRequest, Long userId);

    /**
     * 删除用户投递记录
     */
    boolean deleteUserJobApply(Long id, Long userId);

    /**
     * 更新用户投递记录
     */
    boolean updateUserJobApply(UserJobApplyUpdateRequest req, Long userId);

    /**
     * 根据ID获取用户投递记录
     */
    UserJobApply getUserJobApplyById(Long id);

    /**
     * 分页查询用户投递记录
     */
    PageResponse<UserJobApplyDTO> getUserJobApplyPage(UserJobApplyQueryRequest queryRequest);

    /**
     * 检查用户是否已投递某个职位
     */
    boolean hasApplied(Long userId, Long jobId);
}