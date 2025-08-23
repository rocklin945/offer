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

    @Schema(description = "岗位名称")
    private String positionName;

    @Schema(description = "投递进度")
    private String applicationStatus;
}