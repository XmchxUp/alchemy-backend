package io.github.xmchxup.backend.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class JwtAuthenticationResponseVO {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponseVO(String accessToken) {
        this.accessToken = accessToken;
    }
}
