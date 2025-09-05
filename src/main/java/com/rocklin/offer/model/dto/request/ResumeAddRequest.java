package com.rocklin.offer.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ResumeAddRequest
 * @Description 简历添加请求
 * @Author: rocklin
 * @Date 2025/8/31
 * @Version 1.0
 */
@Data
public class ResumeAddRequest implements Serializable {
    /**
     * 姓名
     */
    @Size(max = 255, message = "姓名长度不能超过255个字")
    private String name;

    /**
     * 手机号
     */
    @Size(max = 255, message = "手机号长度不能超过255个字")
    private String phone;

    /**
     * 微信号
     */
    @Size(max = 255, message = "微信号长度不能超过255个字")
    private String wechat;

    /**
     * 邮箱
     */
    @Size(max = 255, message = "邮箱长度不能超过255个字")
    private String email;

    /**
     * 性别
     */
    @Size(max = 255, message = "性别长度不能超过255个字")
    private String gender;

    /**
     * 出生日期
     */
    @Size(max = 255, message = "出生日期长度不能超过255个字")
    private String birthday;

    /**
     * 地址
     */
    @Size(max = 255, message = "地址长度不能超过255个字")
    private String address;

    /**
     * 教育背景
     */
    @Size(max = 255, message = "教育背景长度不能超过255个字")
    private String education;

    /**
     * 专业技能
     */
    @Size(max = 2000, message = "专业技能长度不能超过2000个字")
    private String skills;

    /**
     * 工作经验
     */
    @Size(max = 2000, message = "工作经历长度不能超过2000个字")
    private String workExperience;

    /**
     * 项目经历
     */
    @Size(max = 2000, message = "项目经历长度不能超过2000个字")
    private String projectExperience;

    /**
     * 实习经历
     */
    @Size(max = 2000, message = "实习经历长度不能超过2000个字")
    private String internshipExperience;

    /**
     * 荣誉证书
     */
    @Size(max = 2000, message = "荣誉证书长度不能超过2000个字")
    private String certificates;

    /**
     * 自我评价
     */
    @Size(max = 2000, message = "自我评价长度不能超过2000个字")
    private String selfEvaluation;
}