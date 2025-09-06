package com.rocklin.offer.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 资料名称
     */
    private String fileName;

    /**
     * 资料分类
     */
    private String category;

    /**
     * 文件UUID
     */
    private String fileUuid;

    /**
     * 总页数
     */
    private Integer totalPages;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 查看次数
     */
    private Integer viewCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}