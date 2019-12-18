package com.kitsrc.watt.core;

import com.kitsrc.watt.core.json.JSONUtil;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/1/14
 * @time : 13:39
 * Description:
 */
public class WebUtil {
	/**
	 * 转换成&连接的参数字符串形式
	 *
	 * @param jsonText
	 * @return
	 */
	public static String toGetParamsStr(String jsonText) {
		if (ObjectUtil.isEmpty(jsonText)) {
			return "";
		}
		Map<String, Object> paramMap = JSONUtil.toMap(jsonText);
		return toGetParamsStr(paramMap);
	}

	/**
	 * 转换成&连接的参数字符串形式
	 *
	 * @param paramObj
	 * @return
	 */
	public static String toGetParamsStr(Object paramObj) {
		if (ObjectUtil.isEmpty(paramObj)) {
			return "";
		}
		String jsonText = JSONUtil.toJSONString(paramObj);
		return toGetParamsStr(jsonText);
	}

	public static String toGetParamsStr(Map<String, Object> paramMap) {
		if (ObjectUtil.isEmpty(paramMap)) {
			return "";
		}
		StringBuilder paramStr = new StringBuilder();
		Set<Map.Entry<String, Object>> paramEntrySet = paramMap.entrySet();
		for (Map.Entry<String, Object> paramEntry : paramEntrySet) {
			paramStr.append(paramEntry.getKey());
			paramStr.append("=");
			paramStr.append(paramEntry.getValue());
			paramStr.append("&");

		}
		return paramStr.toString();
	}

	public static String required(HttpServletRequest req, String key) {
		String value = req.getParameter(key);
		if (StringUtils.isEmpty(value)) {
			throw new IllegalArgumentException("Param '" + key + "' is required.");
		}

		String encoding = req.getParameter("encoding");
		if (!StringUtils.isEmpty(encoding)) {
			try {
				value = new String(value.getBytes(StandardCharsets.UTF_8), encoding);
			}
			catch (UnsupportedEncodingException ignore) {
			}
		}

		return value.trim();
	}

	public static String optional(HttpServletRequest req, String key, String defaultValue) {

		if (!req.getParameterMap().containsKey(key) || req.getParameterMap().get(key)[0] == null) {
			return defaultValue;
		}

		String value = req.getParameter(key);

		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}

		String encoding = req.getParameter("encoding");
		if (StringUtil.isNotEmpty(encoding)) {
			try {
				value = new String(value.getBytes(StandardCharsets.UTF_8), encoding);
			}
			catch (UnsupportedEncodingException ignore) {
			}
		}

		return value.trim();
	}

	public static String getAcceptEncoding(HttpServletRequest req) {
		String encode = StringUtil.defaultIfEmpty(req.getHeader("Accept-Charset"), "UTF-8");
		encode = encode.contains(",") ? encode.substring(0, encode.indexOf(",")) : encode;
		return encode.contains(";") ? encode.substring(0, encode.indexOf(";")) : encode;
	}
}
