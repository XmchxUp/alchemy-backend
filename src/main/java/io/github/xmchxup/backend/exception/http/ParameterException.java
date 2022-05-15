package io.github.xmchxup.backend.exception.http;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
public class ParameterException extends HttpException {
    public ParameterException(int code) {
        this.code = code;
        this.httpStatusCode = 400;
    }
}
