package cn.kcyf.pms.modular.system.dao;

import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.pms.modular.system.entity.Holiday;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayDao extends BasicDao<Holiday, Long> {
}
