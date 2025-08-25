package com.rocklin.offer.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户投递记录更新请求
 */
@Data
@Schema(description = "用户投递记录更新请求")
public class UserJobApplyUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID", required = true)
    private Long id;

    @Schema(description = "投递进度")
    private String applicationStatus;

    @Schema(description = "个人备注")
    private String personalNote;
}