package com.mercer.myblog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @ Date:2019/8/17
 * Auther:Mercer
 * Auther:麻前程
 */
//统一异常处理类
@ControllerAdvice //监控所有controller异常
public class ControllerExceptionHandler {
    //日志初始化
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //异常处理方法
    @ExceptionHandler(Exception.class)
    public ModelAndView exception(HttpServletRequest request,Exception e) throws Exception {
        logger.error("Request Url : {} ; Exception : {}",request.getRequestURL(),e);
//        当异常类型为404时，不进行处理，直接抛出异常交由springboot处理，从而跳转到404页面
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
            throw e;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url",request.getRequestURL());
        modelAndView.addObject("exception",e);
        modelAndView.setViewName("error/error");
        return modelAndView;
    }

}
