package cn.kcyf.pms.core.controller;

import cn.kcyf.pms.core.model.SuccessResponseData;
import cn.kcyf.orm.jpa.entity.TableDomain;
import cn.kcyf.security.domain.ShiroUser;
import org.apache.shiro.SecurityUtils;

/**
 * 所有Controller的父类
 *
 * @author Tom
 */
public abstract class BasicController {
    protected final String REDIRECT = "redirect:";
    protected final String FORWARD = "forward:";
    protected final static SuccessResponseData SUCCESS_TIP = new SuccessResponseData();

    protected ShiroUser getUser() {
        return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }

    protected Boolean isAuthenticated(){
        return SecurityUtils.getSubject().isAuthenticated();
    }

    protected void create(TableDomain domain) {
        ShiroUser shiroUser = getUser();
        domain.setId(null);
        if (shiroUser != null) {
            domain.setCreateUserId(shiroUser.getId());
            domain.setCreateUserName(shiroUser.getAccount());
        } else {
            domain.setCreateUserId(1L);
            domain.setCreateUserName("admin");
        }
    }

    protected void update(TableDomain domain) {
        ShiroUser shiroUser = getUser();
        if (shiroUser != null) {
            domain.setLastUpdateUserId(shiroUser.getId());
            domain.setLastUpdateUserName(shiroUser.getAccount());
        } else {
            domain.setLastUpdateUserId(1L);
            domain.setLastUpdateUserName("admin");
        }
    }
}
