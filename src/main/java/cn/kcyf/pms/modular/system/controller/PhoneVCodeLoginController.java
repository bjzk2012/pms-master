package cn.kcyf.pms.modular.system.controller;

import cn.kcyf.commons.utils.RandomUtils;
import cn.kcyf.pms.core.constant.Constant;
import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.core.enumerate.AuthType;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.modular.system.service.AuthCodeService;
import cn.kcyf.security.domain.PhoneVCodeToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Date;

@Controller
public class PhoneVCodeLoginController extends BasicController {

    @Autowired
    private AuthCodeService authCodeService;

    @PostMapping("/phonevcode_send")
    @ResponseBody
    public ResponseData send(HttpSession session, String phone, String kaptcha) {
        if (StringUtils.isEmpty(phone)){
            return ResponseData.error("手机号码不能为空");
        }
        if (StringUtils.isEmpty(kaptcha)){
            return ResponseData.error("图片验证码不能为空");
        }
        Object kaptcha_session = session.getAttribute(Constant.KAPTCHA_PHONEVCODE_SESSION_KEY);
        if (!kaptcha.equals(kaptcha_session)){
            return ResponseData.error("图片验证码不正确");
        }
        authCodeService.send(phone, AuthType.PHONEVCODELOGIN_AUTHCODE, null, RandomUtils.generateNumString(6));
        return ResponseData.success();
    }

    @PostMapping("/phonevcode_login")
    public String login(HttpSession session, Model model, String phone, String kaptcha, String vcode) {
        Object kaptcha_session = session.getAttribute(Constant.KAPTCHA_PHONEVCODE_SESSION_KEY);
        if (!kaptcha.equals(kaptcha_session)){
            return REDIRECT + "/login?tab=3&tips=" + URLEncoder.encode("图片验证码不正确");
        }
        if (StringUtils.isEmpty(phone)) {
            return REDIRECT + "/login?tab=3&tips=" + URLEncoder.encode("手机号码不能为空");
        }
        if (StringUtils.isEmpty(vcode)) {
            return REDIRECT + "/login?tab=3&tips=" + URLEncoder.encode("手机验证码不能为空");
        }
        PhoneVCodeToken token = new PhoneVCodeToken(phone, vcode);
        try {
            SecurityUtils.getSubject().login(token);
        } catch (Exception e) {
            return REDIRECT + "/login?tab=3&tips=" + URLEncoder.encode("手机验证码登录失败");
        }
        return REDIRECT + "/";
    }
}
