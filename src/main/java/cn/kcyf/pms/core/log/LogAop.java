package cn.kcyf.pms.core.log;

import cn.kcyf.pms.core.enumerate.LogType;
import cn.kcyf.pms.core.enumerate.Succeed;
import cn.kcyf.security.domain.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 日志记录
 *
 * @author Tom
 *
 */
@Aspect
@Component
public class LogAop {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LogFactory logFactory;

    @Pointcut(value = "@annotation(cn.kcyf.pms.core.log.BussinessLog)")
    public void cutService() {
    }

    @Around("cutService()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {

        //先执行业务
        Object result = point.proceed();

        try {
            handle(point);
        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }

        return result;
    }

    private void handle(ProceedingJoinPoint point) throws Exception {

        // 获取拦截的方法名
        Signature sig = point.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        String methodName = currentMethod.getName();

        // 如果当前用户未登录，不做日志
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if (null == user) {
            return;
        }

        // 获取拦截方法的参数
        String className = point.getTarget().getClass().getName();
        Object[] params = point.getArgs();

        // 获取操作名称
        BussinessLog annotation = currentMethod.getAnnotation(BussinessLog.class);
        String bussinessName = annotation.value();

        StringBuilder sb = new StringBuilder();
        for (Object param : params) {
            sb.append(param);
            sb.append(" & ");
        }

        logFactory.log(annotation.type(), bussinessName, className, methodName, Succeed.SUCCESS, sb.toString());
    }
}