
package cn.kcyf.pms.modular.system.controller;

import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.core.log.BussinessLog;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.modular.system.entity.Role;
import cn.kcyf.pms.core.enumerate.Status;
import cn.kcyf.pms.modular.system.service.MenuService;
import cn.kcyf.pms.modular.system.service.RoleService;
import cn.kcyf.commons.utils.ArrayUtils;
import cn.kcyf.orm.jpa.criteria.Criteria;
import cn.kcyf.orm.jpa.criteria.Restrictions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 角色控制器
 *
 * @author Tom
 */
@Controller
@RequestMapping("/role")
@Api(tags = "角色管理", description = "角色管理")
@RequiresRoles(value = "administrator")
public class RoleController extends BasicController {

    private static String PREFIX = "/modular/system/role";

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    @GetMapping("")
    @RequiresPermissions(value = "role")
    public String index() {
        return PREFIX + "/role.html";
    }

    @GetMapping(value = "/role_add")
    @RequiresPermissions(value = "role_add")
    public String roleAdd() {
        return PREFIX + "/role_add.html";
    }

    @GetMapping(value = "/role_edit")
    @RequiresPermissions(value = "role_edit")
    public String roleEdit(Long roleId, Model model) {
        model.addAttribute("roleId", roleId);
        return PREFIX + "/role_edit.html";
    }

    @GetMapping(value = "/list")
    @ResponseBody
    @ApiOperation("查询角色列表")
    @RequiresPermissions(value = "role_list")
    public ResponseData list(String roleName, int page, int limit) {
        Criteria<Role> criteria = new Criteria<Role>();
        if (!StringUtils.isEmpty(roleName)) {
            criteria.add(Restrictions.or(Restrictions.like("name", roleName), Restrictions.like("code", roleName), Restrictions.like("description", roleName)));
        }
        return ResponseData.list(roleService.findList(criteria, PageRequest.of(page - 1, limit)));
    }

    @PostMapping(value = "/add")
    @ResponseBody
    @BussinessLog("新增角色")
    @ApiOperation("新增角色")
    @RequiresPermissions(value = "role_add")
    public ResponseData add(@Valid Role role, String menuId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        if (StringUtils.isEmpty(menuId)){
            return ResponseData.error("菜单未选择");
        }
        create(role);
        role.setStatus(Status.ENABLE);
        role.setMenus(menuService.findByIdIn(ArrayUtils.convertStrArrayToLong(menuId.split(","))));
        roleService.create(role);
        return SUCCESS_TIP;
    }

    @PostMapping(value = "/edit")
    @ResponseBody
    @BussinessLog("修改角色")
    @ApiOperation("修改角色")
    @RequiresPermissions(value = "role_edit")
    public ResponseData edit(@Valid Role role, String menuId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        if (StringUtils.isEmpty(menuId)){
            return ResponseData.error("菜单未选择");
        }
        Role dbrole = roleService.getOne(role.getId());
        if (dbrole.getCode().equals("administrator") && !dbrole.getCode().equals(role.getCode())) {
            return ResponseData.error("超级管理员角色标识不允许被修改!");
        } else {
            dbrole.setCode(role.getCode());
        }
        update(dbrole);
        dbrole.setDescription(role.getDescription());
        dbrole.setSort(role.getSort());
        dbrole.setMenus(menuService.findByIdIn(ArrayUtils.convertStrArrayToLong(menuId.split(","))));
        roleService.update(dbrole);
        return SUCCESS_TIP;
    }

    @PostMapping(value = "/delete/{roleId}")
    @ResponseBody
    @BussinessLog("删除角色")
    @ApiOperation("删除角色")
    @RequiresPermissions(value = "role_delete")
    public ResponseData delete(@PathVariable Long roleId) {
        Role role = roleService.getOne(roleId);
        if (role.getCode().equals("administrator")) {
            return ResponseData.error("超级管理员角色不允许被删除!");
        }
        roleService.delete(roleId);
        return SUCCESS_TIP;
    }

    @PostMapping("/freeze/{roleId}")
    @ResponseBody
    @BussinessLog("禁用角色")
    @ApiOperation("禁用角色")
    @RequiresPermissions(value = "role_freeze")
    public ResponseData freeze(@PathVariable Long roleId) {
        Role role = roleService.getOne(roleId);
        if (role.getCode().equals("administrator")) {
            return ResponseData.error("超级管理员角色不允许被禁用!");
        }
        role.setStatus(Status.DISABLE);
        roleService.update(role);
        return SUCCESS_TIP;
    }

    @PostMapping("/unfreeze/{roleId}")
    @ResponseBody
    @BussinessLog("启用角色")
    @ApiOperation("启用角色")
    @RequiresPermissions(value = "role_freeze")
    public ResponseData unfreeze(@PathVariable Long roleId) {
        Role role = roleService.getOne(roleId);
        role.setStatus(Status.ENABLE);
        roleService.update(role);
        return SUCCESS_TIP;
    }

    @GetMapping(value = "/detail/{roleId}")
    @ResponseBody
    @RequiresPermissions(value = "role_detail")
    public ResponseData detail(@PathVariable Long roleId) {
        return ResponseData.success(roleService.getOne(roleId));
    }

}
