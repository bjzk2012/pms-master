package cn.kcyf.pms.modular.system.dao;

import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.pms.modular.system.entity.Notice;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeDao extends BasicDao<Notice, Long> {
}
