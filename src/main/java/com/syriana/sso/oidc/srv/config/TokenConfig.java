package com.syriana.sso.oidc.srv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author syriana.zh
 * @date 2020/06/24
 */
@Configuration
public class TokenConfig {

    private String SIGNING_KEY = "SALT";

//    // 内存TOKEN
//    @Bean
//    public TokenStore tokenStore(){
//        return new InMemoryTokenStore();
//    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }
}
