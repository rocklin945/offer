package com.rocklin.offer.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName WebInfo
 * @Description 网站概览信息实体类
 * @Author: rocklin
 * @Date 2025/8/24 
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 网站总浏览量
     */
    private Long pv;

    /**
     * 会员展示图片URL
     */
    private String imageUrl;

    /**
     * 用户数量
     */
    private Integer userCount;

    /**
     * 招聘信息数量
     */
    private Integer jobCount;

    /**
     * 最近活动1
     */
    private String activity1;

    /**
     * 最近活动2
     */
    private String activity2;

    /**
     * 最近活动3
     */
    private String activity3;

    /**
     * 最近活动4
     */
    private String activity4;

    /**
     * 最近活动5
     */
    private String activity5;

    /**
     * 最近活动1时间
     */
    private LocalDateTime activity1Time;

    /**
     * 最近活动2时间
     */
    private LocalDateTime activity2Time;

    /**
     * 最近活动3时间
     */
    private LocalDateTime activity3Time;

    /**
     * 最近活动4时间
     */
    private LocalDateTime activity4Time;

    /**
     * 最近活动5时间
     */
    private LocalDateTime activity5Time;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}