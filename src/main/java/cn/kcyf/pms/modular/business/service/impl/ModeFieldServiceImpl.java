package cn.kcyf.pms.modular.business.service.impl;

import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.service.AbstractBasicService;
import cn.kcyf.pms.modular.business.dao.ModeFieldDao;
import cn.kcyf.pms.modular.business.entity.ModeField;
import cn.kcyf.pms.modular.business.service.ModeFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeFieldServiceImpl extends AbstractBasicService<ModeField, Long> implements ModeFieldService {
    @Autowired
    private ModeFieldDao modeFieldDao;
    public BasicDao<ModeField, Long> getRepository() {
        return modeFieldDao;
    }
}
