package com.rocklin.offer.model.dto.request;

import com.rocklin.offer.common.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MaterialPageQueryRequest extends PageRequest {
    
    /**
     * 分类
     */
    private String category;
    
    /**
     * 关键词搜索
     */
    private String keyword;
}