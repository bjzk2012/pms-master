package cn.kcyf.pms.config;

import cn.kcyf.pms.core.converter.StringToDateConverter;
import cn.kcyf.pms.core.filter.VisitInterceptor;
import cn.kcyf.pms.core.view.ErrorView;
import cn.kcyf.commons.utils.FtpUtils;
import cn.kcyf.commons.utils.SmsUtils;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.util.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private List<String> NONE_PERMISSION_RES = Arrays.asList(new String[]{"/assets/**", "/gunsApi/**", "/login", "/global/sessionError", "/kaptcha", "/kaptcha_register", "/kaptcha_feedback", "/error", "/global/error"});

    @Bean
    public DefaultKaptcha defaultKaptcha(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.border.color", "105,179,90");
        properties.put("kaptcha.textproducer.font.color", "blue");
        properties.put("kaptcha.image.width", "200");
        properties.put("kaptcha.image.height", "75");
        properties.put("kaptcha.textproducer.font.size", "60");
        properties.put("kaptcha.textproducer.char.length", "4");
        properties.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    /**
     * 登录用
     */
    @Autowired
    private VisitInterceptor visitInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(visitInterceptor).excludePathPatterns(NONE_PERMISSION_RES).addPathPatterns("/**");
    }

    /**
     * 默认错误页面，返回json
     */
    @Bean("error")
    public ErrorView error() {
        return new ErrorView();
    }

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    /**
     * 增加字符串转日期的功能
     */

    @PostConstruct
    public void initEditableAvlidation() {

        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer)handlerAdapter.getWebBindingInitializer();
        if(initializer.getConversionService()!=null) {
            GenericConversionService genericConversionService = (GenericConversionService)initializer.getConversionService();
            genericConversionService.addConverter(new StringToDateConverter());

        }

    }

    @Bean
    public FtpUtils ftpUtils(){
        return new FtpUtils();
    }

    @Bean
    public SmsUtils smsUtils(){
        return new SmsUtils();
    }
}
