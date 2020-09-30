package com.syriana.sso.oidc.srv.config.redis;

/**
 * @author syriana.zh
 * @date 2020/07/03
 */
public class RedisKeyGenerator {

    private static String KEY_PREFIX = "sso:oidc:api:";
    public static final String KEY_ACCESS_CODE = KEY_PREFIX + "accessCode:";
    public static final String KEY_ACCESS_TOEKN = KEY_PREFIX + "accessToken:";

    public static String buildAccessCodeKey(String accessCode) {
        return KEY_PREFIX + KEY_ACCESS_CODE + accessCode;
    }
}
