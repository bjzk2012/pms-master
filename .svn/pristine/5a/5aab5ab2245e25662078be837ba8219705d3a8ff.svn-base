package cn.kcyf.pms.modular.system.service.impl;

import cn.kcyf.pms.core.enumerate.AuthType;
import cn.kcyf.pms.core.enumerate.YesOrNo;
import cn.kcyf.pms.modular.system.dao.AuthCodeDao;
import cn.kcyf.pms.modular.system.entity.AuthCode;
import cn.kcyf.pms.modular.system.service.AuthCodeService;
import cn.kcyf.commons.utils.DateUtils;
import cn.kcyf.commons.utils.SmsUtils;
import cn.kcyf.orm.jpa.criteria.Criteria;
import cn.kcyf.orm.jpa.criteria.Restrictions;
import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.service.AbstractBasicService;
import cn.kcyf.security.domain.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AuthCodeServiceImpl extends AbstractBasicService<AuthCode, Long> implements AuthCodeService {
    @Autowired
    private AuthCodeDao authCodeDao;
    @Autowired
    private SmsUtils smsUtils;
    public BasicDao<AuthCode, Long> getRepository() {
        return authCodeDao;
    }

    @Transactional
    public AuthCode send(String mobile, AuthType type, String memberName, String content) {
        AuthCode entity = new AuthCode();
        entity.setAuth(content);
        entity.setMobile(mobile);
        entity.setType(type);
        entity.setUesd(YesOrNo.NO);
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if (shiroUser != null) {
            entity.setCreateUserId(shiroUser.getId());
            entity.setCreateUserName(shiroUser.getAccount());
        } else {
            entity.setCreateUserId(1L);
            entity.setCreateUserName("admin");
        }
        switch (type){
            case REGISTER_AUTHCODE:
                entity.setContent(String.format(type.getTemplate(), content));
                break;
            case RESTPASSWORD_AUTHCODE:
                entity.setContent(String.format(type.getTemplate(), memberName, content));
                break;
        }
//        String result = smsUtils.send(mobile, entity.getContent(), DateUtils.format(new Date(), "yyyy-MM-dd"));
//        if("Success".equals(JSON.parseObject(result).get("returnstatus"))){
            authCodeDao.save(entity);
//            return entity;
//        }
        return null;
    }

    @Transactional
    public Boolean check(String mobile, AuthType type, String content) {
        Criteria<AuthCode> criteria = new Criteria<AuthCode>();
        criteria.add(Restrictions.eq("mobile", mobile));
        criteria.add(Restrictions.eq("type", type));
        List<AuthCode> list = authCodeDao.findAll(criteria, new Sort(Sort.Direction.DESC, "createTime"));
        if (list == null || list.isEmpty()){
            return Boolean.FALSE;
        }
        AuthCode dbinfo = list.get(0);
        if (dbinfo == null || dbinfo.getId() == null){
            return Boolean.FALSE;
        }
        Date time = DateUtils.addTime(new Date(), Calendar.SECOND, -180);
        if (dbinfo.getCreateTime().before(time)){
            return Boolean.FALSE;
        }
        if (!dbinfo.getAuth().equals(content)){
            return Boolean.FALSE;
        }
        dbinfo.setUesd(YesOrNo.YES);
        authCodeDao.save(dbinfo);
        return Boolean.TRUE;
    }
}
