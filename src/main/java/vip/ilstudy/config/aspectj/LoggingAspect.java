package vip.ilstudy.config.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import vip.ilstudy.config.annotation.Log;
import vip.ilstudy.entity.LogEntity;
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.manager.AsyncManager;
import vip.ilstudy.manager.factory.AsyncFactory;
import vip.ilstudy.utils.ServletUtils;
import vip.ilstudy.utils.StringUtils;

/**
 * 操作日志记录处理
 * before => afterReturning => after
 */
@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // 前置通知：在方法执行之前执行
//    @Before("@annotation(log)")
//    public void logBefore(JoinPoint joinPoint, Log log) {
//    }

    // 后置通知：在方法执行之后执行（无论是否发生异常）
//    @After("@annotation(log)")
//    public void logAfter(JoinPoint joinPoint, Log log) {
//    }

    // 返回后通知：在方法成功执行之后执行
    @AfterReturning(pointcut = "@annotation(log)", returning = "jsonResult")
    public void logAfterReturning(JoinPoint joinPoint, Log log, Object jsonResult) {
        handleLog(joinPoint, log, null, jsonResult);
    }

    // 异常后通知：在方法抛出异常后执行
//    @AfterThrowing(pointcut = "@annotation(log)", throwing = "exception")
//    public void logAfterThrowing(JoinPoint joinPoint, Log log, Throwable exception) {
//    }


    protected void handleLog(final JoinPoint joinPoint, Log log, final Exception e, Object jsonResult) {
        try {
            // 获取当前的用户
            LoginUserEntity loginUser = ServletUtils.getLoginUser();
            LogEntity logEntity = new LogEntity();
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            logEntity.setMethod(StringUtils.format("{}.{}()", className, methodName));
            logEntity.setRequestMethod(ServletUtils.getRequest().getMethod());
            logEntity.setLogTitle(log.title());
            logEntity.setLogType(log.businessType().name());

            assert loginUser != null;
            logEntity.setCreateBy(loginUser.getUsername());
            AsyncManager.me().execute(AsyncFactory.recordLog(logEntity));
        } catch (Exception exp) {
            // 记录本地异常日志
            logger.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }
}
