package cn.kcyf.pms.modular.system.controller;

import cn.kcyf.commons.utils.security.SecurityUtils;
import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.modular.system.service.UserService;
import io.swagger.annotations.Api;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Controller
@RequestMapping("/security")
@Api(tags = "安全工具", description = "安全工具")
@RequiresRoles(value = "administrator")
public class SecurityController extends BasicController {
    private static String PREFIX = "/modular/system/security";
    @Autowired
    private UserService userService;

    @GetMapping("")
    public String index() {
        return PREFIX + "/security.html";
    }

    @PostMapping("/salt")
    @ResponseBody
    public ResponseData salt() {
        return ResponseData.success(RandomStringUtils.randomAlphabetic(5));
    }

    @PostMapping("/md5")
    @ResponseBody
    public ResponseData md5(String password, String salt) {
        if (StringUtils.isEmpty(password)) {
            return ResponseData.error("待加密字符未输入!");
        }
        if (StringUtils.isEmpty(salt)) {
            return ResponseData.error("加密盐未输入!");
        }
        return ResponseData.success(userService.md5(password, salt));
    }
}
