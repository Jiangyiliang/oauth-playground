package com.edusoft.client.model;

import java.io.Serializable;

public class OauthClientDetailsKey implements Serializable {
    private String clientId;

    private static final long serialVersionUID = 1L;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }
}