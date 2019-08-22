package cn.kcyf.pms.modular.business.service.impl;

import cn.kcyf.pms.modular.business.dao.ProjectDao;
import cn.kcyf.pms.modular.business.entity.Project;
import cn.kcyf.pms.modular.business.service.ProjectService;
import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.service.AbstractBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl extends AbstractBasicService<Project, Long> implements ProjectService {
    @Autowired
    private ProjectDao projectDao;
    public BasicDao<Project, Long> getRepository() {
        return projectDao;
    }
}
