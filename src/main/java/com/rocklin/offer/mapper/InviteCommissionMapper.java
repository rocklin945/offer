package com.rocklin.offer.mapper;

import com.rocklin.offer.model.dto.request.InviteCommissionPageQueryRequest;
import com.rocklin.offer.model.entity.InviteCommission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;
import java.util.List;

/**
 * 邀请佣金记录Mapper
 */
@Mapper
public interface InviteCommissionMapper {
    /**
     * 插入佣金记录
     */
    Long insert(InviteCommission commission);

    /**
     * 根据用户ID查询佣金记录
     */
    InviteCommission selectByUserId(@Param("userId") Long userId);

    /**
     * 根据ID查询佣金记录
     */
    InviteCommission selectById(@Param("id") Long id);

    /**
     * 增加待结算佣金
     */
    Long increasePendingCommission(@Param("userId") Long userId, @Param("amount") BigDecimal amount);

    /**
     * 增加邀请用户数
     */
    Long increaseInvitedCount(@Param("userId") Long userId);

    /**
     * 分页查询佣金记录
     */
    List<InviteCommission> selectCommissionPage(@Param("query") InviteCommissionPageQueryRequest query,
                                               @Param("offset") int offset,
                                               @Param("pageSize") int pageSize);

    /**
     * 查询佣金记录总数
     */
    Long selectCommissionCount(@Param("query") InviteCommissionPageQueryRequest query);

    /**
     * 确认佣金
     */
    Long confirmCommission(@Param("id") Long id, @Param("amount") BigDecimal amount);

    /**
     * 拒绝佣金
     */
    Long rejectCommission(@Param("id") Long id);

    Long update(InviteCommission inviteCommission);
}