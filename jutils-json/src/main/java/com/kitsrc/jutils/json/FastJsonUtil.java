package com.kitsrc.jutils.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kitsrc.jutils.core.ObjectUtil;
import com.kitsrc.jutils.core.StringUtil;

/**
 * fastjson工具类
 */
public class FastJsonUtil implements JSONInterface {

    public static final SerializeConfig SERIALIZE_CONFIG;
    public static final Feature[] PARSE_FEATURES = {
            //// 使用BigDecimal 装载数字 而不是double
            //Feature.UseBigDecimal,
            //// 允许 忽略不匹配 的 field
            //Feature.IgnoreNotMatch,
            //// FastJson模式的字段排序 使用 fastJson parser时优化读取 提高性能
            //Feature.SortFeidFastMatch,
            // 禁用ASM 避免使用 FastjsonASMDeserializer* 相关工具
            Feature.DisableASM,
            // 数据对象属性字段 保持原来的顺序
            Feature.OrderedField,
            //对于没有值得字符串属性设置为空串 避免NPE
            //Feature.InitStringFieldAsEmpty,
    };
    private static final TypeReference<Map<String, Object>> TYPE_MAP_SO = new TypeReference<Map<String, Object>>() {
    };
    private static final TypeReference<Map<String, String>> TYPE_MAP_SS = new TypeReference<Map<String, String>>() {
    };
    /**
     * json字符串 预处理 开头匹配
     */
    private static final String PT_START_REGX = "\"\\{";
    /**
     * json字符串 预处理 末尾匹配
     */
    private static final String PT_END_REGX = "\\}\"";
    public static SerializerFeature[] SERIALIZER_FEATURES;
    public static SerializerFeature[] BEAN_TO_ARRAY_SF;
    public static List<SerializerFeature> SF_LIST;
    /**
     * 字符串预处理
     * key: regex
     * value: replacement
     */
    public static Map<String, String> PT_REGX_MAP;

    static {
        PT_REGX_MAP = new HashMap<>();
        PT_REGX_MAP.put(PT_START_REGX, "{");
        PT_REGX_MAP.put(PT_END_REGX, "}");
    }

    static {
        SERIALIZE_CONFIG = SerializeConfig.getGlobalInstance();

        SF_LIST = new ArrayList<>(33);
        // 禁止循环引用 避免 json中 出现 $ref等字样
        SF_LIST.add(SerializerFeature.DisableCircularReferenceDetect);
        // 输出空置字段
        SF_LIST.add(SerializerFeature.WriteMapNullValue);
        // list字段如果为null，输出为[]，而不是null
        SF_LIST.add(SerializerFeature.WriteNullListAsEmpty);
        // Boolean字段如果为null，输出为false，而不是null
        SF_LIST.add(SerializerFeature.WriteNullBooleanAsFalse);
        // 字符类型字段如果为null，输出为""，而不是null
        SF_LIST.add(SerializerFeature.WriteNullStringAsEmpty);
        // 允许 JSON.DEFFAULT_DATE_FORMAT =""; 来指定日期格式
        SF_LIST.add(SerializerFeature.WriteDateUseDateFormat);
        //枚举 使用 Enum的toString() 输出
        SF_LIST.add(SerializerFeature.WriteEnumUsingToString);
        // 跳过 transient
        SF_LIST.add(SerializerFeature.SkipTransientField);
        // 字段排序
        SF_LIST.add(SerializerFeature.SortField);

        SERIALIZER_FEATURES = SF_LIST.toArray(new SerializerFeature[SF_LIST.size()]);
        // BeanToArray 模式 过滤掉key
        SF_LIST.add(SerializerFeature.BeanToArray);
        BEAN_TO_ARRAY_SF = SF_LIST.toArray(new SerializerFeature[SF_LIST.size()]);
        SF_LIST = null;

    }
    // 首先各种对象转换成 JSON 对象

    /**
     * 禁止使用
     *
     * @param object
     * @return
     */
    private static String toJSONStr(Object object) {
        // 2018-12-31 00:00:00.000 +0800 带时区的日期格式
        // 不指定时 时间序列化成时间戳
        //JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss Z";

        return JSON.toJSONString(object, SERIALIZE_CONFIG, SERIALIZER_FEATURES);
    }


    /**
     * java对象 转换JSON 原生对象
     *
     * @param data
     * @return
     */
    @Override
    public JSON toJSON(Object data) {
        final Object json = JSON.toJSON(data);
        if (json instanceof JSON) {
            return (JSON) json;
        }
        else {
            return null;
        }
    }

    /**
     * json 字符串转换JSON 原生对象
     *
     * @param jsonText
     * @return
     */
    @Override
    public JSON toJSON(String jsonText) {
        Object parse = JSON.parse(jsonText);
        if (parse instanceof JSONObject || parse instanceof JSONArray) {
            return (JSON) parse;
        }
        else {
            throw new IllegalArgumentException("FastJsonUtil.toJSON 不是标准的JSON字符串:" + jsonText);
        }
    }

    /**
     * 转换JSON字符串为Java对象
     *
     * @param object
     * @param clazz
     * @return
     */
    public <T> T parseObject(Object object, Class<T> clazz) {
        String jsonText = toJSONString(object);
        return JSON.parseObject(jsonText, clazz, PARSE_FEATURES);
    }

