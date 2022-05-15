package io.github.xmchxup.backend.exception;

import io.github.xmchxup.backend.exception.http.HttpException;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
public class CreateSuccess extends HttpException {
    public CreateSuccess(int code) {
        this.httpStatusCode = 201;
        this.code = code;
    }
}
