package cn.kcyf.pms.modular.system.service;

import cn.kcyf.pms.core.model.DeptNode;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.modular.system.entity.Dept;
import cn.kcyf.orm.jpa.service.BasicService;

import java.util.List;

public interface DeptService extends BasicService<Dept, Long> {
    String getParentName(Long id);
    List<DeptNode> tree();

    /**
     * 锁定判断是否有有误引用
     * @param deptId
     * @param userId
     * @return
     */
    ResponseData freeze(Long deptId, Long userId);

    /**
     * 解锁包括父级
     * @param deptId
     * @return
     */
    ResponseData unfreeze(Long deptId);
}
