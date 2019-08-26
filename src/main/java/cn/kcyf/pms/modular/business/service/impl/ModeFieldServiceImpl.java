package cn.kcyf.pms.modular.business.service.impl;

import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.service.AbstractBasicService;
import cn.kcyf.pms.core.enumerate.FieldType;
import cn.kcyf.pms.modular.business.dao.ModeDao;
import cn.kcyf.pms.modular.business.dao.ModeFieldDao;
import cn.kcyf.pms.modular.business.entity.Mode;
import cn.kcyf.pms.modular.business.entity.ModeField;
import cn.kcyf.pms.modular.business.service.ModeFieldService;
import cn.kcyf.pms.modular.system.dao.DictDao;
import cn.kcyf.pms.modular.system.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModeFieldServiceImpl extends AbstractBasicService<ModeField, Long> implements ModeFieldService {
    @Autowired
    private ModeFieldDao modeFieldDao;
    public BasicDao<ModeField, Long> getRepository() {
        return modeFieldDao;
    }
}
