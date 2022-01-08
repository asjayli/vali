package com.vali.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vandream.hamster.core.util.json.FastJsonUtil;
import com.vandream.hamster.core.util.json.JSONInterface;

/**
 * fastjson工具类
 *
 * @author Li Jie
 */
public class JSONUtil {
    /**
     * json 空数组
     */
    private static final String EMPTY_ARRAY = "[]";
    /**
     * json 空对象
     */
    private static final String EMPTY_OBJECT = "{}";
    /**
     * 使用 com.alibaba.fastjson 实现
     */
    private static JSONInterface json = new FastJsonUtil();


    /**
     * json 字符串转换JSON 原生对象
     *
     * @param jsonText
     * @return <code>com.alibaba.fastjson.JSON</code>
     */
    public static JSON toJSON(String jsonText) {
        return json.toJSON(pt(jsonText));
    }

    /**
     * json 字符串转换JSON 原生对象
     *
     * @param object
     * @return <code>com.alibaba.fastjson.JSON</code>
     */
    public static JSON toJSON(Object object) {
        return json.toJSON(object);
    }

    /**
     * 将json字符串 解析成 java Object
     *
     * @param jsonText
     * @return
     */
    public static Object parse(String jsonText) {
        return json.parse(pt(jsonText));
    }


    /**
     * 将字符串转换成 JSONObject
     *
     * @param jsonText
     * @return
     */
    public static JSONObject parseObject(String jsonText) {
        if (isJSON(jsonText)) {
            Object json = parse(jsonText);
            if (json instanceof JSONObject) {
                return (JSONObject) json;
            }
        }
        return null;
    }

    /**
     * 对象转json
     * 基础类型转为 json数组 格式
     * "xxxx"  --> ["xxxx"]
     * 确保返回结果只有 "" 和合法json格式
     *
     * @param data
     * @return
     */
    public static String toJSONString(Object data) {
        return json.toJSONString(data);
    }

    /**
     * 判断字符串是否为json
     * 暴力解析判断 保证可靠性
     *
     * @param jsonText
     * @return
     */
    public static boolean isJSON(String jsonText) {
        return json.isJSON(pt(jsonText));
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
     * @param data
     * @return
     */
    public static String beanToArray(Object data) {
        if (ObjectUtil.isEmpty(data)) {
            return EMPTY_ARRAY;
        }
        return json.beanToArray(data);
    }


    /**
     * 转换JSON字符串为Java对象
     *
     * @param jsonText
     * @param clazz
     * @return
     */
    public static <T> T parseObject(String jsonText, Class<T> clazz) {
        if (StringUtil.isBlank(jsonText)) {
            return null;
        }
        return json.parseObject(pt(jsonText), clazz);
    }

    /**
     * 转换为数组
     *
     * @param jsonText
     * @param clazz
     * @return
     * @see JSONUtil#toList(String, Class) <code>list.toArray(new <T>[list.size()])</code>
     */
    //@Deprecated
    //public static <T> T[] toArray(String jsonText, Class<T> clazz) {
    //    if (StringUtil.isBlank(jsonText)) {
    //        jsonText = EMPTY_ARRAY;
    //    }
    //    return json.toArray(pt(jsonText), clazz);
    //}

    /**
     * json 转换为List
     *
     * @param jsonText
     * @param clazz
     * @return
     */
    public static <T> List<T> toList(String jsonText, Class<T> clazz) {
        return json.toList(pt(jsonText), clazz);
    }

    /**
     * json text to  <code>Map<String, Object></code>
     *
     * @param jsonText
     * @return
     */
    public static Map<String, Object> toMap(String jsonText) {
        return json.toMap(pt(jsonText));
    }

    /**
     * json text to  <code>Map<String, T></code>
     *
     * @param jsonText
     * @param objClazz
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> toMap(String jsonText, Class<T> objClazz) {
        if (StringUtil.isBlank(jsonText)) {
            return Collections.EMPTY_MAP;
        }
        return json.toMap(pt(jsonText), objClazz);
    }


    /**
     * json to <code>Map<String, String></code>
     *
     * @param jsonText
     * @return
     */
    public static Map<String, String> toMapSS(String jsonText) {
        if (StringUtil.isBlank(jsonText)) {
            return Collections.EMPTY_MAP;
        }
        return json.toMapSS(pt(jsonText));
    }


    /**
     * 检验 清洗 转换
     *
     * @param jsonString
     * @return jsonString
     */
    private static String pt(String jsonString) {
        return jsonString;
    }
}