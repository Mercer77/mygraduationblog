package com.mercer.myblog.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @ Date:2019/8/17
 * Auther:Mercer
 * Auther:麻前程
 * Describe:日志切面类
 */
@Aspect //定义切面类
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    切点
    @Pointcut("execution(* com.mercer.myblog.controller..*.*(..))")
    public void log(){ }

    @After("log()")
    public void after(){
        logger.info("----------after----------");
    }
    @Before("log()")
    public void before(JoinPoint joinPoint){
//        获取HttpRequestServlet
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        获取url ip等属性
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        logger.info("requestLog : {}",requestLog);
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void afterReturn(Object result){
        logger.info("Return : {}",result);
    }


//    请求日志信息类
    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

    public RequestLog(String url, String ip, String classMethod, Object[] args) {
        this.url = url;
        this.ip = ip;
        this.classMethod = classMethod;
        this.args = args;
    }

    @Override
    public String toString() {
        return "url='" + url + '\'' +
                ", ip='" + ip + '\'' +
                ", classMethod='" + classMethod + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}

}
