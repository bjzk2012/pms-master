package cn.kcyf.pms.modular.business.dao;

import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.pms.modular.business.entity.Content;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentDao extends BasicDao<Content, Long> {
}
