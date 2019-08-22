package cn.kcyf.pms.modular.business.dao;

import cn.kcyf.pms.modular.business.entity.WorkRecord;
import cn.kcyf.orm.jpa.dao.BasicDao;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRecordDao extends BasicDao<WorkRecord, Long> {
}
