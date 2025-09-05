package com.rocklin.offer.model.dto.request;

import com.rocklin.offer.common.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class InviteCommissionPageQueryRequest extends PageRequest implements Serializable {
    /**
     * ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 用户名
     */
    private String userName;

    /**
     * 状态 (0-待确认, 1-已确认, 2-已拒绝)
     */
    private Integer status;
}