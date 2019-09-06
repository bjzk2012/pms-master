package cn.kcyf.pms.modular.business.controller;

import cn.kcyf.orm.jpa.criteria.Criteria;
import cn.kcyf.orm.jpa.criteria.Restrictions;
import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.core.enumerate.QuestionCategory;
import cn.kcyf.pms.core.enumerate.QuestionStatus;
import cn.kcyf.pms.core.log.BussinessLog;
import cn.kcyf.pms.core.model.modular.system.PasswordRequest;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.modular.business.entity.Question;
import cn.kcyf.pms.modular.business.service.QuestionService;
import cn.kcyf.pms.modular.system.entity.User;
import cn.kcyf.pms.modular.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("/center")
@Api(tags = "个人中心", description = "个人中心")
public class CenterController extends BasicController {

    @Autowired
    private QuestionController questionController;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/user_info")
    public String userInfo(Model model) {
        model.addAttribute("entity", userService.getOne(getUser().getId()));
        questionController.setModel(model);
        return "/modular/business/center/index.html";
    }

    @Autowired
    private UserService userService;

    @PostMapping("/user_info")
    @ResponseBody
    @BussinessLog("修改当前用户信息")
    @ApiOperation("修改当前用户信息")
    public ResponseData user_info(User user) {
        User dbuser = userService.getOne(getUser().getId());
        dbuser.setName(user.getName());
        dbuser.setSex(user.getSex());
        dbuser.setEmail(user.getEmail());
        dbuser.setBirthday(user.getBirthday());
        dbuser.setPhone(user.getPhone());
        dbuser.setAddress(user.getAddress());
        userService.update(dbuser);
        return SUCCESS_TIP;
    }

    @PostMapping("/user_avatar")
    @ResponseBody
    @BussinessLog("修改当前用户图像")
    @ApiOperation("修改当前用户图像")
    public ResponseData user_avatar(String avatar) {
        User dbuser = userService.getOne(getUser().getId());
        dbuser.setAvatar(avatar);
        userService.update(dbuser);
        return SUCCESS_TIP;
    }

    @PostMapping("/password")
    @ResponseBody
    @BussinessLog("设置当前用户新密码")
    @ApiOperation("设置当前用户新密码")
    public ResponseData password(@Valid PasswordRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseData.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        User dbuser = userService.getOne(getUser().getId());
        if (!userService.md5(request.getOldPassword(), dbuser.getSalt()).equals(dbuser.getPassword())) {
            return ResponseData.error("旧密码输入错误");
        }
        if (!request.getNewPassword().equals(request.getRePassword())) {
            return ResponseData.error("新密码和确认密码不一致");
        }
        dbuser.setPassword(userService.md5(request.getNewPassword(), dbuser.getSalt()));
        userService.update(dbuser);
        return SUCCESS_TIP;
    }

    /**
     * 我解决的
     * @param condition
     * @param projectId
     * @param category
     * @param submitTimeLimit
     * @param phone
     * @param status
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "/mydisposal/list")
    @ResponseBody
    public ResponseData mydisposalList(String condition, Long projectId, QuestionCategory category, String submitTimeLimit, String phone, QuestionStatus status, int page, int limit) {
        Criteria<Question> criteria = questionController.createCriteria(condition, projectId, category, submitTimeLimit, phone, status);
        criteria.add(Restrictions.eq("liable.id", getUser().getId()));
        return ResponseData.list(questionService.findList(criteria, PageRequest.of(page - 1, limit, new Sort(Sort.Direction.DESC, "time"))));
    }

    /**
     * 我发起的
     * @param condition
     * @param projectId
     * @param category
     * @param submitTimeLimit
     * @param phone
     * @param status
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "/myoriginate/list")
    @ResponseBody
    public ResponseData myoriginateList(String condition, Long projectId, QuestionCategory category, String submitTimeLimit, String phone, QuestionStatus status, int page, int limit) {
        Criteria<Question> criteria = questionController.createCriteria(condition, projectId, category, submitTimeLimit, phone, status);
        criteria.add(Restrictions.eq("sponsor", userService.getOne(getUser().getId()).getName()));
        return ResponseData.list(questionService.findList(criteria, PageRequest.of(page - 1, limit, new Sort(Sort.Direction.DESC, "time"))));
    }
}
