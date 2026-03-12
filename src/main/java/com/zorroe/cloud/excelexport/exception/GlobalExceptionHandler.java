package com.zorroe.cloud.excelexport.exception;

import com.zorroe.cloud.excelexport.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        log.warn("请求参数错误 - URI: {}, 错误: {}", request.getRequestURI(), e.getMessage());
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e, HttpServletRequest request) {
        log.error("系统异常 - URI: {}, 错误: {}", request.getRequestURI(), e.getMessage(), e);
        return Result.fail("系统繁忙，请稍后重试");
    }
}
