package com.syriana.sso.oidc.srv.config.redis;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.cache.support.NullValue;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * 『@Cacheable』注解会将NullValue 转成Binary 存入redis，所以这里单独处理序列化/反序列化过程，解决此问题
 *
 * @Author: wangzhe
 * @Date: 2019-07-10
 */
public class MyGenericJackson2JsonRedisSerializer implements RedisSerializer<Object> {

    private final ObjectMapper mapper;
    private static final byte[]       EMPTY_ARRAY       = new byte[0];
    private static final byte[]       BINARY_NULL_VALUE = RedisSerializer.java().serialize(NullValue.INSTANCE);
    private              byte[]       NULL_VALUE;


    /**
     * Creates {@link org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer} and configures {@link com.fasterxml.jackson.databind.ObjectMapper} for default typing.
     */
    public MyGenericJackson2JsonRedisSerializer() {
        this((String) null);
    }

    /**
     * Creates {@link org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer} and configures {@link com.fasterxml.jackson.databind.ObjectMapper} for default typing using the
     * given {@literal name}. In case of an {@literal empty} or {@literal null} String the default
     * {@link com.fasterxml.jackson.annotation.JsonTypeInfo.Id#CLASS} will be used.
     *
     * @param classPropertyTypeName Name of the JSON property holding type information. Can be {@literal null}.
     */
    public MyGenericJackson2JsonRedisSerializer(@Nullable String classPropertyTypeName) {

        this(new ObjectMapper());

        // simply setting {@code mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)} does not help here since we need
        // the type hint embedded for deserialization using the default typing feature.
        mapper.registerModule(new SimpleModule().addSerializer(new NullValueSerializer(classPropertyTypeName)));

        if (StringUtils.hasText(classPropertyTypeName)) {
            mapper.enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.NON_FINAL, classPropertyTypeName);
        } else {
            mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        }

        try {
            //特殊处理
            NULL_VALUE = mapper.writeValueAsBytes(NullValue.INSTANCE);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setting a custom-configured {@link com.fasterxml.jackson.databind.ObjectMapper} is one way to take further control of the JSON serialization
     * process. For example, an extended {@link com.fasterxml.jackson.databind.ser.SerializerFactory} can be configured that provides custom serializers for
     * specific types.
     *
     * @param mapper must not be {@literal null}.
     */
    public MyGenericJackson2JsonRedisSerializer(ObjectMapper mapper) {

        Assert.notNull(mapper, "ObjectMapper must not be null!");
        this.mapper = mapper;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.serializer.RedisSerializer#serialize(java.lang.Object)
     */
    @Override
    public byte[] serialize(@Nullable Object source) throws SerializationException {

        if (source == null) {
            return EMPTY_ARRAY;
        }

        //特殊处理
        if (source instanceof NullValue) {
            return BINARY_NULL_VALUE;
        }

        try {
            return mapper.writeValueAsBytes(source);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Could not write JSON: " + e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.serializer.RedisSerializer#deserialize(byte[])
     */
    @Override
    public Object deserialize(@Nullable byte[] source) throws SerializationException {
        return deserialize(source, Object.class);
    }

    /**
     * @param source can be {@literal null}.
     * @param type   must not be {@literal null}.
     * @return {@literal null} for empty source.
     * @throws org.springframework.data.redis.serializer.SerializationException
     */
    @Nullable
    public <T> T deserialize(@Nullable byte[] source, Class<T> type) throws SerializationException {

        Assert.notNull(type,
                "Deserialization type must not be null! Pleaes provide Object.class to make use of Jackson2 default typing.");

        if (source == null || source.length == 0) {
            return null;
        }

        //特殊处理
        if (ObjectUtils.nullSafeEquals(source, BINARY_NULL_VALUE)) {
            source = NULL_VALUE;
        }

        try {
            return mapper.readValue(source, type);
        } catch (Exception ex) {
            throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    /**
     * {@link com.fasterxml.jackson.databind.ser.std.StdSerializer} adding class information required by default typing. This allows de-/serialization of
     * {@link org.springframework.cache.support.NullValue}.
     *
     * @author Christoph Strobl
     * @since 1.8
     */
    private class NullValueSerializer extends StdSerializer<NullValue> {

        private static final long   serialVersionUID = 1999052150548658808L;
        private final        String classIdentifier;

        /**
         * @param classIdentifier can be {@literal null} and will be defaulted to {@code @class}.
         */
        NullValueSerializer(@Nullable String classIdentifier) {

            super(NullValue.class);
            this.classIdentifier = StringUtils.hasText(classIdentifier) ? classIdentifier : "@class";
        }

        /*
         * (non-Javadoc)
         * @see com.fasterxml.jackson.databind.ser.std.StdSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
         */
        @Override
        public void serialize(NullValue value, JsonGenerator jgen, SerializerProvider provider)
                throws IOException {

            jgen.writeStartObject();
            jgen.writeStringField(classIdentifier, NullValue.class.getName());
            jgen.writeEndObject();
        }
    }
}
