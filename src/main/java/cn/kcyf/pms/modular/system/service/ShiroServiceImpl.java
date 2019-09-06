package cn.kcyf.pms.modular.system.service;

import cn.kcyf.commons.web.SpringContextHolder;
import cn.kcyf.orm.jpa.criteria.Criteria;
import cn.kcyf.orm.jpa.criteria.Restrictions;
import cn.kcyf.pms.core.enumerate.AuthType;
import cn.kcyf.pms.core.enumerate.LogType;
import cn.kcyf.pms.core.enumerate.Status;
import cn.kcyf.pms.core.enumerate.Succeed;
import cn.kcyf.pms.core.log.LogFactory;
import cn.kcyf.pms.modular.system.dao.MenuDao;
import cn.kcyf.pms.modular.system.dao.UserDao;
import cn.kcyf.pms.modular.system.entity.Menu;
import cn.kcyf.pms.modular.system.entity.Role;
import cn.kcyf.pms.modular.system.entity.User;
import cn.kcyf.security.domain.ShiroUser;
import cn.kcyf.security.enumerate.LoginType;
import cn.kcyf.security.service.ShiroService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("shiroService")
public class ShiroServiceImpl implements ShiroService {

    private static final String LOGIN_LOG_TPL = "用户【%s】通过IP【%s】登录【%s】";
    @Autowired
    private UserDao userDao;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private LogFactory logFactory;

    private ShiroUser getShiroUserByUser(User user) {
        if (user == null) {
            return null;
        }
        Set<String> roles = new HashSet<String>();
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            for (Role role : user.getRoles()) {
                roles.add(role.getCode());
            }
        }
        List<Long> roleIds = new ArrayList<Long>();
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            for (Role role : user.getRoles()) {
                roleIds.add(role.getId());
            }
        }
        Set<String> permissions = new HashSet<String>();
        if (roleIds != null && !roleIds.isEmpty()) {
            List<Menu> menuAll = menuDao.findByRoleIds(Arrays.asList(new Integer[]{0, 1}), roleIds);
            if (menuAll != null && !menuAll.isEmpty()) {
                for (Menu menu : menuAll) {
                    permissions.add(menu.getCode());
                }
            }
        }
        ShiroUser shiroUser = new ShiroUser(user.getId(), user.getAccount(), user.getPassword(), user.getSalt(), Status.DISABLE.equals(user.getStatus()), roles, permissions, JSON.parseObject(JSON.toJSONString(user)));
        return shiroUser;
    }

    @Transactional(readOnly = true)
    public ShiroUser getUser(String account) {
        User user = userDao.findFirstByAccount(account);
        return getShiroUserByUser(user);
    }

    public ShiroUser loginLogger(Long id, String account, String ip, int type, boolean success) {
        LogType loginType = success ? LogType.LOGIN : LogType.LOGIN_FAIL;
        String remark = success ? "成功" : "失败";
        logFactory.log(loginType, loginType.getMessage(), this.getClass().getName(), "post", Succeed.SUCCESS, String.format(LOGIN_LOG_TPL, account, ip, remark));
        return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }

    public ShiroUser checkUser(JSONObject jsonObject, LoginType type) {
        Criteria<User> criteria = new Criteria<>();
        Optional<User> optional;
        User user = null;
        if (type.equals(LoginType.DINGTALK_DRCODE)) {
            criteria.add(Restrictions.eq("dingtalkOpenId", jsonObject.getString("openId")));
            optional = userDao.findOne(criteria);
            if (optional == null || !optional.isPresent()) {
                throw new RuntimeException("该用户不存在");
            }
            user = optional.get();
            return getShiroUserByUser(user);
        }
        if (type.equals(LoginType.PHONEVCODE)) {
            String phone = jsonObject.getString("phone");
            if (!SpringContextHolder.getBean(AuthCodeService.class).check(phone, AuthType.PHONEVCODELOGIN_AUTHCODE, jsonObject.getString("vcode"))){
                throw new RuntimeException("验证码不正确或已失效");
            }
            criteria.add(Restrictions.eq("phone", phone));
            optional = userDao.findOne(criteria);
            if (optional == null || !optional.isPresent() || optional.get() == null) {
                return new ShiroUser(null, phone, null, null, Boolean.TRUE, null, null, null);
            }
            user = optional.get();
            return getShiroUserByUser(user);
        }
        return null;
    }
}
