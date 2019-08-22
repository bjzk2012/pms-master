
package cn.kcyf.pms.modular.system.controller;

import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.security.util.AjaxUtils;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局的控制器
 *
 * @author Tom
 */
@Controller
@RequestMapping("/global")
@Api(hidden = true)
public class GlobalController extends BasicController {

    @RequestMapping(path = "/unauthorized")
    public String unauthorized(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (AjaxUtils.isForm(request)) {
            response.getWriter().write("{\"session-status\":\"unauth\"}");
        }
        return "/unauthorized.html";
    }

    @RequestMapping(path = "/error")
    public String error() {
        return "/500.html";
    }

    @RequestMapping(path = "/sessionError")
    public String sessionError(Model model) {
        model.addAttribute("tips", "session超时");
        return "/login.html";
    }
}
