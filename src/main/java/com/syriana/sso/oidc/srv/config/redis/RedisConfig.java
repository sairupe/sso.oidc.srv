package com.syriana.sso.oidc.srv.config.redis;

import com.alibaba.fastjson.parser.ParserConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author: wangzhe
 * @Date: 2019-07-09
 */
@Slf4j
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        // fastjson在2017年3月爆出了在1.2.24以及之前版本存在远程代码执行高危安全漏洞。
        // 所以要使用ParserConfig.getGlobalInstance().addAccept("com.xiaolyuh.");指定序列化白名单
        ParserConfig.getGlobalInstance().addAccept("com.syriana.sso.oidc.api.");

        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());
        redisTemplate.setDefaultSerializer(valueSerializer());

        redisTemplate.afterPropertiesSet();

        log.info("自定义RedisTemplate加载完成");
        return redisTemplate;
    }


    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

//    private RedisSerializer<Object> valueSerializer() {
//        return new MyGenericJackson2JsonRedisSerializer();
//    }

    private RedisSerializer<Object> valueSerializer() {
        return new FastJsonRedisSerializer<>(Object.class);
    }
}