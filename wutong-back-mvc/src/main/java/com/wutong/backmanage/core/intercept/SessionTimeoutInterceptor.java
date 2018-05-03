package com.wutong.backmanage.core.intercept;

import org.apache.shiro.session.InvalidSessionException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.wutong.backmanage.core.controller.BaseController;
import com.wutong.backmanage.core.support.HttpKit;
import com.wutong.backmanage.core.shiro.ShiroKit;

/**
 * 验证session超时的拦截器
 *
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "wutong", name = "session-open", havingValue = "true")
public class SessionTimeoutInterceptor extends BaseController {

    @Pointcut("execution(* com.wutong.backmanage.*..controller.*.*(..))")
    public void cutService() {
    }

    @Around("cutService()")
    public Object sessionTimeoutValidate(ProceedingJoinPoint point) throws Throwable {

        String servletPath = HttpKit.getRequest().getServletPath();

        if (servletPath.equals("/kaptcha") || servletPath.equals("/login") || servletPath.equals("/global/sessionError")) {
            return point.proceed();
        }else{
            if(ShiroKit.getSession().getAttribute("sessionFlag") == null){
                ShiroKit.getSubject().logout();
                throw new InvalidSessionException();
            }else{
                return point.proceed();
            }
        }
    }
}
