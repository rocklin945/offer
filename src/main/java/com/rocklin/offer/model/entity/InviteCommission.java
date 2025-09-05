package com.rocklin.offer.model.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 邀请佣金记录
 */
@Data
public class InviteCommission {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 邀请人用户ID
     */
    private Long userId;

    /**
     * 累计邀请用户数
     */
    private Integer invitedCount;

    /**
     * 累计获得佣金
     */
    private BigDecimal totalCommission;

    /**
     * 确认中的佣金
     */
    private BigDecimal pendingCommission;

    /**
     * 佣金余额，可用佣金
     */
    private BigDecimal balanceCommission;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态：0-待确认，1-已确认，2-已拒绝
     */
    private Integer status;
}