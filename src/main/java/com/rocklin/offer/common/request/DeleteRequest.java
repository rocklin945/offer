package com.rocklin.offer.common.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName DeleteRequest
 * @Description 删除请求
 * @Author: rocklin
 */
@Data
public class DeleteRequest implements Serializable {
    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;
}
