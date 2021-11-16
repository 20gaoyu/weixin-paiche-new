package cn.gy.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

/**
 * @author qingx
 * @Modified by qingx
 * @Modified_Date: 2020/8/5
 * @Modified_Desc:
 * @ClassName JacksonUtil
 */
public class JacksonUtil {
    private static final String DEFAULT_STRING_VALUE = "";
    private static final int DEFAULT_INT_VALUE = 0;
    private static final long DEFAULT_LONG_VALUE = 0L;
    private static final double DEFAULT_DOUBLE_VALUE = 0.0;
    private static final byte DEFAULT_BYTE_VALUE = 0;
    private static final ThreadLocal<ObjectMapper> objectMapperThreadLocal = ThreadLocal.withInitial(() -> {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
            @Override
            public boolean hasIgnoreMarker(final AnnotatedMember m) {
                // 防止单测序列化mock对象时出现死循环
                return super.hasIgnoreMarker(m) || m.getFullName().contains("Mockito");
            }
        });
        return mapper;
    });

    public static ObjectNode createObjectNode() {
        return objectMapperThreadLocal.get().createObjectNode();
    }

    public static ArrayNode createArrayNode() {
        return objectMapperThreadLocal.get().createArrayNode();
    }

    public static JsonNode readTree(String content) throws IOException {
        return objectMapperThreadLocal.get().readTree(content);
    }

    public static JsonNode readTree(byte[] content) throws IOException {
        return objectMapperThreadLocal.get().readTree(content);
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        return objectMapperThreadLocal.get().readValue(json, clazz);
    }


    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapperThreadLocal.get().writeValueAsString(object);
    }

    public static <T> T jsonNodeToObject(TreeNode treeNode, Class<T> clazz) throws IOException {
        if (treeNode == null) {
            return null;
        } else {
            return objectMapperThreadLocal.get().treeToValue(treeNode, clazz);
        }
    }

    public static <T> T parseObject(Object obj, TypeReference<T> typeReference) throws IOException{
        if (obj == null) {
            return null;
        } else {
            return objectMapperThreadLocal.get().convertValue(obj, typeReference);
        }
    }

    public static <T> T parseObject(String json, TypeReference<T> typeReference) throws IOException{
        if (json != null && json.length() > 0) {
            return objectMapperThreadLocal.get().readValue(json, typeReference);
        } else {
            return null;
        }
    }
    public static <T> T parseObject(Object obj, Class<T> clazz) throws IOException{
        if (obj == null) {
            return null;
        } else {
            return objectMapperThreadLocal.get().convertValue(obj, clazz);
        }
    }

    public static <T> T parseObject(String json, Class<T> clazz) throws IOException{
        if (json != null && json.length() > 0) {
            return objectMapperThreadLocal.get().readValue(json, clazz);
        } else {
            return null;
        }
    }
    public static byte getByte(JsonNode jsonNode, String key){
        return getByte(jsonNode, key, DEFAULT_BYTE_VALUE);
    }

    public static String getString(JsonNode jsonNode, String key) {
        return getString(jsonNode, key, DEFAULT_STRING_VALUE);
    }

    public static int getInt(JsonNode jsonNode, String key) {
        return getInt(jsonNode, key, DEFAULT_INT_VALUE);
    }

    public static long getLong(JsonNode jsonNode, String key){
        return getLong(jsonNode, key, DEFAULT_LONG_VALUE);
    }

    public static double getDouble(JsonNode jsonNode, String key) {
        return getDouble(jsonNode, key, DEFAULT_DOUBLE_VALUE);
    }

    public static String getString(JsonNode jsonNode, String key, String defaultValue){
        JsonNode node = jsonNode.get(key);
        return node == null ? defaultValue : node.asText(defaultValue);
    }

    public static int getInt(JsonNode jsonNode, String key, int defaultValue){
        JsonNode node = jsonNode.get(key);
        return node == null ? defaultValue : node.asInt(defaultValue);
    }

    public static boolean getBool(JsonNode jsonNode, String key){
        JsonNode node = jsonNode.get(key);
        return  node.asBoolean();
    }

    public static boolean exist(JsonNode jsonNode, String key){
        return jsonNode.has(key);
    }

    public static long getLong(JsonNode jsonNode, String key, long defaultValue){
        JsonNode node = jsonNode.get(key);
        return node == null ? defaultValue : node.asLong(defaultValue);
    }

    public static double getDouble(JsonNode jsonNode, String key, double defaultValue){
        JsonNode node = jsonNode.get(key);
        return node == null ? defaultValue : node.asDouble(defaultValue);
    }

    public static byte getByte(JsonNode jsonNode, String key, byte defaultValue){
        int value = getInt(jsonNode, key, defaultValue);
        if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE){
            return defaultValue;
        }else {
            return (byte) value;
        }
    }
}
