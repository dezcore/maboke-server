package com.zcore.mabokeserver.studiomaker;

public class StudioMaker {
    private String code; 
    private String scope;
    
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

    @Override
    public String toString() {
        return "StudioMaker [code=" + code + ", scope=" + scope + "]";
    } 
}
