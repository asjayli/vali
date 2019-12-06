package com.kitsrc.watt.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vandream.hamster.core.constant.Constants;
import lombok.Cleanup;
import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/3/21
 * @time : 19:35
 * Description:
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class StringUtil extends StringUtils {


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
		int of = underlineName.indexOf((int) Constants.UNDERLINE);
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
			if (ch == Constants.UNDERLINE) {
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
					builder.append(Constants.UNDERLINE);
				}
			}
			else if (Character.isDigit(c) || Character.isLowerCase(c) || Character
					.compare(c, Constants.UNDERLINE) == 0) {
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

			Matcher m = PATTERN_BLANK_CHARACTER.matcher(str);
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
		if (isBlank(sourceStr)) {
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
		if (isBlank(cs)) {
			return false;
		}
		if (Constants.UPPER_NULL.equals(cs) || Constants.LOWER_NULL.equals(cs)) {
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
		if (isNullStr(sourceStr)) {
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
		return isNullStr(cs);
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
		return !isBlank(cs);
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
	public static String getFirstDigit(final String str) {
		if (isBlank(str)) {
			return str;
		}
		if (str.contains("\\u")) {
			return "";
		}
		final int sz = str.length();
		final StringBuilder strDigits = new StringBuilder(sz);
		boolean flag = false;
		for (int i = 0; i < sz; i++) {
			final char tempChar = str.charAt(i);
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
	public static boolean isEmpty(final CharSequence cs) {
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
		if (isEmpty(sourceStr)) {
			return defaultValue;
		}
		return sourceStr;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}


	/**
	 * 字符转成大写
	 *
	 * @param c
	 * @return
	 */
	public static char upperCaseChar(char c) {
		if (97 <= c && c <= 122) {
			//等同于c-=32 更快
			c ^= 32;
		}
		return c;
	}

	/**
	 * 替换字符串并让它的下一个字母为大写
	 *
	 * @param srcStr
	 * @param org
	 * @param ob
	 * @return
	 */
	@Deprecated
	public static String replaceUnderlineAndfirstToUpper(String srcStr, String org, String ob) {
		StringBuilder newString = new StringBuilder();
		int first = 0;
		while (srcStr.indexOf(org) != -1) {
			first = srcStr.indexOf(org);
			if (first != srcStr.length()) {
				newString.append(srcStr.substring(0, first))
						.append(ob);
				srcStr = srcStr.substring(first + org.length());
				srcStr = capitalize(srcStr);
			}
		}
		newString.append(srcStr);
		return newString.toString();
	}

	/**
	 * 首字母大写
	 *
	 * @param srcStr
	 * @return
	 * @see StringUtil#initialToUpper(String) (String)
	 */
	@Deprecated
	public static String firstCharacterToUpper(String srcStr) {
		return initialToUpper(srcStr);
	}

	/**
	 * 首字母大写
	 *
	 * @param srcStr
	 * @return
	 */
	public static String initialToUpper(String srcStr) {
		if (isBlank(srcStr)) {
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
		if (isBlank(srcStr)) {
			return srcStr;
		}
		char[] cs = srcStr.toCharArray();
		cs[0] += 32;
		return String.valueOf(cs);
	}

	public static HashMap getURLParams(String urlStr) {
		HashMap params = new HashMap();
		if (urlStr == null || "".equalsIgnoreCase(urlStr)) {
			return params;
		}
		int paramStartIndex = urlStr.indexOf("?");
		int i;
		String param;
		String paramName;
		String paramValue;
		String paramStr = urlStr.substring(paramStartIndex + 1);
		StringTokenizer st = new StringTokenizer(paramStr, "&", false);
		while (st.hasMoreTokens()) {
			param = st.nextToken();
			i = param.indexOf("=");
			paramName = param.substring(0, i);
			paramValue = param.substring(i + 1);
			params.put(paramName, paramValue);
		}
		return params;
	}

	/**
	 * @param sourceText
	 * @param prefix
	 * @return
	 */
	public static boolean startsWith(String sourceText, String prefix) {
		if (isBlank(sourceText)) {
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
		if (isBlank(sourceText)) {
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

	public static long parseLong(String num) {
		long res;
		if ("".equalsIgnoreCase(num) || num == null) {
			res = 0;
		}
		else {
			res = Long.parseLong(num);
		}
		return res;
	}

	public static Integer getInteger(String num, boolean nullToZero) {
		Integer res;
		int inum;
		if (nullToZero) {
			if ("".equalsIgnoreCase(num) || num == null) {
				res = 0;
			}
			else {
				inum = Integer.parseInt(num);
				res = inum;
			}
		}
		else {
			if ("".equalsIgnoreCase(num) || num == null) {
				res = null;
			}
			else {
				inum = Integer.parseInt(num);
				res = inum;
			}
		}
		return res;
	}

	public static int parseInt(String num) {
		int res;
		if ("".equalsIgnoreCase(num) || num == null) {
			res = 0;
		}
		else {
			res = Integer.parseInt(num);
		}
		return res;
	}


	/**
	 * @param source
	 * @param delimiter
	 * @param appendTail 是否在字符串末尾增加delimiter
	 * @return
	 * @功能 将soure数组变成通过delimiter链接的字符串
	 */
	@Deprecated
	public static <T> String arrayToString(T[] source, String delimiter, boolean appendTail) {
		if (source == null) {
			return "";
		}
		StringBuffer bf = new StringBuffer();
		for (int i = 0; i < source.length; i++) {
			if (i == 0) {
				bf.append(source[i]);
			}
			else {
				bf.append(delimiter + source[i]);
			}
		}
		if (appendTail) {
			bf.append(delimiter);
		}
		return bf.toString();
	}

	/**
	 * @param source
	 * @param delimiter
	 * @param appendTail 是否在字符串末尾增加delimiter
	 * @return
	 * @功能 将soure集合变成通过delimiter链接的字符串
	 */
	@Deprecated
	public static <T> String listToString(List<T> source, String delimiter, boolean appendTail) {
		if (source == null) {
			return "";
		}
		StringBuffer bf = new StringBuffer();
		for (int i = 0; i < source.size(); i++) {
			if (i == 0) {
				bf.append(source.get(i));
			}
			else {
				bf.append(delimiter + source.get(i));
			}
		}
		if (appendTail) {
			bf.append(delimiter);
		}
		return bf.toString();
	}

	/**
	 * @param source
	 * @return
	 * @功能 将source集合变成有，链接的字符串，并将source集合的每个值的两端加上单引号
	 * @说明 数据库设置in (param) 参数专用
	 */
	@Deprecated
	public static String listToStringForSqlInParam(List<String> source) {
		if (source == null) {
			return "";
		}
		String delimiter = ",";
		StringBuffer bf = new StringBuffer();
		for (int i = 0; i < source.size(); i++) {
			if (i == 0) {
				bf.append("'" + source.get(i) + "'");
			}
			else {
				bf.append(delimiter + "'" + source.get(i) + "'");
			}
		}
		return bf.toString();
	}

	/**
	 * @param inString
	 * @param oldPattern
	 * @param newPattern
	 * @return
	 */
	@Deprecated
	public static String replace(String inString, String oldPattern, String newPattern) {
		if (inString == null) {
			return null;
		}
		if (oldPattern == null || newPattern == null) {
			return inString;
		}
		StringBuffer sbuf = new StringBuffer();
		int pos = 0; // Our position in the old string
		int index = inString.indexOf(oldPattern);
		int patLen = oldPattern.length();
		while (index >= 0) {
			sbuf.append(inString, pos, index);
			sbuf.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sbuf.append(inString.substring(pos));
		return sbuf.toString();
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

	/**
	 * @param old
	 * @return
	 */
	@Deprecated
	public static String replaceHtml(String old) {
		String rt = replace(old, "<", "&lt;");
		rt = replace(rt, ">", "&gt;");
		rt = replace(rt, "'", "&quot;");
		rt = replace(rt, "'", "&quot;");
		rt = replace(rt, "\"", "&quot;");
		return rt;
	}

	/**
	 * Input stream 2 string string.
	 *
	 * @param is the is
	 * @return the string
	 */
	public static String inputStream2String(InputStream is) throws IOException {
		if (is == null) {
			return null;
		}
		@Cleanup ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString(Constants.DEFAULT_CHARSET_NAME);

	}

	/**
	 * Input stream to byte array
	 *
	 * @param is the is
	 * @return the byte array
	 */
	public static byte[] inputStream2Bytes(InputStream is) throws IOException {
		if (is == null) {
			return null;
		}
		@Cleanup ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toByteArray();

	}
    /**
     * 字符串生成 格式化 支持{} 和 {0},{1} 但括号内数字无任何意义 仅作为兼容性措施
     *
     * @param template
     * @param arguments 参数严格有序
     * @return
     */
    public static String format(CharSequence template, Object... arguments) {
        if (null == template) {
            return null;
        }
        if (ObjectUtil.isEmpty(arguments) || isBlank(template)) {
            return template.toString();
        }
        return StringFormat.format(template.toString(), arguments);
    }
}
