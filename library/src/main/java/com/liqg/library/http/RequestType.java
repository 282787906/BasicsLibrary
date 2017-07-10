package com.liqg.library.http;

/**
 * Created by liqg
 * 2016/7/18 17:04
 * Note :
 */
public enum RequestType {
    GET("GET"),
    POST("POST"),
    HEAD("HEAD");
    private final String value;

    RequestType(String value) {
        this.value = value;
    }
}
