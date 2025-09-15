package com.rocklin.offer.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName CodeRedeemRequest
 * @Description 卡密兑换请求参数
 * @Author rocklin
 * @Date 2025/9/15
 * @Version 1.0
 */
@Data
@Schema(description = "卡密兑换请求参数")
public class CodeRedeemRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "用户账号不能为空")
    private String account;

    @NotBlank(message = "用户密码不能为空")
    private String password;

    @NotBlank(message = "卡密不能为空")
    private String code;
}