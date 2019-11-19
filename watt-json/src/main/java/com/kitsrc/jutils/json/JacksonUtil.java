package com.kitsrc.jutils.json;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.kitsrc.jutils.core.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/2/20
 * @time : 10:22
 * Description:
 */
public class JacksonUtil implements JSONInterface {
    private static final Logger logger = LoggerFactory.getLogger(JacksonUtil.class);
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final static TypeReference<Map<String, Object>> TYPE_MAP_SO = new TypeReference<Map<String, Object>>() {
    };
    private final static TypeReference<Map<String, String>> TYPE_MAP_SS = new TypeReference<Map<String, String>>() {
    };

    static {
        /**
         * 序列化的时候序列对象的所有属性
         */
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        /**
         * 反序列化的时候如果多了其他属性,不抛出异常
         */
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        /**
         * 如果是空对象的时候,不抛异常
         */
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);


        /**
         * 取消默认的时间转化格式,默认是时间戳,同时需要设置要表现的时间格式
         */
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        final JsonNodeFactory nodeFactory = OBJECT_MAPPER.getNodeFactory();
        return OBJECT_MAPPER.getTypeFactory()
                .constructParametricType(collectionClass, elementClasses);
    }


    /**
     * 把json解析成list，如果list内部的元素存在jsonString，继续解析
     *
     * @param json
     * @return
     */
    @Override
    public List toList(String json) {
        try {
            if (StringUtil.isBlank(json)) {
                return Collections.EMPTY_LIST;
            }

            List list = OBJECT_MAPPER.readValue(json, List.class);

            for (Object obj : list) {
                if (obj instanceof String) {
                    String str = (String) obj;
                    if (str.startsWith("[")) {
                        obj = toList(str);
                    }
                    else if (obj.toString()
                            .startsWith("{")) {
                        obj = toMap(str);
                    }
                }
            }

            return list;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转换为map
     */
    @Override
    public <T> Map<String, T> toMap(String jsonString, Class<T> clazz) {
        try {
            final Map<String, Map<String, Object>> rawMap = OBJECT_MAPPER
                    .readValue(jsonString, new TypeReference<Map<String, T>>() {
                    });
            Map<String, T> result = new HashMap<>(rawMap.size());
            for (Map.Entry<String, Map<String, Object>> entry : rawMap.entrySet()) {
                result.put(entry.getKey(), OBJECT_MAPPER.convertValue(rawMap, clazz));
            }
            return result;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Object parse(String jsonText) {
        return null;
    }

    /**
     * javaBean、列表数组转换为json字符串
     */
    @Override
    public String toJSONString(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断字符串是否为json
     * 暴力解析判断 保证可靠性
     *
     * @param jsonText
     * @return
     */
    @Override
    public boolean isJSON(String jsonText) {
        try {
            OBJECT_MAPPER.readTree(jsonText);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * <code>
     * Model model = new Model();<br>
     * model.id = 1001;<br>
     * model.name = "gaotie";<br>
     * </code>
     * 执行 beanToArray(model)<br><br>
     * &#9;[1001,"gaotie"]
     *
     * @param object
     * @return
     */
    @Override
    public String beanToArray(Object object) {
        return null;
    }

    @Override
    public JSON toJSON(Object data) {
        return null;
    }

    @Override
    public JSON toJSON(String jsonText) {
        return null;
    }

    /**
     * 转换JSON字符串为Java对象
     *
     * @param jsonText
     * @param clazz
     * @return
     */
    @Override
    public <T> T parseObject(String jsonText, Class<T> clazz) {
        try {
            final T value = OBJECT_MAPPER.readValue(jsonText, clazz);
            return value;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }


    /**
     * 转换为数组
     *
     * @param jsonText
     * @param clazz
     * @return
     * @see JacksonUtil#toList(String, Class) <code>list.toArray(new <T>[list.size()])</code>
     */
    //@Override
    //@Deprecated
    //public <T> T[] toArray(String jsonText, Class<T> clazz) {
    //    final List<T> list = toList(jsonText, clazz);
    //    return (T[]) list.toArray();
    //
    //}

    /**
     * json 转换为List
     *
     * @param jsonText
     * @param clazz
     * @return
     */
    @Override
    public <T> List<T> toList(String jsonText, Class<T> clazz) {
        try {
            if (jsonText == null) {
                return Collections.EMPTY_LIST;
            }

            List<T> list = OBJECT_MAPPER.readValue(jsonText, new TypeReference<List<T>>() {
            });
            return list;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }

    /**
     * json to  <code>Map<String, Object></code>
     *
     * @param jsonText
     * @return
     */
    @Override
    public Map<String, Object> toMap(String jsonText) {
        try {
            if (jsonText == null) {
                return null;
            }
            Map<String, Object> map = OBJECT_MAPPER.readValue(jsonText, Map.class);

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Object obj = entry.getValue();
                if (obj != null && obj instanceof String) {
                    String str = ((String) obj);

                    if (str.startsWith("[")) {
                        List<?> list = toList(str);
                        map.put(entry.getKey(), list);
                    }
                    else if (str.startsWith("{")) {
                        Map<String, Object> mapRecursion = toMap(str);
                        map.put(entry.getKey(), mapRecursion);
                    }
                }
            }
            return map;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * json to <code>Map<String, String></code>
     *
     * @param jsonText
     * @return
     */
    @Override
    public Map<String, String> toMapSS(String jsonText) {
        try {
            return OBJECT_MAPPER.readValue(jsonText, TYPE_MAP_SS);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
