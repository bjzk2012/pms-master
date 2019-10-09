
package cn.kcyf.pms.modular.system.controller;

import cn.kcyf.pms.core.constant.Constant;
import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.core.enumerate.LockStatus;
import cn.kcyf.pms.core.log.BussinessLog;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.core.model.modular.system.UserAddRequest;
import cn.kcyf.pms.core.model.modular.system.UserEditRequest;
import cn.kcyf.pms.modular.system.entity.User;
import cn.kcyf.pms.modular.system.service.DeptService;
import cn.kcyf.pms.modular.system.service.RoleService;
import cn.kcyf.pms.modular.system.service.UserService;
import cn.kcyf.commons.utils.ArrayUtils;
import cn.kcyf.commons.utils.DateUtils;
import cn.kcyf.orm.jpa.criteria.Criteria;
import cn.kcyf.orm.jpa.criteria.Restrictions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 系统管理员控制器
 *
 * @author Tom
 */
@Controller
@RequestMapping("/mgr")
@Api(tags = "管理员管理", description = "管理员管理")
public class UserMgrController extends BasicController {

    private final static String PREFIX = "/modular/system/user/";

    @Value("${shiro.password.saltlenth}")
    public int saltlenth;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DeptService deptService;

    @GetMapping("")
    @RequiresPermissions(value = "mgr")
    public String index() {
        return PREFIX + "user.html";
    }

    @GetMapping("/user_add")
    @RequiresPermissions(value = "mgr_add")
    public String add(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return PREFIX + "user_add.html";
    }

    @GetMapping("/user_edit")
    @RequiresPermissions(value = "mgr_edit")
    public String edit(Long userId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("roles", roleService.findAll());
        return PREFIX + "user_edit.html";
    }

    @GetMapping("/list")
    @ResponseBody
    @ApiOperation("查询管理员列表")
    @RequiresPermissions(value = "mgr_list")
    public ResponseData list(String name, String timeLimit, int page, int limit) {
        Criteria<User> criteria = new Criteria<User>();
        if (!StringUtils.isEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            criteria.add(Restrictions.gte("createTime", DateUtils.parse(split[0] + " 00:00:00", "yyyy-MM-dd HH:mm:ss")));
            criteria.add(Restrictions.lte("createTime", DateUtils.parse(split[1] + " 23:59:59", "yyyy-MM-dd HH:mm:ss")));
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.add(Restrictions.or(Restrictions.like("account", name), Restrictions.like("name", name), Restrictions.like("email", name), Restrictions.like("phone", name)));
        }

        return ResponseData.list(userService.findList(criteria, PageRequest.of(page - 1, limit)));
    }

    @PostMapping("/add")
    @ResponseBody
    @BussinessLog("新增管理员")
    @ApiOperation("新增管理员")
    @RequiresPermissions(value = "mgr_add")
    public ResponseData add(@Valid User user, @Valid UserAddRequest request, BindingResult bindingResult) {
        if (!request.getPassword().equals(request.getRePassword())) {
            return ResponseData.error("两次密码输入不一致");
        }
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        create(user);
        user.setStatus(LockStatus.UNLOCK);
        user.setAvatar(Constant.DEFAULT_HEAD);
        user.setSalt(RandomStringUtils.randomAlphabetic(saltlenth));
        user.setPassword(userService.md5(request.getPassword(), user.getSalt()));
        user.setKeyPassword(userService.md5(Constant.DEFAULT_PWD, user.getSalt()));
        user.setDept(deptService.getOne(request.getDeptId()));
        if (!StringUtils.isEmpty(request.getRoleId())) {
            user.setRoles(roleService.findByIdIn(ArrayUtils.convertStrArrayToLong(request.getRoleId().split(","))));
        }
        try {
            userService.create(user);
        } catch (DataIntegrityViolationException e) {
            return ResponseData.error("用户名重复");
        }
        return SUCCESS_TIP;
    }

    @PostMapping("/edit")
    @ResponseBody
    @BussinessLog("修改管理员")
    @ApiOperation("修改管理员")
    @RequiresPermissions(value = "mgr_edit")
    public ResponseData edit(@Valid User user, @Valid UserEditRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        User dbuser = userService.getOne(user.getId());
        update(dbuser);
        if (!StringUtils.isEmpty(user.getPassword())) {
            dbuser.setPassword(userService.md5(user.getPassword(), dbuser.getSalt()));
        }
        dbuser.setBirthday(user.getBirthday());
        dbuser.setEmail(user.getEmail());
        dbuser.setSex(user.getSex());
        dbuser.setDept(deptService.getOne(request.getDeptId()));
        if (!StringUtils.isEmpty(request.getRoleId())) {
            dbuser.setRoles(roleService.findByIdIn(ArrayUtils.convertStrArrayToLong(request.getRoleId().split(","))));
        } else {
            dbuser.setRoles(null);
        }
        dbuser.setPhone(user.getPhone());
        dbuser.setAddress(user.getAddress());
//        dbuser.setAvatar(user.getAvatar());
        dbuser.setName(user.getName());
        userService.update(dbuser);
        return SUCCESS_TIP;
    }

    @PostMapping("/delete/{userId}")
    @ResponseBody
    @BussinessLog("删除管理员")
    @ApiOperation("删除管理员")
    @RequiresPermissions(value = "mgr_delete")
    public ResponseData delete(@PathVariable Long userId) {
//        userService.delete(userId);
//        return SUCCESS_TIP;
        return ResponseData.error("暂不支持删除管理员，请使用锁定解锁功能");
    }

    @PostMapping("/reset/{userId}")
    @ResponseBody
    @BussinessLog("重置密码")
    @ApiOperation("重置密码")
    @RequiresPermissions(value = "mgr_reset")
    public ResponseData reset(@PathVariable Long userId) {
        User user = userService.getOne(userId);
        user.setPassword(userService.md5(Constant.DEFAULT_PWD, user.getSalt()));
        userService.update(user);
        return SUCCESS_TIP;
    }

    @PostMapping("/freeze/{userId}")
    @ResponseBody
    @BussinessLog("冻结管理员")
    @ApiOperation("冻结管理员")
    @RequiresPermissions(value = "mgr_freeze")
    public ResponseData freeze(@PathVariable Long userId) {
        User user = userService.getOne(userId);
        user.setStatus(LockStatus.LOCK);
        userService.update(user);
        return SUCCESS_TIP;
    }

    @PostMapping("/unfreeze/{userId}")
    @ResponseBody
    @BussinessLog("解冻管理员")
    @ApiOperation("解冻管理员")
    @RequiresPermissions(value = "mgr_freeze")
    public ResponseData unfreeze(@PathVariable Long userId) {
        User user = userService.getOne(userId);
        user.setStatus(LockStatus.UNLOCK);
        userService.update(user);
        return SUCCESS_TIP;
    }

    @GetMapping("/detail/{userId}")
    @ResponseBody
    @ApiOperation("查看管理员详情")
    @RequiresPermissions(value = "mgr_detail")
    public ResponseData detail(@PathVariable Long userId) {
        return ResponseData.success(userService.getOne(userId));
    }
}
