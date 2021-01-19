package com.edusoft.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lego-jspx01 on 2020/12/25.
 */
public class AuthVO {
    @ApiModelProperty("scope")
    private String scope;
    @ApiModelProperty("responseType")
    private String responseType;
    @ApiModelProperty("authUrl")
    private String authUrl;
    @ApiModelProperty("clientId")
    private String clientId;
    @ApiModelProperty("secret")
    private String secret;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
