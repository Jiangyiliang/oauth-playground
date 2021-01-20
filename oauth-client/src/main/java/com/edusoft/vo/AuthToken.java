package com.edusoft.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lego-jspx01 on 2020/12/25.
 */
public class AuthToken {
    @ApiModelProperty("认证服务返回code")
    private String code;
    @ApiModelProperty("获取tokenUrl")
    private String tokenUrl;
    @ApiModelProperty("clientId")
    private String clientId;
    @ApiModelProperty("secret")
    private String clientSecret;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
