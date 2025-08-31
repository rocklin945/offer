package com.rocklin.offer.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName Resume
 * @Description 个人简历实体类
 * @Author: rocklin
 * @Date 2025/8/31
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resume implements Serializable {
    /**
     * 简历ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private String gender;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 地址
     */
    private String address;

    /**
     * 教育背景（学校、专业、时间等）
     */
    private String education;

    /**
     * 专业技能
     */
    private String skills;

    /**
     * 工作经验
     */
    private String workExperience;

    /**
     * 项目经历
     */
    private String projectExperience;

    /**
     * 实习经历
     */
    private String internshipExperience;

    /**
     * 荣誉证书
     */
    private String certificates;

    /**
     * 自我评价
     */
    private String selfEvaluation;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}