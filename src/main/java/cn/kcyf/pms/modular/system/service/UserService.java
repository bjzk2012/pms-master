package cn.kcyf.pms.modular.system.service;

import cn.kcyf.pms.core.model.MenuNode;
import cn.kcyf.pms.modular.system.entity.User;
import cn.kcyf.orm.jpa.service.BasicService;

import java.util.List;

public interface UserService extends BasicService<User, Long> {
    List<MenuNode> getUserMenus(Long userId);
    String md5(String credentials, String saltSource);
    List<User> findAllByDeptId(Long deptId);
}
