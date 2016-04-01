package cn.zhangxd.server.common.message.converter;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyBeanSerializerModifier extends BeanSerializerModifier {

    private JsonSerializer<Object> _nullArrayJsonSerializer = new MyNullArrayJsonSerializer();
    private JsonSerializer<Object> _nullBooleanJsonSerializer = new MyNullBooleanJsonSerializer();
    private JsonSerializer<Object> _nullMapJsonSerializer = new MyNullMapJsonSerializer();
    private JsonSerializer<Object> _nullNumberJsonSerializer = new MyNullNumberJsonSerializer();

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties) {
        // 循环所有的beanPropertyWriter
        for (BeanPropertyWriter writer : beanProperties) {
            // 判断字段的类型，如果是array，list，set则注册nullSerializer
            Class<?> clazz = writer.getPropertyType();
            if (isArrayType(clazz)) {
                //给writer注册一个自己的nullSerializer
                writer.assignNullSerializer(this.defaultNullArrayJsonSerializer());
            } else if (isBooleanType(clazz)) {
                writer.assignNullSerializer(this.defaultNullBooleanJsonSerializer());
            } else if (isMapType(clazz)) {
                writer.assignNullSerializer(this.defaultNullMapJsonSerializer());
            } else if (isNumberType(clazz)) {
                writer.assignNullSerializer(this.defaultNullNumberJsonSerializer());
            }
        }
        return beanProperties;
    }

    // 判断是什么类型
    protected boolean isArrayType(Class<?> clazz) {
        return clazz.isArray() || List.class.isAssignableFrom(clazz) || Set.class.isAssignableFrom(clazz);
    }

    protected boolean isBooleanType(Class<?> clazz) {
        return Boolean.class.isAssignableFrom(clazz);
    }

    protected boolean isMapType(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }

    protected boolean isNumberType(Class<?> clazz) {
        return Number.class.isAssignableFrom(clazz);
    }

    protected JsonSerializer<Object> defaultNullArrayJsonSerializer() {
        return _nullArrayJsonSerializer;
    }

    protected JsonSerializer<Object> defaultNullBooleanJsonSerializer() {
        return _nullBooleanJsonSerializer;
    }

    protected JsonSerializer<Object> defaultNullMapJsonSerializer() {
        return _nullMapJsonSerializer;
    }

    protected JsonSerializer<Object> defaultNullNumberJsonSerializer() {
        return _nullNumberJsonSerializer;
    }
}