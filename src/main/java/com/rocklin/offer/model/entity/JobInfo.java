package com.rocklin.offer.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName JobInfo
 * @Description 招聘信息实体类
 * @Author: rocklin
 * @Date 2025/8/19 21:05
 * @Version 1.0
 */
@Data
public class JobInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司类型
     */
    private String companyType;

    /**
     * 所属行业
     */
    private String industry;

    /**
     * 招聘类型(校招/社招/实习)
     */
    private String recruitType;

    /**
     * 工作地点
     */
    private String workLocation;

    /**
     * 招聘对象(20xx年毕业)
     */
    private String recruitTarget;

    /**
     * 岗位(专业/岗位名称)
     */
    private String positionName;

    /**
     * 投递进度
     */
    private String applicationStatus;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 投递截止(尽快投递/x.x截止)
     */
    private String deadline;

    /**
     * 相关链接
     */
    private String relatedLink;

    /**
     * 招聘公告
     */
    private String announcement;

    /**
     * 内推码
     */
    private String referralCode;

    /**
     * 备注
     */
    private String remark;
}