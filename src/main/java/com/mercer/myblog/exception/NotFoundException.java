package com.mercer.myblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @ Date:2019/8/17
 * Auther:Mercer
 * Auther:麻前程
 */
//404异常处理
@ResponseStatus(HttpStatus.NOT_FOUND)//当为404异常时
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
