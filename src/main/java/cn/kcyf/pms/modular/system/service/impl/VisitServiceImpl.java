package cn.kcyf.pms.modular.system.service.impl;

import cn.kcyf.pms.modular.system.dao.VisitDao;
import cn.kcyf.pms.modular.system.entity.Visit;
import cn.kcyf.pms.modular.system.service.VisitService;
import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.service.AbstractBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class VisitServiceImpl extends AbstractBasicService<Visit, Long> implements VisitService {
    @Autowired
    private VisitDao visitDao;

    public BasicDao<Visit, Long> getRepository() {
        return visitDao;
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> day(Date startTime, Date endTime) {
        return visitDao.day(startTime, endTime);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> month(Date startTime, Date endTime) {
        return visitDao.month(startTime, endTime);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> year(Date startTime, Date endTime) {
        return visitDao.year(startTime, endTime);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> city() {
        return visitDao.city();
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> hour(Date startTime, Date endTime) {
        return visitDao.hour(startTime, endTime);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> month2year(Date startTime, Date endTime) {
        return visitDao.month2year(startTime, endTime);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> uv() {
        return visitDao.uv();
    }
}
