package cn.kcyf.pms.modular.system.service.impl;

import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.service.AbstractBasicService;
import cn.kcyf.pms.modular.system.dao.NoticeRecordDao;
import cn.kcyf.pms.modular.system.entity.NoticeRecord;
import cn.kcyf.pms.modular.system.service.NoticeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeRecordServiceImpl extends AbstractBasicService<NoticeRecord, Long> implements NoticeRecordService {
    @Autowired
    private NoticeRecordDao noticeRecordDao;
    public BasicDao<NoticeRecord, Long> getRepository() {
        return noticeRecordDao;
    }
}
