package cn.kcyf.pms.modular.business.controller;

import cn.kcyf.pms.core.enumerate.LockStatus;
import cn.kcyf.pms.core.enumerate.LogType;
import cn.kcyf.pms.core.enumerate.Status;
import cn.kcyf.pms.core.log.BussinessLog;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.modular.business.entity.Key;
import cn.kcyf.pms.modular.business.entity.Project;
import cn.kcyf.pms.modular.business.service.KeyService;
import cn.kcyf.pms.modular.business.service.ProjectService;
import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.modular.system.entity.User;
import cn.kcyf.pms.modular.system.service.UserService;
import cn.kcyf.orm.jpa.criteria.Criteria;
import cn.kcyf.orm.jpa.criteria.Restrictions;
import cn.kcyf.security.domain.ShiroUser;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 口令控制器
 *
 * @author Tom
 */
@Controller
@RequestMapping("/key")
@Api(tags = "账号管理", description = "账号管理")
public class KeyController extends BasicController {
    private static String PREFIX = "/modular/business/key";

    @Autowired
    private KeyService keyService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    private void setProjects(Model model) {
        Criteria<Project> criteria = new Criteria<Project>();
        criteria.add(Restrictions.eq("status", Status.ENABLE));
        model.addAttribute("projects", projectService.findList(criteria));
    }

    private void setUsers(Model model) {
        Criteria<User> criteria = new Criteria<User>();
        criteria.add(Restrictions.eq("status", LockStatus.UNLOCK));
        model.addAttribute("users", userService.findList(criteria));
    }

    @GetMapping("")
    public String index() {
        return PREFIX + "/key.html";
    }

    @GetMapping(value = "/key_add")
    public String keyAdd(Model model) {
        setProjects(model);
        setUsers(model);
        return PREFIX + "/key_add.html";
    }

    @GetMapping(value = "/key_edit")
    public String keyEdit(Long keyId, Model model) {
        model.addAttribute("keyId", keyId);
        setProjects(model);
        setUsers(model);
        return PREFIX + "/key_edit.html";
    }

    @GetMapping(value = "/key_pass")
    public String keyPass() {
        return PREFIX + "/key_pass.html";
    }

    @GetMapping(value = "/list")
    @ResponseBody
    public ResponseData list(String condition, int page, int limit) {
        Criteria<Key> criteria = new Criteria<Key>();
        if (!StringUtils.isEmpty(condition)) {
            criteria.add(Restrictions.or(Restrictions.like("name", condition),
                    Restrictions.like("useway", condition),
                    Restrictions.like("project.name", condition),
                    Restrictions.like("account", condition),
                    Restrictions.like("manager.name", condition),
                    Restrictions.like("backupManager.name", condition)));
        }
        return ResponseData.list(keyService.findList(criteria, PageRequest.of(page - 1, limit)));
    }

    @PostMapping(value = "/add")
    @ResponseBody
    @BussinessLog("新增账号")
    public ResponseData add(@Valid Key key,
                            @NotBlank(message = "账号密码不能为空") String password,
                            @NotBlank(message = "请选择项目") Long projectId,
                            @NotBlank(message = "请选择管理人") Long managerId,
                            Long backupManagerId,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        create(key);
        key.setProject(projectService.getOne(projectId));
        key.setManager(userService.getOne(managerId));
        if (backupManagerId != null) {
            key.setBackupManager(userService.getOne(backupManagerId));
        }
        keyService.create(key);
        return SUCCESS_TIP;
    }

    @PostMapping(value = "/edit")
    @ResponseBody
    @BussinessLog("修改账号")
    public ResponseData edit(@Valid Key key,
                             @NotBlank(message = "请选择项目") Long projectId,
                             @NotBlank(message = "请选择管理人") Long managerId,
                             Long backupManagerId,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Key dbkey = keyService.getOne(key.getId());
        update(dbkey);
        dbkey.setProject(projectService.getOne(projectId));
        dbkey.setName(key.getName());
        dbkey.setUseway(key.getUseway());
        dbkey.setAccount(key.getAccount());
        if (!StringUtils.isEmpty(key.getPassword())) {
            dbkey.setPassword(key.getPassword());
        }
        dbkey.setDescription(key.getDescription());
        dbkey.setManager(userService.getOne(managerId));
        if (backupManagerId != null) {
            dbkey.setBackupManager(userService.getOne(backupManagerId));
        }
        keyService.update(dbkey);
        return SUCCESS_TIP;
    }

    @PostMapping(value = "/delete/{keyId}")
    @ResponseBody
    @BussinessLog("删除账号")
    public ResponseData delete(@PathVariable Long keyId) {
        keyService.delete(keyId);
        return SUCCESS_TIP;
    }

    @PostMapping(value = "/key_pass")
    @ResponseBody
    @BussinessLog("修改口令")
    public ResponseData key_pass(
            @NotBlank(message = "旧密码不能为空")
            @Size(min = 6, max = 12, message = "旧密码必须6到12位")
            @Pattern(regexp = "[\\S]+", message = "旧密码不能出现空格") String oldPassword,
            @NotBlank(message = "新密码不能为空")
            @Size(min = 6, max = 12, message = "新密码必须6到12位")
            @Pattern(regexp = "[\\S]+", message = "新密码不能出现空格")
                    String newPassword,
            @NotBlank(message = "确认密码不能为空")
            @Size(min = 6, max = 12, message = "确认密码必须6到12位")
            @Pattern(regexp = "[\\S]+", message = "确认密码不能出现空格")
                    String rePassword) {
        User dbuser = userService.getOne(getUser().getId());
        if (!userService.md5(oldPassword, dbuser.getSalt()).equals(dbuser.getKeyPassword())) {
            return ResponseData.error("旧口令输入错误");
        }
        if (!newPassword.equals(rePassword)) {
            return ResponseData.error("新密码和确认密码不一致");
        }
        dbuser.setKeyPassword(userService.md5(newPassword, dbuser.getSalt()));
        userService.update(dbuser);
        return SUCCESS_TIP;
    }

    @GetMapping(value = "/detail/{keyId}")
    @ResponseBody
    public ResponseData detail(@PathVariable Long keyId) {
        return ResponseData.success(keyService.getOne(keyId));
    }

    @PostMapping(value = "/password/{keyId}")
    @ResponseBody
    @BussinessLog(value = "查看密码", type = LogType.KEY)
    public ResponseData password(@PathVariable Long keyId, String password) {
        Key key = keyService.getOne(keyId);
        User manager = key.getManager();
        User backupManager = key.getBackupManager();
        ShiroUser shiroUser = getUser();
        if (shiroUser.getId().equals(manager.getId())) {
            if (userService.md5(password, manager.getSalt()).equals(manager.getKeyPassword())) {
                return ResponseData.success(key.getPassword());
            } else {
                return ResponseData.error("密码输入错误");
            }
        } else if (backupManager != null && shiroUser.getId().equals(backupManager.getId())) {
            if (userService.md5(password, backupManager.getSalt()).equals(backupManager.getKeyPassword())) {
                return ResponseData.success(key.getPassword());
            } else {
                return ResponseData.error("密码输入错误");
            }
        } else {
            return ResponseData.error("您无权限查看该账号密码");
        }
    }
}
