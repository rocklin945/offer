package com.rocklin.offer.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户投递记录添加请求
 */
@Data
@Schema(description = "用户投递记录添加请求")
public class UserJobApplyAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "招聘信息ID", required = true)
    @NotNull(message = "招聘信息ID不能为空")
    private Long jobId;

    @Schema(description = "投递进度")
    private String applicationStatus;
}