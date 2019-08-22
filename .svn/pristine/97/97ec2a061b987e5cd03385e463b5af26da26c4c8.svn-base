package cn.kcyf.pms.modular.plugins;


import cn.kcyf.commons.utils.FtpUtils;
import cn.kcyf.tools.ueditor.ActionEnter;
import cn.kcyf.tools.ueditor.UeditorConfigKit;
import cn.kcyf.tools.ueditor.manager.FtpFileManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Ueditor编辑器
 */
@RequestMapping("/plugins/ueditor")
@Controller
public class UeditorController {

    @Autowired
    private FtpUtils ftpUtils;

    public void init() {
        UeditorConfigKit.setFileManager(new FtpFileManager(ftpUtils));
    }

    @ResponseBody
    @RequestMapping("/action")
    public JSONObject index(HttpServletRequest request) {
        init();
        return JSON.parseObject(ActionEnter.me().exec(request));
    }

}
