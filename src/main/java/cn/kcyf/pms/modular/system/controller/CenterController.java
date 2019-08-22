package cn.kcyf.pms.modular.system.controller;

import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.core.log.BussinessLog;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.modular.system.entity.User;
import cn.kcyf.pms.modular.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Controller
@RequestMapping("/center")
@Api(tags = "个人中心", description = "个人中心")
public class CenterController extends BasicController {

    @GetMapping("/user_info")
    public String userInfo(Model model) {
        model.addAttribute("entity", getUser().getDetail());
        return "/modular/frame/user_info.html";
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
    @ApiImplicitParams({
            @ApiImplicitParam(value = "旧密码", name = "oldPassword", required = true, format = "\\S{6-12}"),
            @ApiImplicitParam(value = "新密码", name = "newPassword", required = true, format = "\\S{6-12}"),
            @ApiImplicitParam(value = "确认密码", name = "rePassword", required = true, format = "\\S{6-12}")
    })
    public ResponseData password(
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
        if (!userService.md5(oldPassword, dbuser.getSalt()).equals(dbuser.getPassword())) {
            return ResponseData.error("旧密码输入错误");
        }
        if (!newPassword.equals(rePassword)) {
            return ResponseData.error("新密码和确认密码不一致");
        }
        dbuser.setPassword(userService.md5(newPassword, dbuser.getSalt()));
        userService.update(dbuser);
        return SUCCESS_TIP;
    }
}
