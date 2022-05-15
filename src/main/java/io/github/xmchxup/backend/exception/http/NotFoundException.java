package io.github.xmchxup.backend.exception.http;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
public class NotFoundException extends HttpException {
    public NotFoundException(int code) {
        this.httpStatusCode = 404;
        this.code = code;
    }
}