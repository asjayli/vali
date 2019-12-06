/*
 *  Copyright 1999-2019 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.kitsrc.watt.constant;

import java.nio.charset.Charset;

/**
 * The type Constants.
 *
 * @author jimin.jm @alibaba-inc.com
 * @date 2018 /10/9 17:14
 */
public interface Constants {


    /**
     * default charset name
     */
    String DEFAULT_CHARSET_NAME = "UTF-8";

    /**
     * default charset is utf-8
     */
    Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_CHARSET_NAME);
    /**
     * 大写 NULL 字符串 "NULL"
     */
    String UPPER_NULL = "NULL";
    /**
     * 小写 null 字符串 "null"
     */
    String LOWER_NULL = "null";
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
     * vandream 所有服务器环境 环境变量标志符  服务器编码名称
     * VCLOUD_SERVER=1
     */
    String KEY_VCLOUD_SERVER_FLAG = "VCLOUD_SERVER";
    /**
     * vandream 服务器签名 可识别 可解析 可扩展 数据结构紧凑 约定式结构
     * 环境:DEV,SIT,UAT,PROD
     * 类型:P 物理机 V 虚拟机 C 容器
     * 取值范围 [9~1023]  0-8 预留不允许占用
     * 环境:物理机实例编号:虚拟机实例编号:容器运行实例
     * DEV:P001 开发环境 物理机 001 无虚拟机 无容器实例
     * DEV:P001:V001 开发环境 物理机 001 虚拟机 001
     * DEV:P001:V001:C001 开发环境 物理机 001 虚拟机 001 容器实例 001
     * DEV:P001:0000:C001 开发环境 物理机 001 无虚拟机 容器实例 001
     */
    String KEY_VNM_SERVER_SIGN = "VCLOUD_SERVER_SIGN";
}
