package com.rocklin.offer.model.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 会员套餐实体类
 */
@Data
public class MembershipPlan {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 套餐名称，比如周卡、月卡
     */
    private String label;

    /**
     * 套餐价格
     */
    private BigDecimal price;

    /**
     * 有效天数
     */
    private Integer days;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态：0=下架，1=上架
     */
    private Integer status;
}