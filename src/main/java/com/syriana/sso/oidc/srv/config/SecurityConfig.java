//package com.syriana.sso.oidc.srv.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * @author syriana.zh
// * @date 2020/06/22
// * @EnableGlobalMethodSecurity(prePostEnabled = true)  controller有了这个配置似乎就不用这个了，一直要注释!!!!!
// */
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest().authenticated() //所有请求都需要通过认证
//                .and()
//                .csrf().disable(); //关跨域保护
//    }
//}
