
package cn.kcyf.pms.modular.system.controller;

import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.core.enumerate.Status;
import cn.kcyf.pms.core.log.BussinessLog;
import cn.kcyf.pms.core.model.MenuNode;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.modular.system.entity.Menu;
import cn.kcyf.pms.modular.system.service.MenuService;
import cn.kcyf.orm.jpa.criteria.Criteria;
import cn.kcyf.orm.jpa.criteria.Restrictions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单控制器
 *
 * @author Tom
 */
@Controller
@RequestMapping("/menu")
@Api(tags = "菜单管理", description = "菜单管理")
@RequiresRoles(value = "administrator")
public class MenuController extends BasicController {

    private static String PREFIX = "/modular/system/menu/";

    @Autowired
    private MenuService menuService;

    @GetMapping("")
    @RequiresPermissions(value = "menu")
    public String index() {
        return PREFIX + "menu.html";
    }

    @GetMapping(value = "/menu_add")
    @RequiresPermissions(value = "menu_add")
    public String menuAdd() {
        return PREFIX + "menu_add.html";
    }

    @GetMapping(value = "/menu_edit")
    @RequiresPermissions(value = "menu_edit")
    public String menuEdit(Long menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return PREFIX + "menu_edit.html";
    }

    @GetMapping(value = "/treeSelect")
    @ResponseBody
    @ApiOperation("查询菜单树形下拉列表")
    @RequiresPermissions(value = {"menu_add", "menu_edit"}, logical = Logical.OR)
    public List<MenuNode> treeSelect() {
        MenuNode root = new MenuNode();
        root.setId(0L);
        root.setName("顶级菜单");
        List<MenuNode> list = menuService.tree();
        root.setChildren(list);
        List<MenuNode> result = new ArrayList<MenuNode>();
        result.add(root);
        return result;
    }

    @GetMapping(value = "/list")
    @ResponseBody
    @ApiOperation("查询菜单树形列表")
    @RequiresPermissions(value = "menu_list")
    public ResponseData list(String menuName) {
        Criteria<Menu> criteria = new Criteria<Menu>();
        if (!StringUtils.isEmpty(menuName)) {
            criteria.add(Restrictions.or(Restrictions.like("name", menuName), Restrictions.like("code", menuName)));
        }
        return ResponseData.list(menuService.findList(criteria, new Sort(Sort.Direction.ASC, "sort")));
    }

    @PostMapping("/add")
    @ResponseBody
    @BussinessLog("新增菜单")
    @ApiOperation("新增菜单")
    @RequiresPermissions(value = "menu_add")
    public ResponseData add(@Valid Menu menu, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        create(menu);
        menu.setStatus(Status.ENABLE);
        if (menu.getParentId() != null && !menu.getParentId().equals(0L)) {
            menu.setLevels(menuService.getOne(menu.getParentId()).getLevels() + 1);
        } else {
            menu.setParentId(null);
            menu.setLevels(1);
        }
        menuService.create(menu);
        return SUCCESS_TIP;
    }

    @PostMapping("/edit")
    @ResponseBody
    @BussinessLog("修改菜单")
    @ApiOperation("修改菜单")
    @RequiresPermissions(value = "menu_edit")
    public ResponseData edit(@Valid Menu menu, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Menu dbmenu = menuService.getOne(menu.getId());
        update(dbmenu);
        menu.setStatus(Status.ENABLE);
        dbmenu.setCode(menu.getCode());
        dbmenu.setName(menu.getName());
        dbmenu.setIcon(menu.getIcon());
        dbmenu.setUrl(menu.getUrl());
        dbmenu.setMenuFlag(menu.getMenuFlag());
        dbmenu.setDescription(menu.getDescription());
        dbmenu.setSort(menu.getSort());
        dbmenu.setParentId(menu.getParentId());
        if (dbmenu.getParentId() != null && !menu.getParentId().equals(0L)) {
            dbmenu.setLevels(menuService.getOne(dbmenu.getParentId()).getLevels() + 1);
        } else {
            menu.setParentId(null);
            menu.setLevels(1);
        }
        menuService.update(dbmenu);
        return SUCCESS_TIP;
    }

    @PostMapping("/delete/{menuId}")
    @ResponseBody
    @BussinessLog("删除菜单")
    @ApiOperation("删除菜单")
    @RequiresPermissions(value = "menu_delete")
    public ResponseData delete(@PathVariable Long menuId) {
        menuService.delete(menuId);
        return SUCCESS_TIP;
    }

    @GetMapping("/detail/{menuId}")
    @ResponseBody
    @RequiresPermissions(value = "menu_edit")
    public ResponseData detail(@PathVariable Long menuId) {
        Menu menu = menuService.getOne(menuId);
        return ResponseData.success(menu);
    }

    @PostMapping("/freeze/{menuId}")
    @ResponseBody
    @BussinessLog("禁用菜单")
    public ResponseData freeze(@PathVariable Long menuId) {
        menuService.freeze(menuId);
        return SUCCESS_TIP;
    }

    @PostMapping("/unfreeze/{menuId}")
    @ResponseBody
    @BussinessLog("启用菜单")
    public ResponseData unfreeze(@PathVariable Long menuId) {
        menuService.unfreeze(menuId);
        return SUCCESS_TIP;
    }

}
