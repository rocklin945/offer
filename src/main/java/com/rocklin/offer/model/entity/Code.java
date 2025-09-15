package com.rocklin.offer.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName Code
 * @Description 卡密实体类
 * @Author rocklin
 * @Date 2025/9/15
 * @Version 1.0
 */
@Data
@Schema(description = "卡密实体类")
public class Code implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "卡密")
    private String code;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "关联的商家ID")
    private Long merchantId;

    @Schema(description = "是否已使用")
    private Integer isUsed;

    @Schema(description = "使用者账号")
    private String userAccount;

    @Schema(description = "使用时间")
    private Date usedTime;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "逻辑删除")
    private Integer isDelete;
}