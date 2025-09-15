package com.rocklin.offer.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName codePriceResponse
 * @Description 获取卡密价格
 * @Author: rocklin
 * @Date 2025/9/15 19:45
 * @Version 1.0
 */
@Data
public class CodePriceResponse {
    @Schema(description = "秋招会员价格")
    private BigDecimal autumnPrice;

    @Schema(description = "校招会员价格")
    private BigDecimal campusPrice;
}
