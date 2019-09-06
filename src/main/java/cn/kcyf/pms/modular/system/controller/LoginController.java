
package cn.kcyf.pms.modular.system.controller;

import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.core.model.MenuNode;
import cn.kcyf.pms.core.model.MenuNodeComparator;
import cn.kcyf.pms.modular.system.service.UserService;
import cn.kcyf.security.util.KaptchaException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

/**
 * 登录控制器
 *
 * @author Tom
 */
@Controller
@Api(tags = "登录相关", description = "登录相关")
public class LoginController extends BasicController {
    @Value("${dingtalk.login_url}")
    private String login_url;
    @Value("${dingtalk.ldomain}")
    private String ldomain;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    @ApiOperation("跳转到首页")
    public String index(Model model) {
        if (getUser().getId() == null){
            return REDIRECT + "/sightseer/index";
        }
        List<MenuNode> menus = userService.getUserMenus(getUser().getId());
        Collections.sort(menus, new MenuNodeComparator());
        model.addAttribute("menus", menus);
        return "/index.html";
    }
    @GetMapping("/test")
    public String test(Model model) {
        return "/test.html";
    }

    @RequestMapping(value = "login")
    @ApiOperation("登录")
    public String login(HttpServletRequest request, Model modelMap) {
        String error = null;
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.isAuthenticated()){
            return REDIRECT + "/";
        }
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        if (KaptchaException.class.getName().equals(exceptionClassName)) {
            error = "验证码不正确";
        } else if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户不存在或密码错误";
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户不存在或密码错误";
        } else if (ExcessiveAttemptsException.class.getName().equals(exceptionClassName)){
            error = "密码错误次数已达上限，请10分钟后再试";
        } else if (exceptionClassName != null) {
            error = "未知错误：" + exceptionClassName;
        }
        if (StringUtils.isEmpty(error) && !StringUtils.isEmpty(request.getParameter("tips"))){
            error = request.getParameter("tips");
        }
        modelMap.addAttribute("login_url", login_url);
        modelMap.addAttribute("ldomain", ldomain);
        modelMap.addAttribute("tips", error);
        return "/login.html";
    }
}
