package cn.kcyf.pms.modular.system.service;

import cn.kcyf.pms.core.model.DeptNode;
import cn.kcyf.pms.modular.system.entity.Dept;
import cn.kcyf.orm.jpa.service.BasicService;

import java.util.List;

public interface DeptService extends BasicService<Dept, Long> {
    String getParentName(Long id);
    List<DeptNode> tree();
}
