package cn.kcyf.pms.core.log;

import cn.kcyf.pms.core.enumerate.LogType;
import cn.kcyf.pms.core.enumerate.Succeed;
import cn.kcyf.pms.modular.system.entity.OperationLog;
import cn.kcyf.pms.modular.system.service.OperationLogService;
import cn.kcyf.security.domain.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 日志操作任务创建工厂
 *
 * @author Tom
 */
@Component
public class LogFactory {

    @Autowired
    private OperationLogService operationLogService;

    @Async
    public void log(LogType logType, String bussinessName, String clazzName, String methodName, Succeed succeed, String msg) {
        OperationLog operationLog = new OperationLog();
        operationLog.setLogType(logType);
        operationLog.setLogName(bussinessName);
        if (SecurityUtils.getSubject().isAuthenticated()) {
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            operationLog.setUserId(shiroUser.getId());
            operationLog.setUserName(shiroUser.getAccount());
        }
        operationLog.setCreateTime(new Date());
        operationLog.setClassName(clazzName);
        operationLog.setMethod(methodName);
        operationLog.setCreateTime(new Date());
        operationLog.setSucceed(succeed);
        operationLog.setMessage(msg);
        operationLogService.create(operationLog);
    }
}
