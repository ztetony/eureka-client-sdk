package com.tonysfriend.ms.constant;

public enum MethodType {

    REG_POST("POST"),
    APPS_GET("GET"),
    CANCEL_DELETE("DELETE "),
    HB_PUT("PUT");

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    MethodType(String name) {
        this.name = name;
    }

}
