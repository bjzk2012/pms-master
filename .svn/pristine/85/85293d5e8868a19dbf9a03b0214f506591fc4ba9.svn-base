package cn.kcyf.pms.modular.business.service.impl;

import cn.kcyf.pms.modular.business.dao.ModeDao;
import cn.kcyf.pms.modular.business.entity.Mode;
import cn.kcyf.pms.modular.business.service.ModeService;
import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.service.AbstractBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeServiceImpl extends AbstractBasicService<Mode, Long> implements ModeService {
    @Autowired
    private ModeDao modeDao;
    public BasicDao<Mode, Long> getRepository() {
        return modeDao;
    }
}
