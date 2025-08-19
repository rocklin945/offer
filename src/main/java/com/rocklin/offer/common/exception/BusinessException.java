package com.rocklin.offer.common.exception;

import com.rocklin.offer.common.enums.ErrorCode;
import lombok.Getter;

/**
 * @ClassName BusinessException
 * @Description 业务异常类
 * @Author: rocklin
 */
@Getter
public class BusinessException extends RuntimeException{
    private final int statusCode;

    public BusinessException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public BusinessException(ErrorCode errorCode) {
        this(errorCode.getStatusCode(), errorCode.getMessage());
    }

    public BusinessException(ErrorCode errorCode, String message) {
        this(errorCode.getStatusCode(), message);
    }
}
