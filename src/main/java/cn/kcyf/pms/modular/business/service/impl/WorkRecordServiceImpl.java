package cn.kcyf.pms.modular.business.service.impl;

import cn.kcyf.pms.core.enumerate.WorkStatus;
import cn.kcyf.pms.modular.business.dao.ProjectDao;
import cn.kcyf.pms.modular.business.dao.WorkAuditDao;
import cn.kcyf.pms.modular.business.dao.WorkDao;
import cn.kcyf.pms.modular.business.dao.WorkRecordDao;
import cn.kcyf.pms.modular.business.entity.Project;
import cn.kcyf.pms.modular.business.entity.Work;
import cn.kcyf.pms.modular.business.entity.WorkAudit;
import cn.kcyf.pms.modular.business.entity.WorkRecord;
import cn.kcyf.pms.modular.business.service.WorkRecordService;
import cn.kcyf.commons.utils.ArrayUtils;
import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.service.AbstractBasicService;
import cn.kcyf.security.domain.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class WorkRecordServiceImpl extends AbstractBasicService<WorkRecord, Long> implements WorkRecordService {
    @Autowired
    private WorkDao workDao;
    @Autowired
    private WorkRecordDao workRecordDao;
    @Autowired
    private WorkAuditDao workAuditDao;
    @Autowired
    private ProjectDao projectDao;

    public BasicDao<WorkRecord, Long> getRepository() {
        return workRecordDao;
    }

    @Transactional
    public void audit(String ids, boolean flag, String suggestions) {
        List<Long> idlist = ArrayUtils.convertStrArrayToLong(ids.split(","));
        if (idlist != null && !idlist.isEmpty()) {
            for (Long id : idlist) {
                audit(id, flag, suggestions);
            }
        }
    }

    private void audit(Long id, boolean flag, String suggestions) {
        WorkStatus status = flag ? WorkStatus.FINISH : WorkStatus.REFUSE;
        WorkRecord dbWorkRecord = workRecordDao.getOne(id);
        if (!dbWorkRecord.getStatus().equals(WorkStatus.SUBMIT)) {
            return;
        }
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        dbWorkRecord.setLastUpdateTime(new Date());
        dbWorkRecord.setLastUpdateUserId(shiroUser.getId());
        dbWorkRecord.setLastUpdateUserName(shiroUser.getAccount());
        dbWorkRecord.setLastAuditTime(new Date());
        dbWorkRecord.setLastAuditUserId(shiroUser.getId());
        dbWorkRecord.setLastAuditUserName(shiroUser.getAccount());
        dbWorkRecord.setStatus(status);
        update(dbWorkRecord);
        Work dbwork = workDao.getOne(dbWorkRecord.getWorkId());
        WorkAudit audit = new WorkAudit();
        audit.setId(null);
        audit.setCreateTime(new Date());
        audit.setCreateUserId(shiroUser.getId());
        audit.setCreateUserName(shiroUser.getAccount());
        audit.setStatus(status);
        audit.setWorkRecordId(dbWorkRecord.getId());
        audit.setRemark(String.format("用户【%s】%s用户【%s】的【%s】日工作日志【%s】审核", shiroUser.getAccount(), status.getAction(), dbwork.getCreateUserName(), dbwork.getTodayRemark(), dbWorkRecord.getContent()));
        audit.setSuggestions(suggestions);
        workAuditDao.save(audit);
        if (flag) {
            Project dbproject = projectDao.getOne(dbWorkRecord.getProjectId());
            dbproject.setUsed(dbproject.getUsed() + dbWorkRecord.getTime());
            dbproject.setLastUpdateTime(new Date());
            dbproject.setLastUpdateUserId(shiroUser.getId());
            dbproject.setLastUpdateUserName(shiroUser.getAccount());
            projectDao.save(dbproject);
        }
    }
}
