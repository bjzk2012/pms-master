package cn.kcyf.pms.modular.business.service.impl;

import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.service.AbstractBasicService;
import cn.kcyf.pms.modular.business.dao.ContentDao;
import cn.kcyf.pms.modular.business.entity.Content;
import cn.kcyf.pms.modular.business.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentServiceImpl extends AbstractBasicService<Content, Long> implements ContentService {
    @Autowired
    private ContentDao contentDao;

    @Override
    public BasicDao<Content, Long> getRepository() {
        return contentDao;
    }
}
