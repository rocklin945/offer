package com.rocklin.offer.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 会员检查注解
 * 用于标记需要会员权限的接口
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MemberCheck {
    
    /**
     * 允许的最大页数（非会员用户）
     */
    int maxPage() default 2;
    
    /**
     * 允许的最大页面大小（非会员用户）
     */
    int maxPageSize() default 10;
    
    /**
     * 错误信息
     */
    String message() default "需要成为会员才能继续使用此功能";
}
