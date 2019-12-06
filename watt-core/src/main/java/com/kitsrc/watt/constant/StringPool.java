/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.kitsrc.watt.constant;

import java.util.regex.Pattern;

/**
 * Copy to jodd.util
 * <p>
 * Pool of <code>String</code> constants to prevent repeating of
 * hard-coded <code>String</code> literals in the code.
 * Due to fact that these are <code></code>
 * they will be inlined by java compiler and
 * reference to this class will be dropped.
 * There is <b>no</b> performance gain of using this pool.
 * Read: https://java.sun.com/docs/books/jls/third_edition/html/lexical.html#3.10.5
 * <ul>
 * <li>Literal strings within the same class in the same package represent references to the same <code>String</code> object.</li>
 * <li>Literal strings within different classes in the same package represent references to the same <code>String</code> object.</li>
 * <li>Literal strings within different classes in different packages likewise represent references to the same <code>String</code> object.</li>
 * <li>Strings computed by constant expressions are computed at compile time and then treated as if they were literals.</li>
 * <li>Strings computed by concatenation at run time are newly created and therefore distinct.</li>
 * </ul>
 */
public interface StringPool {
    /**
     * 大写 NULL 字符串 "NULL"
     */
    String UPPER_NULL = "NULL";
    /**
     * 小写 null 字符串 "null"
     */
    String LOWER_NULL = "null";
    /**
     * undefined
     */
    String UNDEFINED = "undefined";
    /**
     * 下划线
     */
    char UNDERLINE = '_';
    /**
     * 英文文本内容里的连字符
     */
    char HYPHEN = '-';
    /**
     * empty string
     */
    String EMPTY = "";
    /**
     * 分号
     */
    String SEMICOLON = ";";
    /**
     * 斜杠
     */
    String SLASH = "/";
    /**
     * 反斜杠
     */
    String BACKSLASH = "\\";
    /**
     * 标识索引搜索失败
     */
    int INDEX_NOT_FOUND = -1;
    /**
     * 空格
     */
    String SPACE = " ";
    /**
     * 制表符
     */
    String TAB = "\t";
    /**
     * 点 英文句号
     */
    String DOT = ".";
    /**
     * 点 英文句号 两个
     */
    String DOUBLE_DOT = "..";
    /**
     *
     */
    String CR = "\r";
    /**
     *
     */
    String LF = "\n";
    /**
     *
     */
    String CRLF = "\r\n";
    /**
     * 减号 虚线
     */
    String DASHED = "-";
    /**
     * 逗号
     */
    String COMMA = ",";
    /**
     * 大括号 start
     */
    String DELIM_START = "{";
    /**
     * 大括号 end
     */
    String DELIM_END = "}";
    /**
     * 方括号 start
     */
    String BRACKET_START = "[";
    /**
     * 方括号 end
     */
    String BRACKET_END = "]";
    /**
     * 冒号
     */
    String COLON = ":";

    String AMPERSAND = "&";
    String AND = "and";
    String AT = "@";
    String ASTERISK = "*";
    String STAR = ASTERISK;
    String BACK_SLASH = "\\";

    String DASH = "-";
    String DOLLAR = "$";
    String DOTDOT = "..";
    String DOT_CLASS = ".class";
    String DOT_JAVA = ".java";
    String DOT_XML = ".xml";
    String EQUALS = "=";
    String FALSE = "false";
    String HASH = "#";
    String HAT = "^";
    String LEFT_BRACE = "{";
    String LEFT_BRACKET = "(";
    String LEFT_CHEV = "<";
    String DOT_NEWLINE = ",\n";
    String NEWLINE = "\n";
    String N = "n";
    String NO = "no";
    String NULL = "null";
    String OFF = "off";
    String ON = "on";
    String PERCENT = "%";
    String PIPE = "|";
    String PLUS = "+";
    String QUESTION_MARK = "?";
    String EXCLAMATION_MARK = "!";
    String QUOTE = "\"";
    String RETURN = "\r";
    String RIGHT_BRACE = "}";
    String RIGHT_BRACKET = ")";
    String RIGHT_CHEV = ">";
    String SINGLE_QUOTE = "'";
    String BACKTICK = "`";
    String TILDA = "~";
    String LEFT_SQ_BRACKET = "[";
    String RIGHT_SQ_BRACKET = "]";
    String TRUE = "true";
    String UNDERSCORE = "_";
    String UTF_8 = "UTF-8";
    String US_ASCII = "US-ASCII";
    String ISO_8859_1 = "ISO-8859-1";
    String Y = "y";
    String YES = "yes";
    String ONE = "1";
    String ZERO = "0";
    String DOLLAR_LEFT_BRACE = "${";
    String HASH_LEFT_BRACE = "#{";

    String HTML_NBSP = "&nbsp;";
    String HTML_AMP = "&amp";
    String HTML_QUOTE = "&quot;";
    String HTML_LT = "&lt;";
    String HTML_GT = "&gt;";

    // ---------------------------------------------------------------- array

    String[] EMPTY_ARRAY = new String[0];

    byte[] BYTES_NEW_LINE = NEWLINE.getBytes();

    /*
     * 预编译正则表达式
     * */
    /**
     * 匹配字符串中所有不可见字符 空格 制表符 换行符 \s 可以匹配空格、制表符、换页符等空白字符
     */
    Pattern PATTERN_BLANK_CHARACTER = Pattern.compile("\\s*|\t*|\r*|\n*");
}
