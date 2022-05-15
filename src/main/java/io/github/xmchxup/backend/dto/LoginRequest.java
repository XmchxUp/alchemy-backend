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
    @NotBlank(message = "账号不能为空")
    private String usernameOrEmail;
    @NotBlank(message = "密码不能为空")
    private String password;
    private Boolean rememberMe;
}
