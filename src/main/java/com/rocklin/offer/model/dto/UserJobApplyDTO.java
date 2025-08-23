package com.rocklin.offer.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户投递记录DTO
 */
@Data
@Schema(description = "用户投递记录DTO")
public class UserJobApplyDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "招聘信息ID")
    private Long jobId;

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "公司类型")
    private String companyType;

    @Schema(description = "所属行业")
    private String industry;

    @Schema(description = "招聘类型")
    private String recruitType;

    @Schema(description = "工作地点")
    private String workLocation;

    @Schema(description = "招聘对象")
    private String recruitTarget;

    @Schema(description = "岗位名称")
    private String positionName;

    @Schema(description = "投递进度")
    private String applicationStatus;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
