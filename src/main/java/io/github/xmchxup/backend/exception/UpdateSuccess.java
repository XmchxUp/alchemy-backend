package io.github.xmchxup.backend.exception;

import io.github.xmchxup.backend.exception.http.HttpException;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
public class UpdateSuccess extends HttpException {
    public UpdateSuccess(int code) {
        this.httpStatusCode = 200;
        this.code = code;
    }
}