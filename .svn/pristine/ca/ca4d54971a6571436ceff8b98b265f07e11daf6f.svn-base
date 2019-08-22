package cn.kcyf.pms.core.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import static cn.kcyf.pms.core.constant.Constant.*;

@Controller
public class KaptchaController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @RequestMapping("/kaptcha")
    public void defaultKaptcha(String cmd, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            String kaptchaKey = KAPTCHA_SESSION_KEY;
            if (!StringUtils.isEmpty(cmd)) {
                if ("register".equals(cmd)) {
                    kaptchaKey = KAPTCHA_REGISTER_SESSION_KEY;
                }
                if ("question".equals(cmd)) {
                    kaptchaKey = KAPTCHA_QUESTION_SESSION_KEY;
                }
            }
            String createText = defaultKaptcha.createText();
            request.getSession().setAttribute(kaptchaKey, createText);
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        byte[] captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
