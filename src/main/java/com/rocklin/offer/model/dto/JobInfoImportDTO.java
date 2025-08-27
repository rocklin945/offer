package com.rocklin.offer.model.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName JobInfoImportDTO
 * @Description 招聘信息Excel导入DTO
 * @Author: rocklin
 * @Date 2025/8/24
 * @Version 1.0
 */
@Data
public class JobInfoImportDTO {

    /**
     * 公司名称
     */
    @ExcelProperty("公司名称")
    private String companyName;

    /**
     * 公司类型
     */
    @ExcelProperty("公司类型")
    private String companyType;

    /**
     * 所属行业
     */
    @ExcelProperty("所属行业")
    private String industry;

    /**
     * 招聘类型(校招/社招/实习)
     */
    @ExcelProperty("招聘类型")
    private String recruitType;

    /**
     * 工作地点
     */
    @ExcelProperty("工作地点")
    private String workLocation;

    /**
     * 招聘对象(20xx年毕业)
     */
    @ExcelProperty("招聘对象")
    private String recruitTarget;

    /**
     * 岗位(专业/岗位名称)
     */
    @ExcelProperty("岗位信息")
    private String positionName;

    /**
     * 开始时间
     */
    @ExcelProperty("开始时间")
    @DateTimeFormat("yyyy-MM-dd")
    private Date startTime;

    /**
     * 投递截止(尽快投递/x.x截止)
     */
    @ExcelProperty("投递截止")
    private String deadline;

    /**
     * 相关链接
     */
    @ExcelProperty("相关链接")
    private String relatedLink;

    /**
     * 招聘公告
     */
    @ExcelProperty("招聘公告")
    private String announcement;

    /**
     * 内推码
     */
    @ExcelProperty("内推码")
    private String referralCode;

    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String remark;
}