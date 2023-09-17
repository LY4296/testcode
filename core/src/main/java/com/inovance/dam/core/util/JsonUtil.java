package com.inovance.dam.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    public static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        // 默认情况下，空字符串不能反序列化为 null 对象，会抛异常，此配置解决空字符串转换成Map等对象时不能反序列化为null，而且报错的问题；但是Java simple objects 不受此项配置控制
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        // 处理反序列化时，若遇到未知的属性（没有对应的属性，也没有setter 或 handler来处理这样的属性）会抛出异常的问题
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 解决没有可序列化的属性，Jackson 默认的字段属性发现规则：所有被 public 修饰的字段、所有被 public 修饰的getter。没有可序列化的属性时，会抛出异常的问题
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 默认情况下，时间会以时间戳的形式序列化 关闭此项后，将默认序列化成时间格式
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Bean克隆.
     *
     * @param bean
     * @param dtoClass
     * @return
     */
    public static <T> T clone(Object bean, Class<T> dtoClass) {
        if (bean == null) return null;
        return jsonToBean(objectToJson(bean), dtoClass);
    }



    /**
     * json字符串转换成list.
     *
     * @param json
     * @param beanClass
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> beanClass) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, beanClass);
        try {
            return mapper.readValue(json, javaType);
        } catch (IOException e) {
            log.error("json字符串转换成list异常！", e);
        }
        return null;
    }

    /**
     * json字符串转换成map.
     *
     * @param json json字符串
     * @return map map
     */
    public static <K, V> Map<K, V> jsonToMap(String json) {
        try {
            return mapper.readValue(json, Map.class);
        } catch (IOException e) {
            log.error("json字符串转换成map异常！", e);
        }
        return null;
    }

    /**
     * json字符串转换成bean.
     *
     * @param json          json字符串
     * @param beanClass     bean类型
     * @return bean
     */
    public static <T> T jsonToBean(String json, Class<T> beanClass) {
        try {
            return mapper.readValue(json, beanClass);
        } catch (IOException e) {
            log.error("json字符串转换成bean异常！", e);
        }
        return null;
    }

    /**
     * json字符串转换成bean.
     *
     * @param json          json字符串
     * @param valueTypeRef     TypeReference
     * @return bean
     */
    public static <T> T jsonToBean(String json, TypeReference<T> valueTypeRef) {
        try {
            return mapper.readValue(json, valueTypeRef);
        } catch (IOException e) {
            log.error("json字符串转换成bean异常！", e);
        }
        return null;
    }

    /**
     * 对象转换成json字符串.
     *
     * @param obj 需要转换的对象
     * @return json字符串
     */
    public static String objectToJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            log.error("对象转换成json字符串异常！", e);
        }
        return null;
    }

    /**
     * 对象转换成jsonByte.
     * @param obj 需要转换的对象
     * @return json字符串
     */
    public static byte[] objectToJsonByte(Object obj) {
        try {
            return mapper.writeValueAsBytes(obj);
        } catch (IOException e) {
            log.error("对象转换成jsonByte异常！", e);
        }
        return null;
    }

        /**
         * map转化成bean，深度转化
         *
         * @param map           map
         * @param beanClass     bean类型
         * @return bean
         */
    public static <T> T mapToBean(Map<?, ?> map, Class<T> beanClass) {
        return jsonToBean(objectToJson(map), beanClass);
    }

    /**
     * bean转化成map，深度转化
     *
     * @param obj
     * @return map
     */
    public static <K, V> Map<K, V> beanToMap(Object obj) {
        return jsonToMap(objectToJson(obj));
    }

}
