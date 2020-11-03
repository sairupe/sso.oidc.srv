package com.syriana.sso.oidc.srv.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author syriana.zh
 * @date 2020/06/22
 */
@Configuration
@EnableResourceServer
public class ResourceConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    TokenStore tokenStore;

//    @Bean
//    public ResourceServerTokenServices tokenServices() {
//        final RemoteTokenServices tokenServices = new RemoteTokenServices();
//        //设置授权服务器check_token端点完整地址
//        tokenServices.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
//        //设置客户端id与secret，注意：client_secret值不能使用passwordEncoder加密！
//        tokenServices.setClientId("client-a");
//        tokenServices.setClientSecret("client-a-secret");
//        return tokenServices;
//    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //设置创建session策略
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        //所有请求必须授权
        http.authorizeRequests()
                .antMatchers("/swagger-ui.html").permitAll() //swagger放开
                .antMatchers("/swagger-resources/**").permitAll() //swagger放开
                .antMatchers("/webjars/**").permitAll() //swagger放开
                .antMatchers("/v2/**").permitAll() //swagger放开
                .antMatchers("/redis/**").permitAll() //swagger放开
                .antMatchers("/rpc/**").permitAll() //swagger放开
                .anyRequest().access("#oauth2.hasAnyScope('read_user_info')") //redis放开
//                .anyRequest().permitAll()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("resource1")// 资源ID
//                .tokenServices(tokenServices()) // 验证令牌的服务(远程调用)
                .tokenStore(tokenStore) // 定义好SECRET_KEY后，本地解开
                .stateless(true);
    }
}
