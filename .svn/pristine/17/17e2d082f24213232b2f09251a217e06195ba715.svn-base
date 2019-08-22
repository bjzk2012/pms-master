package cn.kcyf.pms.modular.system.service.impl;

import cn.kcyf.pms.core.model.MenuNode;
import cn.kcyf.pms.modular.system.dao.MenuDao;
import cn.kcyf.pms.modular.system.entity.Menu;
import cn.kcyf.pms.core.enumerate.Status;
import cn.kcyf.pms.modular.system.service.MenuService;
import cn.kcyf.orm.jpa.dao.BasicDao;
import cn.kcyf.orm.jpa.service.AbstractBasicService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MenuServiceImpl extends AbstractBasicService<Menu, Long> implements MenuService {
    @Autowired
    private MenuDao menuDao;

    public BasicDao<Menu, Long> getRepository() {
        return menuDao;
    }

    @Transactional(readOnly = true)
    public List<MenuNode> tree() {
        return buildTree(menuDao.findAll());
    }

    @Transactional(readOnly = true)
    public void freeze(Long menuId) {
        List<Long> childIds = new ArrayList<Long>();
        getChildId(menuId, childIds);
        menuDao.setStatusByIdIn(Status.DISABLE.ordinal(), childIds);
    }

    @Transactional(readOnly = true)
    public void unfreeze(Long menuId) {
        List<Long> childIds = new ArrayList<Long>();
        getChildId(menuId, childIds);
        menuDao.setStatusByIdIn(Status.ENABLE.ordinal(), childIds);
    }

    @Transactional(readOnly = true)
    public Set<Menu> findByIdIn(List<Long> ids) {
        return menuDao.findAllByIdIn(ids);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        List<Long> childIds = new ArrayList<Long>();
        getChildId(id, childIds);
        deletes(childIds);
    }

    private void getChildId(Long id, List<Long> childIds){
        childIds.add(id);
        List<BigInteger> temp = menuDao.getChildIdById(id);
        if (temp != null) {
            for (BigInteger childId : temp){
                getChildId(Long.valueOf(childId.toString()), childIds);
            }
        }
    }

    public static List<MenuNode> buildTree(List<Menu> menus){
        List<MenuNode> result = new ArrayList<MenuNode>();
        if (menus != null && !menus.isEmpty()){
            for (Menu menu : menus){
                if (menu.getParentId() == null && menu.getLevels() == 1){
                    MenuNode node = new MenuNode();
                    BeanUtils.copyProperties(menu, node);
                    buildChild(node, menus);
                    result.add(node);
                }
            }
        }
        return result;
    }

    public static void buildChild(MenuNode node, List<Menu> menus){
        if (menus != null && !menus.isEmpty()){
            if (node.getChildren() == null){
                node.setChildren(new ArrayList<MenuNode>());
            }
            for(Menu menu : menus){
                if (node.getId().equals(menu.getParentId())) {
                    MenuNode child = new MenuNode();
                    BeanUtils.copyProperties(menu, child);
                    node.getChildren().add(child);
                    buildChild(child, menus);
                }
            }
            if (node.getChildren().isEmpty()){
                node.setChildren(null);
            }
        }
    }
}
