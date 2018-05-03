package com.wutong.backmanage.core.intercept;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import org.springframework.stereotype.Component;

import com.wutong.backmanage.core.controller.BaseController;
import com.wutong.backmanage.core.util.HttpSessionHolder;

/**
 * 静态调用session的拦截器
 *
 */
@Aspect
@Component
public class SessionInterceptor extends BaseController {

    @Pointcut("execution(* com.wutong.backmanage.*..controller.*.*(..))")
    public void cutService() {
    }

    @Around("cutService()")
    public Object sessionKit(ProceedingJoinPoint point) throws Throwable {

        HttpSessionHolder.put(super.getHttpServletRequest().getSession());
        try {
            return point.proceed();
        } finally {
            HttpSessionHolder.remove();
        }
    }
}
