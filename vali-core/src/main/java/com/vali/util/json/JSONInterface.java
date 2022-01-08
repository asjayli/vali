package com.vali.util.json;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/2/19
 * @time : 17:41
 * Description:
 */
public interface JSONInterface {
    /**
     * 将json字符串 解析成 java Object
     *
     * @param jsonText
     * @return
     */
    Object parse(String jsonText);

    /**
     * 对象转json
     * 基础类型转为 json数组 格式
     * "xxxx"  --> ["xxxx"]
     * 确保返回结果只有 "" 和合法json格式
     *
     * @param data
     * @return
     * @
     */
    String toJSONString(Object data);

    /**
     * 判断字符串是否为json
     * 暴力解析判断 保证可靠性
     *
     * @param jsonText
     * @return
     */

    boolean isJSON(String jsonText);


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

    String beanToArray(Object data);

    /**
     * 转换成 fastjson 原生对象
     *
     * @param data
     * @return
     */
    JSON toJSON(Object data);

    /**
     * 转换成 fastjson 原生对象
     *
     * @param jsonText
     * @return
     */
    JSON toJSON(String jsonText);

    /**
     * 转换JSON字符串为Java对象
     *
     * @param jsonText
     * @param clazz
     * @return
     */

    <T> T parseObject(String jsonText, Class<T> clazz);


    /**
     * 转换为数组
     *
     * @param jsonText
     * @param clazz
     * @return
     */

    //<T> T[] toArray(String jsonText, Class<T> clazz);

    /**
     * json 转换为List
     *
     * @param jsonText
     * @return
     */
    List toList(String jsonText);

    /**
     * json 转换为List
     *
     * @param jsonText
     * @param clazz
     * @param <T>
     * @return
     * @
     */

    <T> List<T> toList(String jsonText, Class<T> clazz);

    /**
     * json to  <code>Map<String, Object></code>
     *
     * @param jsonText
     * @return
     */

    Map<String, Object> toMap(String jsonText);

    /**
     * json to <code>Map<String, T></code>
     *
     * @param jsonText
     * @param clazz
     * @param <T>
     * @return
     */
    <T> Map<String, T> toMap(String jsonText, Class<T> clazz);

    /**
     * json to <code>Map<String, String></code>
     *
     * @param jsonText
     * @return
     */

    Map<String, String> toMapSS(String jsonText);

}
