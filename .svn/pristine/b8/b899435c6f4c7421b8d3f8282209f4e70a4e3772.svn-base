package cn.kcyf.pms.modular.business.service;


import cn.kcyf.pms.modular.business.entity.WorkRecord;
import cn.kcyf.orm.jpa.service.BasicService;

public interface WorkRecordService extends BasicService<WorkRecord, Long> {
    void audit(String workRecordId, boolean flag, String suggestions);
}
