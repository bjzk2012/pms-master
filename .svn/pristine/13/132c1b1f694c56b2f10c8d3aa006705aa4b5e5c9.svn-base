package cn.kcyf.pms.modular.business.service.impl;

import cn.kcyf.pms.modular.business.dao.KeyDao;
import cn.kcyf.pms.modular.business.entity.Key;
import cn.kcyf.pms.modular.business.service.KeyService;
import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.service.AbstractBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyServiceImpl extends AbstractBasicService<Key, Long> implements KeyService {
    @Autowired
    private KeyDao keyDao;
    public BasicDao<Key, Long> getRepository() {
        return keyDao;
    }
}
