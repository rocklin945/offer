package com.rocklin.offer.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageAccessLimit {
    int maxPageForAnonymous() default 1; // 未登录最大页数
    int maxPageForUser() default 1;      // 普通用户最大页数
}
