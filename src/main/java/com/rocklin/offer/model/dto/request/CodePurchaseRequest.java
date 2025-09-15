package com.rocklin.offer.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName CodePurchaseRequest
 * @Description 卡密购买请求参数
 * @Author rocklin
 * @Date 2025/9/15
 * @Version 1.0
 */
@Data
@Schema(description = "卡密购买请求参数")
public class CodePurchaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "商品价格不能为空")
    private String price;

    @NotNull(message = "数量不能为空")
    private Integer quantity = 1;
}