package com.syriana.sso.oidc.srv.controller;

import com.syriana.sso.oidc.srv.response.common.RestResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author syriana.zh
 * @date 2020/09/30
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @PostMapping("/keys")
    @ApiOperation(value = "keys", nickname = "keys")
    public RestResult<Set<Object>> keys() {
        Set<Object> keys = redisTemplate.keys("*");
        return RestResult.suc("查询成功", keys);
    }

    @PostMapping("/set")
    @ApiOperation(value = "set", nickname = "set")
    public RestResult<Integer> set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return RestResult.suc("查询成功", 1);
    }

    @PostMapping("/get")
    @ApiOperation(value = "get", nickname = "get")
    public RestResult<Object> get(String key) {
        Object o = redisTemplate.opsForValue().get(key);
        return RestResult.suc("查询成功", o);
    }

    @PostMapping("/del")
    @ApiOperation(value = "del", nickname = "del")
    public RestResult<Boolean> del(String key) {
        Boolean delete = redisTemplate.delete(key);
        return RestResult.suc("查询成功", delete);
    }

    @PostMapping("/delAll")
    @ApiOperation(value = "delAll", nickname = "delAll")
    public RestResult<Boolean> delAll(String key) {
        Set keys = redisTemplate.keys(key + "*");
        Long result = redisTemplate.delete(keys);
        return RestResult.suc("查询成功", result);
    }
}
