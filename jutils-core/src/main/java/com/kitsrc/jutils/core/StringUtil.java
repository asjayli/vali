package com.kitsrc.jutils.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/3/21
 * @time : 19:35
 * Description:
 */
public class StringUtil extends StringUtils {

    /**
     * 大写 NULL 字符串 "NULL"
     */
    public static final String UPPER_NULL = "NULL";
    /**
     * 小写 null 字符串 "null"
     */
    public static final String LOWER_NULL = "null";
    /**
     * undefined
     */
    public static final String UNDEFINED = "undefined";
    /**
     * 下划线
     */
    public static final char UNDERLINE = '_';
    /**
     * 英文文本内容里的连字符
     */
    public static final char HYPHEN = '-';
    /**
     * empty string
     */
    public static final String EMPTY = "";
    /**
     * 分号
     */
    public static final String SEMICOLON = ";";
    /**
     * 斜杠
     */
    public static final String SLASH = "/";
    /**
     * 反斜杠
     */
    public static final String BACKSLASH = "\\";
    /**
     * 标识索引搜索失败
     */
    public static final int INDEX_NOT_FOUND = -1;
    /**
     * 空格
     */
    public static final String SPACE = " ";
    /**
     * 制表符
     */
    public static final String TAB = "\t";
    /**
     * 点 英文句号
     */
    public static final String DOT = ".";
    /**
     * 点 英文句号 两个
     */
    public static final String DOUBLE_DOT = "..";
    /**
     *
     */
    public static final String CR = "\r";
    /**
     *
     */
    public static final String LF = "\n";
    /**
     *
     */
    public static final String CRLF = "\r\n";
    /**
     * 减号 虚线
     */
    public static final String DASHED = "-";
    /**
     * 逗号
     */
    public static final String COMMA = ",";
    /**
     * 大括号 start
     */
    public static final String DELIM_START = "{";
    /**
     * 大括号 end
     */
    public static final String DELIM_END = "}";
    /**
     * 方括号 start
     */
    public static final String BRACKET_START = "[";
    /**
     * 方括号 end
     */
    public static final String BRACKET_END = "]";
    /**
     * 冒号
     */
    public static final String COLON = ":";
    /**
     * 匹配字符串中所有空白字符 空格 制表符 换行符 \s 可以匹配空格、制表符、换页符等空白字符
     */
    private static final Pattern PATTERN_BLANK_CHARACTER = Pattern.compile("\\s*|\t*|\r*|\n*");


    /**
     * 将字符串修饰成驼峰命名风格
     *
     * @param underlineName
     * @param capitalized
     * @return
     */
    public static String toCamelCaseStyle(String underlineName, boolean capitalized) {
        StringBuilder builder = new StringBuilder();
        int of = underlineName.indexOf((int) StringUtil.UNDERLINE);
        // of > -1 表示找到位置
        if (of < 0) {
            if (capitalized) {
                return StringUtil.capitalize(underlineName);
            }
            else {
                return StringUtil.uncapitalize(underlineName);
            }
        }
        char[] charArray = underlineName.toCharArray();
        //char[] resultArr = new char[charArray.length];
        boolean first = true;
        boolean underline = false;
        for (char ch : charArray) {
            if (ch == StringUtil.UNDERLINE) {
                underline = true;
                continue;
            }
            // c >= 'A' && c <= 'Z'
            if (ch >= 65 && ch <= 90) {
                if (first) {
                    first = false;
                    underline = false;
                    if (!capitalized) {
                        ch = Character.toLowerCase(ch);

                    }
                }
                else if (underline) {
                    underline = false;
                    //c = Character.toLowerCase(c);

                }
            }
            // 等价 c >= 'a' && c <= 'z'
            if (ch >= 97 && ch <= 122) {
                if (first) {
                    first = false;
                    underline = false;
                    if (capitalized) {
                        ch = Character.toUpperCase(ch);
                    }
                }
                else if (underline) {
                    underline = false;
                    ch = Character.toUpperCase(ch);

                }
            }

            builder.append(ch);
        }
        return builder.toString();
    }

    /**
     * 去除首尾空格 如果为空 返回 ""
     *
     * @param sourceStr
     * @return
     * @see String#trim()
     * @see StringUtil#EMPTY
     */
    public static String trim(String sourceStr) {
        if (StringUtil.isEmpty(sourceStr)) {
            return StringUtil.EMPTY;
        }
        else {
            return sourceStr.trim();
        }
    }

