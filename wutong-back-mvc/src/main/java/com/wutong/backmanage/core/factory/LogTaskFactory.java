package com.wutong.backmanage.core.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wutong.backmanage.common.constant.dictmap.states.LogSucceed;
import com.wutong.backmanage.common.constant.dictmap.states.LogType;
import com.wutong.backmanage.core.log.LogManager;
import com.wutong.backmanage.core.util.SpringContextHolder;
import com.wutong.backmanage.core.util.ToolUtil;
import com.wutong.backmanage.dao.LoginLogMapper;
import com.wutong.backmanage.dao.OperationLogMapper;
import com.wutong.backmanage.pojo.LoginLog;
import com.wutong.backmanage.pojo.OperationLog;

import java.util.TimerTask;

/**
 * 日志操作任务创建工厂
 *
 */
public class LogTaskFactory {

    private static Logger             logger             = LoggerFactory.getLogger(LogManager.class);
    private static LoginLogMapper     loginLogMapper     = SpringContextHolder.getBean(LoginLogMapper.class);
    private static OperationLogMapper operationLogMapper = SpringContextHolder.getBean(OperationLogMapper.class);

    public static TimerTask loginLog(final Integer userId, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    LoginLog loginLog = LogFactory.createLoginLog(LogType.LOGIN, userId, null, ip);
                    loginLogMapper.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建登录日志异常!", e);
                }
            }
        };
    }

    public static TimerTask loginLog(final String username, final String msg, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                LoginLog loginLog = LogFactory.createLoginLog(
                        LogType.LOGIN_FAIL, null, "账号:" + username + "," + msg, ip);
                try {
                    loginLogMapper.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建登录失败异常!", e);
                }
            }
        };
    }

    public static TimerTask exitLog(final Integer userId, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                LoginLog loginLog = LogFactory.createLoginLog(LogType.EXIT, userId, null,ip);
                try {
                    loginLogMapper.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建退出日志异常!", e);
                }
            }
        };
    }

    public static TimerTask bussinessLog(final Integer userId, final String bussinessName, final String clazzName, final String methodName, final String msg) {
        return new TimerTask() {
            @Override
            public void run() {
                OperationLog operationLog = LogFactory.createOperationLog(
                        LogType.BUSSINESS, userId, bussinessName, clazzName, methodName, msg, LogSucceed.SUCCESS);
                try {
                    operationLogMapper.insert(operationLog);
                } catch (Exception e) {
                    logger.error("创建业务日志异常!", e);
                }
            }
        };
    }

    public static TimerTask exceptionLog(final Integer userId, final Exception exception) {
        return new TimerTask() {
            @Override
            public void run() {
                String msg = ToolUtil.getExceptionMsg(exception);
                OperationLog operationLog = LogFactory.createOperationLog(
                        LogType.EXCEPTION, userId, "", null, null, msg, LogSucceed.FAIL);
                try {
                    operationLogMapper.insert(operationLog);
                } catch (Exception e) {
                    logger.error("创建异常日志异常!", e);
                }
            }
        };
    }
}
