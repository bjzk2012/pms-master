package cn.kcyf.pms.modular.system.controller;

import cn.kcyf.commons.utils.security.BASE64Decoder;
import cn.kcyf.commons.utils.security.BASE64Encoder;
import cn.kcyf.commons.utils.security.SecurityUtils;
import cn.kcyf.pms.core.controller.BasicController;
import cn.kcyf.pms.core.model.ResponseData;
import cn.kcyf.pms.modular.system.service.UserService;
import io.swagger.annotations.Api;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

@Controller
@RequestMapping("/security")
@Api(tags = "安全工具", description = "安全工具")
public class SecurityController extends BasicController {
    private static String PREFIX = "/modular/system/security";
    /**
     * 盐长度
     */
    @Value("${shiro.password.saltlenth}")
    public int saltlenth;

    /**
     * 加密循环次数
     */
    @Value("${shiro.password.hashIterations}")
    public int hashIterations;

    /**
     * DES秘钥
     */
    @Value("${shiro.password.deskey}")
    public String deskey;

    /**
     * DES向量
     */
    @Value("${shiro.password.desiv}")
    public String desiv;

    /**
     * 3DES秘钥
     */
    @Value("${shiro.password.des3key}")
    public String des3key;

    /**
     * 3DES向量
     */
    @Value("${shiro.password.des3iv}")
    public String des3iv;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("saltlenth", saltlenth);
        model.addAttribute("hashIterations", hashIterations);
        model.addAttribute("deskey", deskey);
        model.addAttribute("desiv", desiv);
        model.addAttribute("des3key", des3key);
        model.addAttribute("des3iv", des3iv);
        return PREFIX + "/security.html";
    }

    @PostMapping("/salt")
    @ResponseBody
    public ResponseData salt(Integer length) {
        return ResponseData.success(RandomStringUtils.randomAlphabetic(length));
    }

    @PostMapping("/md5")
    @ResponseBody
    public ResponseData md5(String target) {
        if (StringUtils.isEmpty(target)) {
            return ResponseData.error("目标字符串未输入!");
        }
        return ResponseData.success(SecurityUtils.md5Encoder(target));
    }

    @PostMapping("/saltmd5")
    @ResponseBody
    public ResponseData md5(String target, String salt, Integer iterations) {
        if (StringUtils.isEmpty(target)) {
            return ResponseData.error("目标字符串未输入!");
        }
        if (StringUtils.isEmpty(salt)) {
            return ResponseData.error("加密盐未输入!");
        }
        if (iterations == null){
            return ResponseData.error("循环次数未输入!");
        }
        return ResponseData.success(new SimpleHash("md5", target, ByteSource.Util.bytes(salt), iterations).toHex());
    }

    @PostMapping("/dese")
    @ResponseBody
    public ResponseData dese(String target, String key, String iv) {
        if (StringUtils.isEmpty(target)) {
            return ResponseData.error("目标字符串未输入!");
        }
        if (StringUtils.isEmpty(key)) {
            return ResponseData.error("秘钥不能为空!");
        }
        if (iv == null){
            return ResponseData.error("向量不能为空!");
        }
        return ResponseData.success(SecurityUtils.desEncode(target, key, iv));
    }

    @PostMapping("/desed")
    @ResponseBody
    public ResponseData desd(String target, String key, String iv) {
        if (StringUtils.isEmpty(target)) {
            return ResponseData.error("目标字符串未输入!");
        }
        if (StringUtils.isEmpty(key)) {
            return ResponseData.error("秘钥不能为空!");
        }
        if (iv == null){
            return ResponseData.error("向量不能为空!");
        }
        return ResponseData.success(SecurityUtils.desDecode(target, key, iv));
    }

    @PostMapping("/3dese")
    @ResponseBody
    public ResponseData des3e(String target, String key, String iv) {
        if (StringUtils.isEmpty(target)) {
            return ResponseData.error("目标字符串未输入!");
        }
        if (StringUtils.isEmpty(key)) {
            return ResponseData.error("秘钥不能为空!");
        }
        if (iv == null){
            return ResponseData.error("向量不能为空!");
        }
        return ResponseData.success(SecurityUtils.des3Encode(target, key, iv));
    }

    @PostMapping("/3desd")
    @ResponseBody
    public ResponseData des3d(String target, String key, String iv) {
        if (StringUtils.isEmpty(target)) {
            return ResponseData.error("目标字符串未输入!");
        }
        if (StringUtils.isEmpty(key)) {
            return ResponseData.error("秘钥不能为空!");
        }
        if (iv == null){
            return ResponseData.error("向量不能为空!");
        }
        return ResponseData.success(SecurityUtils.des3Decode(target, key, iv));
    }

    @PostMapping("/base64e")
    @ResponseBody
    public ResponseData base64e(String target) {
        if (StringUtils.isEmpty(target)) {
            return ResponseData.error("目标字符串未输入!");
        }
        return ResponseData.success(new BASE64Encoder().encode(target.getBytes()));
    }

    @PostMapping("/base64d")
    @ResponseBody
    public ResponseData base64d(String target) throws IOException {
        if (StringUtils.isEmpty(target)) {
            return ResponseData.error("目标字符串未输入!");
        }
        return ResponseData.success(new String(new BASE64Decoder().decodeBuffer(target), "UTF-8"));
    }



    @PostMapping("/urle")
    @ResponseBody
    public ResponseData urle(String target) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(target)) {
            return ResponseData.error("目标字符串未输入!");
        }
        return ResponseData.success(URLEncoder.encode(target, "UTF-8"));
    }

    @PostMapping("/urld")
    @ResponseBody
    public ResponseData urld(String target) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(target)) {
            return ResponseData.error("目标字符串未输入!");
        }
        return ResponseData.success(URLDecoder.decode(target, "UTF-8"));
    }
}
