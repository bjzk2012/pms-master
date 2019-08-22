package cn.kcyf.pms.modular.business.dao;

import cn.kcyf.pms.modular.business.entity.Project;
import cn.kcyf.orm.jpa.dao.BasicDao;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDao extends BasicDao<Project, Long> {
}
