package com.rocklin.offer.common.response;

import com.rocklin.offer.common.constants.Constants;
import com.rocklin.offer.common.enums.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName BaseResponse
 * @Description 统一返回结果
 * @Author: rocklin
 */
@Data
public class BaseResponse<T> implements Serializable {
    private int statusCode;
    private T data;
    private String message;

    public BaseResponse(int statusCode, T data, String message) {
        this.statusCode = statusCode;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int statusCode, T data) {
        this(statusCode, data, "");
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, data, Constants.SUCCESS);
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(200, null, Constants.SUCCESS);
    }

    public static <T> BaseResponse<T> error(int statusCode, String message) {
        return new BaseResponse<>(statusCode, null, message);
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode.getStatusCode(), null, errorCode.getMessage());
    }
}
