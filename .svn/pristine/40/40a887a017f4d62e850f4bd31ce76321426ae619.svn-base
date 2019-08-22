package cn.kcyf.pms.modular.system.service;

import cn.kcyf.pms.core.model.MenuNode;
import cn.kcyf.pms.modular.system.entity.Menu;
import cn.kcyf.orm.jpa.service.BasicService;

import java.util.List;
import java.util.Set;

public interface MenuService extends BasicService<Menu, Long> {
    List<MenuNode> tree();

    void freeze(Long menuId);

    void unfreeze(Long menuId);

    Set<Menu> findByIdIn(List<Long> ids);
}
