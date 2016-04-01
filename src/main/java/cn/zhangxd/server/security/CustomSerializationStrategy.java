package cn.zhangxd.server.security;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.security.oauth2.provider.token.store.redis.StandardStringSerializationStrategy;

/**
 * Serializes objects using {@link CustomSerializationStrategy}
 * Created by zhangxd on 16/3/30.
 */
public class CustomSerializationStrategy extends StandardStringSerializationStrategy {

    private static final Jackson2JsonRedisSerializer<Object> OBJECT_SERIALIZER = new Jackson2JsonRedisSerializer<>(Object.class);

    public CustomSerializationStrategy() {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        OBJECT_SERIALIZER.setObjectMapper(om);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T deserializeInternal(byte[] bytes, Class<T> clazz) {
        return (T) OBJECT_SERIALIZER.deserialize(bytes);
    }

    @Override
    protected byte[] serializeInternal(Object object) {
        return OBJECT_SERIALIZER.serialize(object);
    }

}
