package cn.kcyf.pms.modular.system.service.impl;

import cn.kcyf.pms.modular.system.dao.RoleDao;
import cn.kcyf.pms.modular.system.entity.Role;
import cn.kcyf.pms.modular.system.service.RoleService;
import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.service.AbstractBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl extends AbstractBasicService<Role, Long> implements RoleService {
    @Autowired
    private RoleDao roleDao;
    public BasicDao<Role, Long> getRepository() {
        return roleDao;
    }

    @Transactional(readOnly = true)
    public Set<Role> findByIdIn(List<Long> ids) {
        return roleDao.findAllByIdIn(ids);
    }
}
