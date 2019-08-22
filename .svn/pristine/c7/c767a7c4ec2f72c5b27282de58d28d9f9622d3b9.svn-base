package cn.kcyf.pms.modular.system.dao;

import cn.kcyf.pms.modular.system.entity.Role;
import cn.kcyf.orm.jpa.dao.BasicDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleDao extends BasicDao<Role, Long> {
    Set<Role> findAllByIdIn(List<Long> ids);
}
