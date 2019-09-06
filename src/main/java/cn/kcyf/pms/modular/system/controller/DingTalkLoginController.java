package cn.kcyf.pms.modular.system.controller;

import cn.kcyf.orm.jpa.criteria.Criteria;
import cn.kcyf.orm.jpa.criteria.Restrictions;
import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.core.util.DingTalkUtil;
import cn.kcyf.pms.modular.system.entity.User;
import cn.kcyf.pms.modular.system.service.UserService;
import cn.kcyf.security.domain.OpenIdToken;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
public class DingTalkLoginController extends BasicController {
    private static String PREFIX = "/thirdpart/dingtalk/";
    @Autowired
    private DingTalkUtil dingTalkUtil;
    @Autowired
    private UserService userService;

    @GetMapping("/dingtalk_login")
    public String dingtalkReturn(String code) {
        JSONObject result = dingTalkUtil.getUserInfoByCode(code);
        if (result.getInteger("errcode") == 0) {
            JSONObject userInfo = result.getJSONObject("user_info");
            String dingtalkNick = userInfo.getString("nick");
            String dingtalkOpenId = userInfo.getString("openid");
            String dingtalkUnionid = userInfo.getString("unionid");
            Criteria<User> criteria = new Criteria<User>();
            criteria.add(Restrictions.eq("dingtalkOpenId", dingtalkOpenId));
            User user = userService.getOne(criteria);
            if (user != null) {
                OpenIdToken token = new OpenIdToken(dingtalkOpenId);
                SecurityUtils.getSubject().login(token);
            } else {
                // 如果当前是已登录状态，则只修改钉钉绑定账号
                if (SecurityUtils.getSubject().isAuthenticated()) {
                    User dbuser = userService.getOne(getUser().getId());
                    dbuser.setDingtalkNick(dingtalkNick);
                    dbuser.setDingtalkOpenId(dingtalkOpenId);
                    dbuser.setDingtalkUnionid(dingtalkUnionid);
                    userService.update(dbuser);
                } else {
                    // 未绑定用户，跳转至注册用户选择页面，带上openId
                    try {
                        return REDIRECT + "/dingtalk_bind?dingtalkNick=" + URLEncoder.encode(dingtalkNick, "UTF-8") + "&dingtalkOpenId=" + dingtalkOpenId + "&dingtalkUnionid=" + dingtalkUnionid;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            // 钉钉登录错误跳转到错误页面
            return REDIRECT + "/global/unauthorized";
        }
        return REDIRECT + "/";
    }

    @GetMapping("/dingtalk_bind")
    public String dingtalkBind(Model model, String dingtalkNick, String dingtalkOpenId, String dingtalkUnionid) {
        model.addAttribute("dingtalkNick", dingtalkNick);
        model.addAttribute("dingtalkOpenId", dingtalkOpenId);
        model.addAttribute("dingtalkUnionid", dingtalkUnionid);
        return PREFIX + "dingtalk_bind.html";
    }

    @PostMapping("/dingtalk_bind")
    public String dingtalkBind(Model model, String username, String password, String dingtalkNick, String dingtalkOpenId, String dingtalkUnionid) {
        String error = null;
        Criteria<User> criteria = new Criteria<>();
        criteria.add(Restrictions.eq("account", username));
        User user = userService.getOne(criteria);
        if (!StringUtils.isEmpty(dingtalkOpenId)) {
            if (user == null) {
                error = "用户不存在";
            } else if (!userService.md5(password, user.getSalt()).equals(user.getPassword())) {
                error = "输入的密码错误";
            } else {
                user.setDingtalkNick(dingtalkNick);
                user.setDingtalkOpenId(dingtalkOpenId);
                user.setDingtalkUnionid(dingtalkUnionid);
                userService.update(user);
                OpenIdToken token = new OpenIdToken(dingtalkOpenId);
                SecurityUtils.getSubject().login(token);
                return REDIRECT + "/";
            }
        }
        model.addAttribute("dingtalkNick", dingtalkNick);
        model.addAttribute("dingtalkOpenId", dingtalkOpenId);
        model.addAttribute("dingtalkUnionid", dingtalkUnionid);
        model.addAttribute("tips", error);
        return PREFIX + "dingtalk_bind.html";
    }
}
