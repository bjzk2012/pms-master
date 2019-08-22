package cn.kcyf.pms.modular.business.dao;

import cn.kcyf.pms.modular.business.entity.QuestionRecord;
import cn.kcyf.orm.jpa.dao.BasicDao;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRecordDao extends BasicDao<QuestionRecord, Long> {
}
