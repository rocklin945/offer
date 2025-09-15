package com.rocklin.offer.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName Merchant
 * @Description 卡密商家实体类
 * @Author rocklin
 * @Date 2025/9/15
 * @Version 1.0
 */
@Data
@Schema(description = "卡密商家实体类")
public class Merchant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "卡密商家账号")
    private String account;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "秋招会员价格")
    private BigDecimal autumnPrice;

    @Schema(description = "校招会员价格")
    private BigDecimal campusPrice;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "逻辑删除")
    private Integer isDelete;
}