    /**
     * 将java对象转换成 JSONObject
     *
     * @param data
     * @return
     */
    public JSONObject parseObject(Object data) {
        return JSON.parseObject(toJSONString(data), PARSE_FEATURES);
    }

    /**
     * 将json字符串 解析成 java Object
     *
     * @param jsonText
     * @return
     */
    @Override
    public Object parse(String jsonText) {
        return JSON.parse(jsonText, PARSE_FEATURES);
    }

    /**
     * 将java对象转换成 JSONArray
     *
     * @param text
     * @return
     */

    public JSONArray parseArray(String text) {
        return JSON.parseArray(text);
    }

    /**
     * 将字符串转换成 JSONObject
     *
     * @param text
     * @return
     */
    public JSONObject parseObject(String text) {
        return JSON.parseObject(text, PARSE_FEATURES);
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
    @Override
    public String toJSONString(Object data) {
        if (ObjectUtil.isNotEmpty(data)) {
            if (ObjectUtil.isPrimitive(data)) {
                return toJSONStr(data);
            }
            if (data instanceof JSONObject) {
                return toJSONStr(data);
            }
            if (data instanceof JSONArray) {
                return toJSONStr(data);
            }
            if (data instanceof String) {
                String jsonText = (String) data;
                if (isJSON(jsonText)) {
                    return jsonText;
                }
                else {
                    String[] strArray = new String[] {data.toString()};
                    return toJSONStr(strArray);
                }
            }
            if (ObjectUtil.isPrimitive(data)) {
                Object[] objArray = new Object[] {data};
                return toJSONStr(objArray);
            }
            if (data instanceof Serializable) {
                //当传入对象是 Serializable 接口的子孙类
                return toJSONStr(data);
            }


            return toJSONStr(data);

        }
        else {
            return "";
        }
    }

    /**
     * 判断字符串是否为json
     * 暴力解析判断 保证可靠性
     *
     * @param text
     * @return
     */
    @Override
    public boolean isJSON(String text) {
        if (StringUtil.isBlank(text)) {
            return false;
        }
        try {
            Object parse = JSON.parse(text);
            if (parse instanceof JSONObject || parse instanceof JSONArray) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断字符串是否为json
     * 暴力解析判断 保证可靠性
     *
     * @param object
     * @return
     */

    public boolean isJSON(Object object) {
        if (ObjectUtil.isEmpty(object)) {
            return false;
        }
        try {
            if (object instanceof JSONObject || object instanceof JSONArray) {
                return true;
            }
            else if (object instanceof String) {
                return isJSON((String) object);
            }
            else {
                String jsonText = toJSONStr(object);
                return isJSON(jsonText);
            }
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
        String json = JSON.toJSONString(object, BEAN_TO_ARRAY_SF);
        return json;
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
        return JSON.parseObject(jsonText, clazz, PARSE_FEATURES);
    }


    /**
     * 转换为数组
     *
     * @param jsonText
     * @param clazz
     * @return
     */
    //@Override
    //@Deprecated
    //public <T> T[] toArray(String jsonText, Class<T> clazz) {
    //    return (T[]) toList(jsonText, clazz).toArray();
    //}

    /**
     * json 转换为List
     *
     * @param jsonText
     * @return
     */
    @Override
    public List<Object> toList(String jsonText) {
        final List<Object> list = JSON.parseArray(jsonText, Object.class);
        return list;
    }

    /**
     * json 转换为List
     *
     * @param jsonText
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> List<T> toList(String jsonText, Class<T> clazz) {
        List<T> resultList = JSON.parseArray(jsonText, clazz);
        return resultList;
    }

    /**
     * json to  <code>Map<String, Object></code>
     *
     * @param jsonText
     * @return
     */
    @Override
    public Map<String, Object> toMap(String jsonText) {
        Map<String, Object> resultMap = JSON.parseObject(jsonText, TYPE_MAP_SO, PARSE_FEATURES);
        return resultMap;
    }

    /**
     * json to <code>Map<String, T></code>
     *
     * @param jsonText
     * @param clazz
     * @return
     */
    @Override
    public <T> Map<String, T> toMap(String jsonText, Class<T> clazz) {
        return null;
    }

    public Map<String, Object> toMap(Object object) {
        String jsonText = toJSONString(object);
        Map<String, Object> resultMap = JSON.parseObject(jsonText, TYPE_MAP_SO, PARSE_FEATURES);
        return resultMap;
    }

    /**
     * json to <code>Map<String, String></code>
     *
     * @param jsonText
     * @return
     */
    @Override
    public Map<String, String> toMapSS(String jsonText) {
        Map<String, String> resultMap = JSON.parseObject(jsonText, TYPE_MAP_SS, PARSE_FEATURES);
        return resultMap;
    }

    /**
     * json to <code>Map<String, String></code>
     *
     * @param object
     * @return
     */
    public Map<String, String> toMapSS(Object object) {

        String jsonText = toJSONString(object);
        Map<String, String> resultMap = JSON.parseObject(jsonText, TYPE_MAP_SS, PARSE_FEATURES);
        return resultMap;
    }

}