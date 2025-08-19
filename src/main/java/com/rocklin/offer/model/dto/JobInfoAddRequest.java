package com.rocklin.offer.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName JobInfoAddRequest
 * @Description 招聘信息添加请求
 * @Author: rocklin
 * @Date 2025/8/19 21:50
 * @Version 1.0
 */
@Data
@Schema(description = "招聘信息添加请求")
public class JobInfoAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "公司名称", required = true)
    @NotBlank(message = "公司名称不能为空")
    private String companyName;

    @Schema(description = "公司类型")
    private String companyType;

    @Schema(description = "所属行业")
    private String industry;

    @Schema(description = "招聘类型(校招/社招/实习)")
    private String recruitType;

    @Schema(description = "工作地点")
    private String workLocation;

    @Schema(description = "招聘对象(20xx年毕业)")
    private String recruitTarget;

    @Schema(description = "岗位(专业/岗位名称)")
    private String positionName;

    @Schema(description = "投递进度")
    private String applicationStatus;

    @Schema(description = "开始时间")
    private Date startTime;

    @Schema(description = "投递截止(尽快投递/x.x截止)")
    private String deadline;

    @Schema(description = "相关链接")
    private String relatedLink;

    @Schema(description = "招聘公告")
    private String announcement;

    @Schema(description = "内推码")
    private String referralCode;

    @Schema(description = "备注")
    private String remark;
}