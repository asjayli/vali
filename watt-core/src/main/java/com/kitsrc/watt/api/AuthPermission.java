package com.kitsrc.watt.api;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * <p>用户鉴权相关接口定义</p>
 * <p>业务线实现该接口以SPI方式依赖实现类</p>
 *
 * @author : liguoqing
 * @date : 2018/12/17
 * Time: 16:23
 * Description:
 */
public interface AuthPermission {

    /**
     * 获取白名单列表
     *
     * @return
     */
    default List<String> getWhiteList() {
        return Collections.EMPTY_LIST;
    }


    /**
     * 服务用户权限校验
     *
     * @param serviceName
     * @param actionName
     * @param token
     * @return
     */
    Boolean hasLogin(String serviceName, String actionName, String token);

    /**
     * 服务用户权限校验
     *
     * @param serviceName
     * @param actionName
     * @param token
     * @return
     */
    Boolean hasPermission(String serviceName, String actionName, String token);

    /**
     * 服务用户权限校验
     *
     * @param token
     * @param requestUrl
     * @return
     */
    Boolean hasPermission(String token, String requestUrl);

    /**
     * 获取session信息 json
     *
     * @param token
     * @return
     */
    String getSessionInfo(String token);

    /**
     * erp 旧服务获取cookie信息 json
     *
     * @param token
     * @return
     */
    String getCookieInfo(String token);

    /**
     * 登出当前token 清理session相关信息
     *
     * @param token
     * @return
     */
    Boolean clearSession(String token);

    /**
     * 更新 共享存储中的 session信息 若key 不存在 返回false
     * 更新
     *
     * @param token
     * @param sessionJson
     * @return
     */
    Boolean updateSession(String token, String sessionJson);

    /**
     * 更新token失效时间
     *
     * @param token
     * @param expireTime
     * @return
     */
    Boolean updateSession(String token, Duration expireTime);

    /**
     * 更新session 信息 并且更新 token失效时间
     *
     * @param token
     * @param sessionInfoJson
     * @param expireTime
     * @return
     */
    Boolean updateSession(String token, String sessionInfoJson, Duration expireTime);
}
