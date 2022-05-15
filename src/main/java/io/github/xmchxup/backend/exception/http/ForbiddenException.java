package io.github.xmchxup.backend.exception.http;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
public class ForbiddenException extends HttpException {
    public ForbiddenException(int code) {
        this.code = code;
        this.httpStatusCode = 403;
    }
}