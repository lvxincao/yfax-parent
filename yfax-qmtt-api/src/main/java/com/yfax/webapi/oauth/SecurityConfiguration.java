package com.yfax.webapi.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

import com.yfax.webapi.GlobalUtils;
import com.yfax.webapi.oauth.service.CfdbUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    //自定义UserDetailsService注入
    @Autowired
    private CfdbUserDetailsService userDetailsService;
    
    //配置匹配用户时密码规则
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return null;
//    }
    
    //配置全局设置
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //设置UserDetailsService以及密码规则
        auth.userDetailsService(userDetailsService);
    }
    
    	//排除/hello路径拦截
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/hello"
        		, GlobalUtils.PROJECT_QMTT + "/doRegister"
        		, GlobalUtils.PROJECT_QMTT + "/queryInitConfig"
        		, GlobalUtils.PROJECT_QMTT + "/faq"
        		, GlobalUtils.PROJECT_QMTT + "/invite"
        		, GlobalUtils.PROJECT_QMTT + "/register"
        		, GlobalUtils.PROJECT_QMTT + "/download"
        		, GlobalUtils.PROJECT_QMTT + "/doLogin"
        		, GlobalUtils.PROJECT_QMTT + "/doSms"
        		, GlobalUtils.PROJECT_QMTT + "/doDownloadUrl"
        		, GlobalUtils.PROJECT_QMTT + "/doResetPwd"
        		, GlobalUtils.PROJECT_QMTT + "/doRedirectUrl"
        		, GlobalUtils.PROJECT_QMTT + "/queryRank"
        		, GlobalUtils.PROJECT_QMTT + "/queryRankGold"
        		, GlobalUtils.PROJECT_QMTT + "/queryAdvList");
    }
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    //开启全局方法拦截
    @EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
    public static class GlobalSecurityConfiguration extends GlobalMethodSecurityConfiguration {
        @Override
        protected MethodSecurityExpressionHandler createExpressionHandler() {
            return new OAuth2MethodSecurityExpressionHandler();
        }
    }
}