    /**
     * 将字符串修饰成 下划线拼接风格
     *
     * @param source
     * @param capitalized
     * @return
     */
    public static String toUnderlineStyle(String source, boolean capitalized) {

        StringBuilder builder = new StringBuilder();
        {
            // 去除首尾下划线
            source = StringUtil.trim(source);

            // 纯大写字符串 原样返回
            String upperCase = source.toUpperCase();
            if (upperCase.equals(source)) {
                return source;
            }
        }
        // 字符串内仅保留
        for (int i = 0, len = source.length(); i < len; i++) {
            char c = source.charAt(i);

            // 如果是大写
            if (Character.isUpperCase(c)) {
                // 首字母大写无视
                if (i > 0) {
                    builder.append(StringUtil.UNDERLINE);
                }
            }
            else if (Character.isDigit(c) || Character.isLowerCase(c) || Character
                    .compare(c, StringUtil.UNDERLINE) == 0) {
                builder.append(Character.toLowerCase(c));
            }
        }
        return builder.toString();
    }

    /**
     * 删除字符串中所有空白字符 空格 制表符 换行符 换页符
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = null;
        if (str == null) {
            return dest;
        }
        else {

            Matcher m = StringUtil.PATTERN_BLANK_CHARACTER.matcher(str);
            dest = m.replaceAll("");
            return dest;
        }
    }

    /**
     * <p>Checks if a CharSequence is empty (""), null or whitespace only.</p>
     * <p>
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     * <p>
     * <pre>
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace only
     * @since 2.0
     * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串 判断 并返回默认值
     *
     * @param sourceStr    原字符串
     * @param defaultValue 默认值
     * @return
     */
    public static String defaultIfBlank(String sourceStr, String defaultValue) {
        if (StringUtil.isBlank(sourceStr)) {
            return defaultValue;
        }
        return sourceStr;
    }

    /**
     * StringUtil.isBlank(...)==true  return false
     * * StringUtil.isNullStr("NULL") return true
     * * StringUtil.isNullStr("null") return true
     *
     * @param cs
     * @return
     */
    public static boolean isNullStr(CharSequence cs) {
        if (StringUtil.isBlank(cs)) {
            return false;
        }
        if (StringUtil.UPPER_NULL.equals(cs) || StringUtil.LOWER_NULL.equals(cs)) {
            return true;
        }
        return false;
    }

    /**
     * 字符串 判断 并返回默认值
     *
     * @param sourceStr    原字符串
     * @param defaultValue 默认值
     * @return
     */
    public static String defaultIfNullStr(String sourceStr, String defaultValue) {
        if (StringUtil.isNullStr(sourceStr)) {
            return defaultValue;
        }
        return sourceStr;
    }

    /**
     * * StringUtil.isNotNullStr("NULL")    = false
     * * StringUtil.isNotNullStr("null")    = false
     *
     * @param cs
     * @return
     */
    public static boolean isNotNullStr(CharSequence cs) {
        return StringUtil.isNullStr(cs);
    }

