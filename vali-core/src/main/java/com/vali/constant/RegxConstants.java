package com.vali.constant;

import java.util.regex.Pattern;

/**
 * 正则表达式 常量集合 </p>
 *
 * @author : LiJie  </p>
 * @date : 2019/05/13  </p>
 * @time : 20:44  </p>
 * Description:  </p>
 */
public interface RegxConstants {
    /**
     * 统一三段式版本号匹配规则
     */
    String VERSION_NUMBER_REGX = "^[1-9]{0,3}\\.([1-9]{0,3}|0)\\.([1-9]{0,3}|0)$";
    /**
     * 版本号校验 预编译正则表达式
     */
    Pattern PATTERN_VERSION_NUMBER_REGX = Pattern.compile(VERSION_NUMBER_REGX);
}
