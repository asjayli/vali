package com.kitsrc.watt.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/1/24
 * @time : 13:36
 * Description:
 */
public class StringFormat {
    private static final long serialVersionUID = 5350571835054914059L;
    /**
     * String.format() 使用的 字符串 占位符
     */
    private static final String FORMAT_SYMBOL = "%s";
    /**
     * 占位标志 正则
     */
    private static final String MARK_REGX = "\\{\\}";
    /**
     * 带数字的占位标志 正则
     */
    private static final String NUMBER_MARK_REGX = "\\{[0-9]\\}";
    /**
     * 预编译正则 NUMBER_MARK_REGX pattern
     */
    private static final Pattern NUMBER_MARK_PATTERN = Pattern.compile(NUMBER_MARK_REGX);
    /**
     * 预编译正则 MARK_REGX pattern
     */
    private static final Pattern MARK_PATTERN = Pattern.compile(MARK_REGX);
    private static Set<Pattern> patternSet = new HashSet<>();

    static {
        patternSet.add(MARK_PATTERN);
        patternSet.add(NUMBER_MARK_PATTERN);
    }

    /**
     * 字符串生成 格式化 支持{} 和 {0},{1} 但括号内数字无任何意义 仅作为兼容性措施
     *
     * @param format
     * @param arguments 参数严格有序
     * @return
     */
    public static String format(String format, Object... arguments) {
        String formatString = "";
        if (ObjectUtil.isNotEmpty(arguments) && StringUtil.isNotBlank(format)) {
            // 将 pattern 中 的 {} {} 统一替换成 %s
            // 使用 String.format
            for (Pattern pattern : patternSet) {
                final Matcher matcher = pattern.matcher(format);
                if (matcher.matches()) {
                    formatString = matcher.replaceAll(FORMAT_SYMBOL);
                }
            }
            return String.format(formatString, arguments);
        }
        return formatString;
    }
}
