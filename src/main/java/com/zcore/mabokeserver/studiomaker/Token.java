package com.zcore.mabokeserver.studiomaker;

public class Token {
    private String code;
    private String scope;
    private String accessToken;

    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getScope() {
        return scope;
    }


    public void setScope(String scope) {
        this.scope = scope;
    }


    public String getAccessToken() {
        return accessToken;
    }


    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    @Override
    public String toString() {
        return "Token [code=" + code + ", scope=" + scope + ", accessToken=" + accessToken + "]";
    }

    
}
