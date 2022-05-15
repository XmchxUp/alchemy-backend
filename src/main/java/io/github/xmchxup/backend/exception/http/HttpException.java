package io.github.xmchxup.backend.exception.http;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
public class HttpException extends RuntimeException {
    protected Integer code;
    protected Integer httpStatusCode = 500;

    public Integer getCode() {
        return code;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }
}
