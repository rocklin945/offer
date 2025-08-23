package com.rocklin.offer.mapper;

import com.rocklin.offer.model.dto.UserJobApplyDTO;
import com.rocklin.offer.model.dto.request.UserJobApplyQueryRequest;
import com.rocklin.offer.model.entity.UserJobApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户投递记录Mapper
 */
@Mapper
public interface UserJobApplyMapper {

    /**
     * 插入用户投递记录
     */
    int insert(UserJobApply userJobApply);

    /**
     * 根据ID删除用户投递记录
     */
    int deleteById(Long id);

    /**
     * 根据用户ID和招聘信息ID删除投递记录
     */
    int deleteByUserIdAndJobId(@Param("userId") Long userId, @Param("jobId") Long jobId);

    /**
     * 更新用户投递记录
     */
    int updateById(UserJobApply userJobApply);

    /**
     * 根据ID查询用户投递记录
     */
    UserJobApply selectById(Long id);

    /**
     * 根据用户ID和招聘信息ID查询投递记录
     */
    UserJobApply selectByUserIdAndJobId(@Param("userId") Long userId, @Param("jobId") Long jobId);

    /**
     * 查询用户投递记录列表（带分页）
     */
    List<UserJobApplyDTO> selectList(@Param("offset") int offset,@Param("query") UserJobApplyQueryRequest queryRequest);

    /**
     * 查询用户投递记录总数
     */
    long countByQuery(UserJobApplyQueryRequest queryRequest);
}