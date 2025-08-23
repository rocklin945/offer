package com.rocklin.offer.model.dto.request;

import com.rocklin.offer.common.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户投递记录查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户投递记录查询请求")
public class UserJobApplyQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "招聘信息ID")
    private Long jobId;

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

    @Schema(description = "岗位名称")
    private String positionName;

    @Schema(description = "投递进度")
    private String applicationStatus;

    @Schema(description = "排序字段")
    private String sortField;

    @Schema(description = "排序方式(asc/desc)")
    private String sortOrder;
}
