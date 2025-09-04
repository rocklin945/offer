package com.rocklin.offer.mapper;

import com.rocklin.offer.model.entity.InviteCommission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;

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
     * 增加待结算佣金
     */
    Long increasePendingCommission(@Param("userId") Long userId, @Param("amount") BigDecimal amount);

    /**
     * 增加邀请用户数
     */
    Long increaseInvitedCount(@Param("userId") Long userId);
}