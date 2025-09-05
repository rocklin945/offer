package com.rocklin.offer.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName WebInfoResponse
 * @Description 网站信息响应DTO
 * @Author: rocklin
 * @Date 2025/8/24
 * @Version 1.0
 */
@Data
@Schema(description = "网站信息响应")
public class WebInfoResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "网站总浏览量")
    private Long pv;

    @Schema(description = "会员展示图片URL")
    private String imageUrl;

    @Schema(description = "用户总数")
    private Integer userCount;

    @Schema(description = "会员数量")
    private Integer memberCount;

    @Schema(description = "招聘信息数量")
    private Integer jobCount;

    @Schema(description = "原价")
    private BigDecimal originalPrice;

    @Schema(description = "现价")
    private BigDecimal currentPrice;

    @Schema(description = "最近活动1")
    private String activity1;

    @Schema(description = "最近活动2")
    private String activity2;

    @Schema(description = "最近活动3")
    private String activity3;

    @Schema(description = "最近活动4")
    private String activity4;

    @Schema(description = "最近活动5")
    private String activity5;

    @Schema(description = "最近活动1时间")
    private LocalDateTime activity1Time;

    @Schema(description = "最近活动2时间")
    private LocalDateTime activity2Time;

    @Schema(description = "最近活动3时间")
    private LocalDateTime activity3Time;

    @Schema(description = "最近活动4时间")
    private LocalDateTime activity4Time;

    @Schema(description = "最近活动5时间")
    private LocalDateTime activity5Time;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}