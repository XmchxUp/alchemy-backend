package io.github.xmchxup.backend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class LoginRequest {
    @NotBlank
    private String usernameOrEmail;
    @NotBlank
    private String password;
    private Boolean rememberMe;
}
