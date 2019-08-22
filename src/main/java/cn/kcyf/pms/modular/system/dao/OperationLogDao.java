package cn.kcyf.pms.modular.system.dao;

import cn.kcyf.pms.modular.system.entity.OperationLog;
import cn.kcyf.orm.jpa.dao.BasicDao;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationLogDao extends BasicDao<OperationLog, Long> {
}
