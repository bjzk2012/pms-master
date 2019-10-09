package cn.kcyf.pms.modular.business.service.impl;

import cn.kcyf.commons.utils.DateUtils;
import cn.kcyf.pms.core.enumerate.WorkStatus;
import cn.kcyf.pms.core.util.WorkDayUtil;
import cn.kcyf.pms.modular.business.dao.WorkAuditDao;
import cn.kcyf.pms.modular.business.dao.WorkDao;
import cn.kcyf.pms.modular.business.dao.WorkRecordDao;
import cn.kcyf.pms.modular.business.entity.Work;
import cn.kcyf.pms.modular.business.entity.WorkAudit;
import cn.kcyf.pms.modular.business.entity.WorkRecord;
import cn.kcyf.pms.modular.business.service.WorkService;
import cn.kcyf.pms.modular.system.dao.UserDao;
import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.service.AbstractBasicService;
import cn.kcyf.security.domain.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

@Service
public class WorkServiceImpl extends AbstractBasicService<Work, Long> implements WorkService {
    @Autowired
    private WorkDao workDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private WorkRecordDao workRecordDao;
    @Autowired
    private WorkAuditDao workAuditDao;
    public BasicDao<Work, Long> getRepository() {
        return workDao;
    }

    @Transactional
    public void submit(Long workId) {
        Work dbwork = workDao.getOne(workId);
        Set<WorkRecord> records = dbwork.getRecords();
        if (records == null || records.isEmpty()) {
            throw new RuntimeException("工单未填写，无法提交！");
        }
        // TODO 此处日期检验暂时使用实时校验，后续版本规划修改为按照排期表判断
//        if (dbwork.getToday().before(WorkDayUtil.get(new Date(), 3))){
//            throw new RuntimeException("工单已过提交时效，无法提交！");
//        }
        int time = 0;
        for (WorkRecord record : records) {
            time += record.getTime();
        }
        if (time != 7){
            throw new RuntimeException("工单总工时必须为7小时，无法提交！");
        }
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        int i = 0;
        for (WorkRecord record : records) {
            if (record.getStatus().equals(WorkStatus.SUBMIT) || record.getStatus().equals(WorkStatus.FINISH)){
                continue;
            }
            if (record.getStatus().equals(WorkStatus.REFUSE)){
                throw new RuntimeException("本次提交存在已拒绝的工单，请修改工单后再提交！");
            }
            i++;
            record.setLastUpdateTime(new Date());
            record.setLastUpdateUserId(shiroUser.getId());
            record.setLastUpdateUserName(shiroUser.getAccount());
            record.setSubmitUserName(userDao.getOne(shiroUser.getId()).getName());
            record.setSubmitTime(new Date());
            record.setStatus(WorkStatus.SUBMIT);
            workRecordDao.save(record);
            WorkAudit audit = new WorkAudit();
            audit.setId(null);
            audit.setCreateTime(new Date());
            audit.setCreateUserId(shiroUser.getId());
            audit.setCreateUserName(shiroUser.getAccount());
            audit.setStatus(WorkStatus.SUBMIT);
            audit.setWorkRecordId(record.getId());
            audit.setRemark(String.format("用户【%s】提交【%s】日工作日志【%s】", shiroUser.getAccount(), dbwork.getTodayRemark(), record.getContent()));
            workAuditDao.save(audit);
        }
        if (i == 0){
            throw new RuntimeException("日志中没有可提交的工单！");
        }
    }
}
