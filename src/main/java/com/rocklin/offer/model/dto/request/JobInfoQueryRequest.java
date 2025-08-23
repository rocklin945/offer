package com.rocklin.offer.model.dto.request;

import com.rocklin.offer.common.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName JobInfoQueryRequest
 * @Description 招聘信息查询请求
 * @Author: rocklin
 * @Date 2025/8/19 21:15
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "招聘信息查询请求")
public class JobInfoQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "公司名称")
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

    @Schema(description = "开始时间")
    private Date startTime;

    @Schema(description = "投递截止")
    private String deadline;
}