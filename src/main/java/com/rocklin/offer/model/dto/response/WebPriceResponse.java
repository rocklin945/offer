package com.rocklin.offer.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName WebPriceResponse
 * @Description 网站价格响应DTO
 * @Author: rocklin
 * @Date 2025/9/5
 * @Version 1.0
 */
@Data
@Schema(description = "网站价格信息")
public class WebPriceResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "原价")
    private BigDecimal originalPrice;

    @Schema(description = "现价")
    private BigDecimal currentPrice;
}