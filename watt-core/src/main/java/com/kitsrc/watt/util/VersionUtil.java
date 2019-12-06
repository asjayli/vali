package com.kitsrc.watt.util;

import java.util.regex.Matcher;

import com.vandream.hamster.core.constant.RegxConstants;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/1/28
 * @time : 11:01
 * Description:
 */
public class VersionUtil {

    /**
     * 校验版本号
     *
     * @param serviceVersion
     * @return
     */
    public static void checkVersionNumber(String serviceName, String serviceVersion) {
        Matcher serviceVersionMatcher = RegxConstants.PATTERN_VERSION_NUMBER_REGX.matcher(serviceVersion);

        if (StringUtil.isBlank(serviceVersion) || !serviceVersionMatcher.matches()) {
            throw new RuntimeException(serviceName + " [" + serviceVersion + "] format error " +
                    "版本号格式错误");
        }
    }

    /**
     * 校验版本号
     *
     * @param actionVersion
     * @return
     */
    public static void checkVersionNumber(String serviceName, String actionName, String actionVersion) {
        Matcher serviceVersionMatcher = RegxConstants.PATTERN_VERSION_NUMBER_REGX.matcher(actionVersion);

        if (StringUtil.isBlank(actionVersion) || !serviceVersionMatcher.matches()) {
            throw new RuntimeException(serviceName + "." + actionName + " [" + actionVersion + "] format error " +
                    "版本号格式错误");
        }
    }
}
