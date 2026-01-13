package com.apigateway.common.exception;

import com.apigateway.common.constant.ResponseCode;
import lombok.Getter;

/**
 * 业务异常类
 * 用于处理业务逻辑中的异常
 *
 * @author apigateway
 * @since 1.0.0
 */
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误消息
     */
    private String message;

    public BusinessException(String message) {
        super(message);
        this.code = ResponseCode.BUSINESS_ERROR;
        this.message = message;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        // 不填充堆栈跟踪，提高性能
        return this;
    }
}
