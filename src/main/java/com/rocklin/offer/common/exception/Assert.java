package com.rocklin.offer.common.exception;

import com.rocklin.offer.common.enums.ErrorCode;

/**
 * @ClassName Assert
 * @Description 自定义断言
 * @Author: rocklin
 */
public class Assert {
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BusinessException(-1, message);
        }
    }

    public static void isTrue(boolean expression, ErrorCode errorCode) {
        if (!expression) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isTrue(boolean expression, ErrorCode errorCode, String message) {
        if (!expression) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void notNull(Object object, ErrorCode errorCode, String message) {
        if (object == null) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void isNull(Object object, ErrorCode errorCode, String message) {
        if (object != null) {
            throw new BusinessException(errorCode, message);
        }
    }
}
