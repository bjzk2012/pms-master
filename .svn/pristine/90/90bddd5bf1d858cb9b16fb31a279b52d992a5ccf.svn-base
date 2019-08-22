package cn.kcyf.pms.modular.system.controller;

import cn.kcyf.orm.jpa.criteria.Criteria;
import cn.kcyf.orm.jpa.criteria.Restrictions;
import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.core.enumerate.LockStatus;
import cn.kcyf.pms.core.enumerate.NoticeType;
import cn.kcyf.pms.core.enumerate.QuestionCategory;
import cn.kcyf.pms.core.enumerate.Status;
import cn.kcyf.pms.core.log.BussinessLog;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.modular.business.entity.Mode;
import cn.kcyf.pms.modular.system.entity.Notice;
import cn.kcyf.pms.modular.system.entity.Role;
import cn.kcyf.pms.modular.system.entity.User;
import cn.kcyf.pms.modular.system.service.NoticeService;
import cn.kcyf.pms.modular.system.service.RoleService;
import cn.kcyf.pms.modular.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/notice")
@Api(tags = "站内信管理", description = "站内信管理")
public class NoticeController extends BasicController {
    private String PREFIX = "/modular/system/notice/";

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @GetMapping("")
    public String index() {
        return PREFIX + "notice.html";
    }

    private void setTypes(Model model){
        Map<String, String> types = new HashMap<String, String>();
        for (NoticeType type : NoticeType.values()) {
            types.put(type.name(), type.getMessage());
        }
        model.addAttribute("types", types);
    }

    private void setUsers(Model model) {
        Criteria<User> criteria = new Criteria<User>();
        criteria.add(Restrictions.eq("status", LockStatus.UNLOCK));
        criteria.add(Restrictions.ne("roles.code", "administrator"));
        model.addAttribute("users", userService.findList(criteria));
    }

    private void setRoles(Model model) {
        Criteria<Role> criteria = new Criteria<Role>();
        criteria.add(Restrictions.eq("status", Status.ENABLE));
        criteria.add(Restrictions.ne("code", "administrator"));
        model.addAttribute("roles",roleService.findList(criteria));
    }

    @GetMapping("/notice_add")
    public String noticeAdd(Model model) {
        setTypes(model);
        setUsers(model);
        setRoles(model);
        return PREFIX + "notice_add.html";
    }

    @GetMapping(value = "/list")
    @ResponseBody
    @ApiOperation("查询内容模板树形结构")
    public ResponseData list(String name) {
        Criteria<Notice> criteria = new Criteria<Notice>();
        if (!StringUtils.isEmpty(name)) {
            criteria.add(Restrictions.or(Restrictions.like("title", name), Restrictions.like("content", name)));
        }
        return ResponseData.list(noticeService.findList(criteria));
    }

    @PostMapping(value = "/add")
    @ResponseBody
    @BussinessLog("新增内容模板")
    @ApiOperation("新增内容模板")
    public ResponseData add(@Valid Notice notice, int totype, String userId, String roleId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        create(notice);
        noticeService.create(notice, totype, userId, roleId);
        return SUCCESS_TIP;
    }

    @GetMapping(value = "/detail/{noticeId}")
    @ResponseBody
    @ApiOperation("查看内容模板详情")
    public ResponseData detail(@PathVariable("noticeId") Long noticeId) {
        return ResponseData.success(noticeService.getOne(noticeId));
    }
}
