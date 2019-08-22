package cn.kcyf.pms.modular.business.service;

import cn.kcyf.pms.core.enumerate.QuestionRecordType;
import cn.kcyf.pms.modular.business.entity.QuestionRecord;
import cn.kcyf.orm.jpa.service.BasicService;

public interface QuestionRecordService extends BasicService<QuestionRecord, Long> {
    QuestionRecord create(Long questionId, QuestionRecordType type, String description);
}
