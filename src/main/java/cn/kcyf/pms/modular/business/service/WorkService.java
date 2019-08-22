package cn.kcyf.pms.modular.business.service;


import cn.kcyf.pms.modular.business.entity.Work;
import cn.kcyf.orm.jpa.service.BasicService;

public interface WorkService extends BasicService<Work, Long> {
    void submit(Long workId);
}