    /**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
     * <p>
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     * <p>
     * <pre>
     * StringUtil.isNotBlank(null)      = false
     * StringUtil.isNotBlank("")        = false
     * StringUtil.isNotBlank(" ")       = false
     * StringUtil.isNotBlank("bob")     = true
     * StringUtil.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is
     * not empty and not null and not whitespace only
     * @since 2.0
     * @since 3.0 Changed signature from isNotBlank(String) to isNotBlank(CharSequence)
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !StringUtil.isBlank(cs);
    }

    /**
     * <p>Checks if a String {@code str} contains Unicode digits,
     * if yes then  return first  digits in {@code str} and return it as a String.</p>
     * <p>
     * <p>An empty ("") String will be returned if no digits found in {@code str}.</p>
     * <p>
     * <pre>
     * StringUtil.getFirstDigits(null)  = null
     * StringUtil.getFirstDigits("")    = ""
     * StringUtil.getFirstDigits("abc") = ""
     * StringUtil.getFirstDigits("1000$") = "1000"
     * StringUtil.getFirstDigits("1123~45") = "123"
     * StringUtil.getFirstDigits("0.125mm") = "0.125"
     * StringUtil.getFirstDigits("(541) 754-3010") = "541"
     * StringUtil.getFirstDigits("\u0967\u0968\u0969") = "\u0967\u0968\u0969"
     * </pre>
     *
     * @param str the String to extract digits from, may be null
     * @return String with only digits,
     * or an empty ("") String if no digits found,
     * or {@code null} String if {@code str} is null
     * @since 3.6
     */
    public static String getFirstDigit(String str) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        if (!isNumeric(str)) {
            return "";
        }
        int sz = str.length();
        StringBuilder strDigits = new StringBuilder(sz);
        boolean flag = false;
        for (int i = 0; i < sz; i++) {
            char tempChar = str.charAt(i);
            if (Character.isDigit(tempChar)) {
                flag = true;
                strDigits.append(tempChar);
            }
            else {
                if (flag) {
                    if ('.' == tempChar) {
                        strDigits.append(tempChar);
                    }
                    else {
                        break;
                    }
                }
            }
        }
        return strDigits.toString();
    }

    /**
     * <p>Checks if a CharSequence is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the CharSequence.
     * That functionality is available in isBlank().</p>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 字符串 判断 并返回默认值
     *
     * @param sourceStr    原字符串
     * @param defaultValue 默认值
     * @return
     */
    public static String defaultIfEmpty(String sourceStr, String defaultValue) {
        if (StringUtil.isEmpty(sourceStr)) {
            return defaultValue;
        }
        return sourceStr;
    }

    public static boolean isNotEmpty(String str) {
        return !StringUtil.isEmpty(str);
    }

    /**
     * <p>Compares all Strings in an array and returns the initial sequence of
     * characters that is common to all of them.</p>
     *
     * <p>For example,
     * <code>getCommonPrefix(new String[] {"i am a machine", "i am a robot"}) -&gt; "i am a "</code></p>
     *
     * <pre>
     * StringUtils.getCommonPrefix(null) = ""
     * StringUtils.getCommonPrefix(new String[] {}) = ""
     * StringUtils.getCommonPrefix(new String[] {"abc"}) = "abc"
     * StringUtils.getCommonPrefix(new String[] {null, null}) = ""
     * StringUtils.getCommonPrefix(new String[] {"", ""}) = ""
     * StringUtils.getCommonPrefix(new String[] {"", null}) = ""
     * StringUtils.getCommonPrefix(new String[] {"abc", null, null}) = ""
     * StringUtils.getCommonPrefix(new String[] {null, null, "abc"}) = ""
     * StringUtils.getCommonPrefix(new String[] {"", "abc"}) = ""
     * StringUtils.getCommonPrefix(new String[] {"abc", ""}) = ""
     * StringUtils.getCommonPrefix(new String[] {"abc", "abc"}) = "abc"
     * StringUtils.getCommonPrefix(new String[] {"abc", "a"}) = "a"
     * StringUtils.getCommonPrefix(new String[] {"ab", "abxyz"}) = "ab"
     * StringUtils.getCommonPrefix(new String[] {"abcde", "abxyz"}) = "ab"
     * StringUtils.getCommonPrefix(new String[] {"abcde", "xyz"}) = ""
     * StringUtils.getCommonPrefix(new String[] {"xyz", "abcde"}) = ""
     * StringUtils.getCommonPrefix(new String[] {"i am a machine", "i am a robot"}) = "i am a "
     * </pre>
     *
     * @param strs  array of String objects, entries may be null
     * @return the initial sequence of characters that are common to all Strings
     * in the array; empty String if the array is null, the elements are all null
     * or if there is no common prefix.
     * @since 2.4
     */
    public static String getCommonPrefix(String... strs) {
        if (strs == null || strs.length == 0) {
            return StringUtil.EMPTY;
        }
        int smallestIndexOfDiff = StringUtil.indexOfDifference(strs);
        if (smallestIndexOfDiff == StringUtil.INDEX_NOT_FOUND) {
            // all strings were identical
            if (strs[0] == null) {
                return StringUtil.EMPTY;
            }
            return strs[0];
        }
        else if (smallestIndexOfDiff == 0) {
            // there were no common initial characters
            return StringUtil.EMPTY;
        }
        else {
            // we found a common initial character sequence
            return strs[0].substring(0, smallestIndexOfDiff);
        }
    }

    /**
     * <p>Compares two CharSequences, and returns the index at which the
     * CharSequences begin to differ.</p>
     *
     * <p>For example,
     * {@code indexOfDifference("i am a machine", "i am a robot") -> 7}</p>
     *
     * <pre>
     * StringUtils.indexOfDifference(null, null) = -1
     * StringUtils.indexOfDifference("", "") = -1
     * StringUtils.indexOfDifference("", "abc") = 0
     * StringUtils.indexOfDifference("abc", "") = 0
     * StringUtils.indexOfDifference("abc", "abc") = -1
     * StringUtils.indexOfDifference("ab", "abxyz") = 2
     * StringUtils.indexOfDifference("abcde", "abxyz") = 2
     * StringUtils.indexOfDifference("abcde", "xyz") = 0
     * </pre>
     *
     * @param cs1  the first CharSequence, may be null
     * @param cs2  the second CharSequence, may be null
     * @return the index where cs1 and cs2 begin to differ; -1 if they are equal
     * @since 2.0
     * @since 3.0 Changed signature from indexOfDifference(String, String) to
     * indexOfDifference(CharSequence, CharSequence)
     */
    public static int indexOfDifference(CharSequence cs1, CharSequence cs2) {
        if (cs1 == cs2) {
            return StringUtil.INDEX_NOT_FOUND;
        }
        if (cs1 == null || cs2 == null) {
            return 0;
        }
        int i;
        for (i = 0; i < cs1.length() && i < cs2.length(); ++i) {
            if (cs1.charAt(i) != cs2.charAt(i)) {
                break;
            }
        }
        if (i < cs2.length() || i < cs1.length()) {
            return i;
        }
        return StringUtil.INDEX_NOT_FOUND;
    }

    /**
     * <p>Compares all CharSequences in an array and returns the index at which the
     * CharSequences begin to differ.</p>
     *
     * <p>For example,
     * {@code indexOfDifference(new String[] {"i am a machine", "i am a robot"}) -> 7}</p>
     *
     * <pre>
     * StringUtils.indexOfDifference(null) = -1
     * StringUtils.indexOfDifference(new String[] {}) = -1
     * StringUtils.indexOfDifference(new String[] {"abc"}) = -1
     * StringUtils.indexOfDifference(new String[] {null, null}) = -1
     * StringUtils.indexOfDifference(new String[] {"", ""}) = -1
     * StringUtils.indexOfDifference(new String[] {"", null}) = 0
     * StringUtils.indexOfDifference(new String[] {"abc", null, null}) = 0
     * StringUtils.indexOfDifference(new String[] {null, null, "abc"}) = 0
     * StringUtils.indexOfDifference(new String[] {"", "abc"}) = 0
     * StringUtils.indexOfDifference(new String[] {"abc", ""}) = 0
     * StringUtils.indexOfDifference(new String[] {"abc", "abc"}) = -1
     * StringUtils.indexOfDifference(new String[] {"abc", "a"}) = 1
     * StringUtils.indexOfDifference(new String[] {"ab", "abxyz"}) = 2
     * StringUtils.indexOfDifference(new String[] {"abcde", "abxyz"}) = 2
     * StringUtils.indexOfDifference(new String[] {"abcde", "xyz"}) = 0
     * StringUtils.indexOfDifference(new String[] {"xyz", "abcde"}) = 0
     * StringUtils.indexOfDifference(new String[] {"i am a machine", "i am a robot"}) = 7
     * </pre>
     *
     * @param css  array of CharSequences, entries may be null
     * @return the index where the strings begin to differ; -1 if they are all equal
     * @since 2.4
     * @since 3.0 Changed signature from indexOfDifference(String...) to indexOfDifference(CharSequence...)
     */
    public static int indexOfDifference(CharSequence... css) {
        if (css == null || css.length <= 1) {
            return StringUtil.INDEX_NOT_FOUND;
        }
        boolean anyStringNull = false;
        boolean allStringsNull = true;
        int arrayLen = css.length;
        int shortestStrLen = Integer.MAX_VALUE;
        int longestStrLen = 0;

        // find the min and max string lengths; this avoids checking to make
        // sure we are not exceeding the length of the string each time through
        // the bottom loop.
        for (CharSequence cs : css) {
            if (cs == null) {
                anyStringNull = true;
                shortestStrLen = 0;
            }
            else {
                allStringsNull = false;
                shortestStrLen = Math.min(cs.length(), shortestStrLen);
                longestStrLen = Math.max(cs.length(), longestStrLen);
            }
        }

        // handle lists containing all nulls or all empty strings
        if (allStringsNull || longestStrLen == 0 && !anyStringNull) {
            return StringUtil.INDEX_NOT_FOUND;
        }

        // handle lists containing some nulls or some empty strings
        if (shortestStrLen == 0) {
            return 0;
        }

        // find the position with the first difference across all strings
        int firstDiff = -1;
        for (int stringPos = 0; stringPos < shortestStrLen; stringPos++) {
            char comparisonChar = css[0].charAt(stringPos);
            for (int arrayPos = 1; arrayPos < arrayLen; arrayPos++) {
                if (css[arrayPos].charAt(stringPos) != comparisonChar) {
                    firstDiff = stringPos;
                    break;
                }
            }
            if (firstDiff != -1) {
                break;
            }
        }

        if (firstDiff == -1 && shortestStrLen != longestStrLen) {
            // we compared all of the characters up to the length of the
            // shortest string and didn't find a match, but the string lengths
            // vary, so return the length of the shortest string.
            return shortestStrLen;
        }
        return firstDiff;
    }

    /**
     * 字符转成大写
     *
     * @param c
     * @return
     */
    public static char upperCaseChar(char c) {
        if ('a' <= c && c <= 'z') {
            //等同于c-=32 更快
            c ^= 32;
        }
        return c;
    }

    /**
     * 首字母大写
     *
     * @param srcStr
     * @return
     */
    public static String initialToUpper(String srcStr) {
        if (StringUtil.isBlank(srcStr)) {
            return srcStr;
        }
        char[] cs = srcStr.toCharArray();
        cs[0] += 32;
        return String.valueOf(cs);
    }

    /**
     * 首字母小写
     *
     * @param srcStr
     * @return
     */
    public static String initialToLower(String srcStr) {
        if (StringUtil.isBlank(srcStr)) {
            return srcStr;
        }
        char[] cs = srcStr.toCharArray();
        cs[0] += 32;
        return String.valueOf(cs);
    }


    /**
     * @param sourceText
     * @param prefix
     * @return
     */
    public static boolean startsWith(String sourceText, String prefix) {
        if (StringUtil.isBlank(sourceText)) {
            return false;
        }
        else {
            return sourceText.startsWith(prefix);
        }
    }

    /**
     * @param sourceText
     * @param suffix
     * @return
     */
    public static boolean endsWith(String sourceText, String suffix) {
        if (StringUtil.isBlank(sourceText)) {
            return false;
        }
        else {
            return sourceText.endsWith(suffix);
        }
    }

    public static Long getLong(String num, boolean nullToZero) {
        Long res;
        long lnum;
        if (nullToZero) {
            if ("".equalsIgnoreCase(num) || num == null) {
                res = 0L;
            }
            else {
                lnum = Long.parseLong(num);
                res = lnum;
            }
        }
        else {
            if ("".equalsIgnoreCase(num) || num == null) {
                res = null;
            }
            else {
                lnum = Long.parseLong(num);
                res = lnum;
            }
        }
        return res;
    }

    /**
     * <pre>
     * StringUtil.substringAfterFirst(null,"_") = null
     * StringUtil.substringAfterFirst("","_")   = ""
     * StringUtil.substringAfterFirst(abc,null) = abc
     * StringUtil.substringAfterFirst(abc,"_")  = abc
     * StringUtil.substringAfterFirst(ab_c,"_") = c
     * StringUtil.substringAfterFirst(_ab_c,"_") = ab_c
     * StringUtil.substringAfterFirst(abc_,"_") = ""
     * </pre>
     */
    public static String substringAfterFirst(String str, String separator) {
        if ((str == null) || (separator == null) || (str.length() == 0)) {
            return str;
        }

        if (separator.length() == 0) {
            return "";
        }

        int pos = str.indexOf(separator);

        if (pos == -1) {
            return str;
        }

        return str.substring(pos + separator.length());
    }


    /**
     * Equals boolean.
     *
     * @param a the a
     * @param b the b
     * @return boolean
     */
    public static boolean equals(String a, String b) {
        if (a == null) {
            return b == null;
        }
        return a.equals(b);
    }

    /**
     * Equals ignore case boolean.
     *
     * @param a the a
     * @param b the b
     * @return the boolean
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        if (a == null) {
            return b == null;
        }
        return a.equalsIgnoreCase(b);
    }

}
