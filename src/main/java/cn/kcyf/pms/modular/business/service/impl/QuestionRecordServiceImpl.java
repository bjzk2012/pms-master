package cn.kcyf.pms.modular.business.service.impl;

import cn.kcyf.pms.core.enumerate.QuestionRecordType;
import cn.kcyf.pms.modular.business.dao.QuestionRecordDao;
import cn.kcyf.pms.modular.business.entity.QuestionRecord;
import cn.kcyf.pms.modular.business.service.QuestionRecordService;
import cn.kcyf.commons.utils.DateUtils;
import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.service.AbstractBasicService;
import cn.kcyf.security.domain.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class QuestionRecordServiceImpl extends AbstractBasicService<QuestionRecord, Long> implements QuestionRecordService {
    private static final String DESCRIPTION_TPL = "%s在%s【%s】了问题（ID为：%s）,完成状态为[%s],备注：%s。";
    @Autowired
    private QuestionRecordDao questionRecordDao;

    public BasicDao<QuestionRecord, Long> getRepository() {
        return questionRecordDao;
    }

    @Transactional
    public QuestionRecord create(Long questionId, QuestionRecordType type, String description) {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        QuestionRecord record = new QuestionRecord();
        record.setQuestionId(questionId);
        record.setType(type);
        record.setStatus(type.getStatus());
        record.setOperator(shiroUser.getDetail().getString("name"));
        record.setCreateUserId(shiroUser.getId());
        record.setCreateUserName(shiroUser.getAccount());
        if (StringUtils.isEmpty(description)) {
            description = "无";
        }
        description = String.format(DESCRIPTION_TPL, record.getOperator(), DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), type.getMessage(), questionId, type.getStatus().getMessage(), description);
        record.setDescription(description);
        questionRecordDao.save(record);
        return record;
    }
}
