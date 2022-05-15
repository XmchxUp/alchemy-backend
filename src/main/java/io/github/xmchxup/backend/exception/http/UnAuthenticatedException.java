package io.github.xmchxup.backend.exception.http;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
public class UnAuthenticatedException extends HttpException {
    public UnAuthenticatedException(int code) {
        this.code = code;
        this.httpStatusCode = 401;
    }
}