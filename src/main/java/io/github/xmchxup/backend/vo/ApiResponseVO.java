package io.github.xmchxup.backend.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiResponseVO {
    private Boolean success;
    private String message;
}
