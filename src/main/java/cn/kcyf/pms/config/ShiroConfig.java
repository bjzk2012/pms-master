package cn.kcyf.pms.config;

import cn.kcyf.security.cache.SpringCacheManagerWrapper;
import cn.kcyf.security.credentials.RetryLimitHashedCredentialsMatcher;
import cn.kcyf.security.filter.DbFormAuthFilter;
import cn.kcyf.security.filter.UserFilter;
import cn.kcyf.security.realm.db.DbRealm;
import cn.kcyf.security.service.ShiroService;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Value("${shiro.password.algorithmName}")
    private String algorithmName;
    @Value("${shiro.password.hashIterations}")
    private Integer hashIterations;
    @Autowired
    private ShiroService shiroService;

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ClassPathResource resource = new ClassPathResource("ehcache/ehcache.xml");
        ehCacheManagerFactoryBean.setConfigLocation(resource);
        return ehCacheManagerFactoryBean;
    }

    @Bean
    public EhCacheCacheManager springCacheManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean){
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
        ehCacheCacheManager.setCacheManager(ehCacheManagerFactoryBean.getObject());
        return ehCacheCacheManager;
    }

    @Bean
    public SpringCacheManagerWrapper cacheManagerWrapper(EhCacheCacheManager springCacheManager){
        SpringCacheManagerWrapper cacheManagerWrapper = new SpringCacheManagerWrapper();
        cacheManagerWrapper.setCacheManager(springCacheManager);
        return cacheManagerWrapper;
    }


    @Bean
    public RetryLimitHashedCredentialsMatcher hashedCredentialsMatcher(SpringCacheManagerWrapper cacheManagerWrapper) {
        RetryLimitHashedCredentialsMatcher hashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher(cacheManagerWrapper); //md5加密1次
        hashedCredentialsMatcher.setHashAlgorithmName(algorithmName);
        hashedCredentialsMatcher.setHashIterations(hashIterations);
        return hashedCredentialsMatcher;
    }

    //将自己的验证方式加入容器
    @Bean
    public DbRealm dbRealm(SessionDAO sessionDAO, RetryLimitHashedCredentialsMatcher hashedCredentialsMatcher) {
        DbRealm dbRealm = new DbRealm();
        dbRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        dbRealm.setCachingEnabled(true);
        dbRealm.setAuthenticationCachingEnabled(false);
//        dbRealm.setAuthenticationCacheName("authenticationCache");
        dbRealm.setAuthorizationCachingEnabled(true);
        dbRealm.setAuthorizationCacheName("authorizationCache");
        dbRealm.setShiroService(shiroService);
        return dbRealm;
    }

    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator(){
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public SimpleCookie sessionIdCookie(){
        SimpleCookie cookie = new SimpleCookie("sid");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-1);
        return cookie;
    }

    @Bean
    public SimpleCookie rememberMeCookie(){
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(604800);
        return cookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager(SimpleCookie rememberMeCookie){
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        rememberMeManager.setCookie(rememberMeCookie);
        return rememberMeManager;
    }

    @Bean
    public EnterpriseCacheSessionDAO sessionDAO(JavaUuidSessionIdGenerator sessionIdGenerator){
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDAO.setSessionIdGenerator(sessionIdGenerator);
        return sessionDAO;
    }

    @Bean
    public DefaultWebSessionManager sessionManager(EnterpriseCacheSessionDAO sessionDAO, SimpleCookie sessionIdCookie){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(1800000L);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(false);
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionIdCookie(sessionIdCookie);
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }

    //权限管理，配置主要是Realm的管理认证
    @Bean
    public DefaultWebSecurityManager securityManager(DbRealm dbRealm, DefaultWebSessionManager sessionManager, SpringCacheManagerWrapper cacheManagerWrapper, CookieRememberMeManager rememberMeManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(dbRealm);
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(cacheManagerWrapper);
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultWebSecurityManager securityManager){
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        methodInvokingFactoryBean.setArguments(securityManager);
        return methodInvokingFactoryBean;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SessionDAO sessionDAO, DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = new HashMap<String, Filter>();
        DbFormAuthFilter authFilter = new DbFormAuthFilter();
        authFilter.setUsernameParam("username");
        authFilter.setPasswordParam("password");
        authFilter.setRememberMeParam("rememberMe");
        authFilter.setLoginUrl("/login");
        authFilter.setShiroService(shiroService);
        filterMap.put("authc", authFilter);
        UserFilter userFilter = new UserFilter();
        filterMap.put("user", userFilter);
        filterMap.put("logout", new LogoutFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        Map<String, String> map = new HashMap<String, String>();
        //对所有用户认证
        map.put("/login", "authc");
        map.put("/logout", "logout");
        map.put("/favicon.ico", "anon");
        map.put("/upload/**", "anon");
        map.put("/kaptcha", "anon");
        map.put("/kaptcha_register", "anon");
        map.put("/kaptcha_feedback", "anon");
        map.put("/assets/**", "anon");
        map.put("/v2/api-docs/", "anon");
        map.put("/question/feedback", "anon");
        map.put("/question/kaptcha", "anon");
        map.put("/**", "user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/global/unauthorized");
        return shiroFilterFactoryBean;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}