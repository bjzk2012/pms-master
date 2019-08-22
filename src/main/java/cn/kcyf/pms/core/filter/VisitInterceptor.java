package cn.kcyf.pms.core.filter;

import cn.kcyf.pms.modular.system.entity.Visit;
import cn.kcyf.pms.modular.system.service.VisitService;
import cn.kcyf.commons.web.RequestUtils;
import cn.kcyf.security.domain.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class VisitInterceptor extends HandlerInterceptorAdapter {

    protected final Logger log = LoggerFactory.getLogger(VisitInterceptor.class);
    @Autowired
    private VisitService visitService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long time = System.currentTimeMillis();
        time = System.currentTimeMillis() - time;
        String uri = request.getRequestURI();
        String ip = RequestUtils.getIpAddr(request);
        // 访问时间
        log.debug("access to {} from {} take {} ms。", new String[]{uri, ip, time + ""});
        addVisit(uri, ip, (ShiroUser) SecurityUtils.getSubject().getPrincipal());
        return true;
    }


    @Async("taskExecutor")
    protected void addVisit(String uri, String ip, ShiroUser shiroUser) {
        Visit visit = new Visit();
        visit.setIp(ip);
        visit.setUri(uri);
        if (shiroUser != null && shiroUser.getId() != null) {
            visit.setLoginId(shiroUser.getId());
            visit.setLoginName(shiroUser.getAccount());
        } else {
            visit.setLoginName("游客");
        }
        visit.setCreateTime(new Date());
        visit.setCreateUserId(1L);
        visit.setCreateUserName("admin");
        visit.setSendTime(visit.getCreateTime());
        visitService.create(visit);
    }

}
