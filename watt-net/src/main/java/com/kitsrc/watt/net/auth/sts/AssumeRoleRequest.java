

package com.kitsrc.watt.net.auth.sts;

/**
 * Created by haowei.yao on 2017/9/14.
 */

import com.kitsrc.watt.net.RpcAcsRequest;
import com.kitsrc.watt.net.http.ProtocolType;

/**
 * @author auto create
 */
public class AssumeRoleRequest extends RpcAcsRequest<AssumeRoleResponse> {

    private Long durationSeconds;
    private String policy;
    private String roleArn;
    private String roleSessionName;

    public AssumeRoleRequest() {
        super("Sts", "2015-04-01", "AssumeRole");
        setSysProtocol(ProtocolType.HTTPS);
    }

    public Long getDurationSeconds() {
        return this.durationSeconds;
    }

    public void setDurationSeconds(Long durationSeconds) {
        this.durationSeconds = durationSeconds;
        putQueryParameter("DurationSeconds", String.valueOf(durationSeconds));
    }

    public String getPolicy() {
        return this.policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
        putQueryParameter("Policy", policy);
    }

    public String getRoleArn() {
        return this.roleArn;
    }

    public void setRoleArn(String roleArn) {
        this.roleArn = roleArn;
        putQueryParameter("RoleArn", roleArn);
    }

    public String getRoleSessionName() {
        return this.roleSessionName;
    }

    public void setRoleSessionName(String roleSessionName) {
        this.roleSessionName = roleSessionName;
        putQueryParameter("RoleSessionName", roleSessionName);
    }

    @Override
    public Class<AssumeRoleResponse> getResponseClass() {
        return AssumeRoleResponse.class;
    }

}