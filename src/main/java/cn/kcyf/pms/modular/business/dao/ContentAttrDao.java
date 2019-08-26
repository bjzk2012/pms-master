package cn.kcyf.pms.modular.business.dao;

import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.pms.modular.business.entity.ContentAttr;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentAttrDao extends BasicDao<ContentAttr, Long> {
}
