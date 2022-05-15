package io.github.xmchxup.backend.core;

import io.github.xmchxup.backend.exception.CreateSuccess;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
public class UnifyResponse {
    private final int code;
    private final String message;
    private final String request;


    public UnifyResponse(int code, String message, String request) {
        this.code = code;
        this.message = message;
        this.request = request;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getRequest() {
        return request;
    }

    public static void createSuccess(int code) {
        throw new CreateSuccess(code);
    }
}
