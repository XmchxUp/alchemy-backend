package io.github.xmchxup.backend.core.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/**
 * spring security 5之前可以拿道自定义的UserDetails(JwtUser)
 * @author huayang (sunhuayangak47@gmail.com)
 * @see CurrentUserUtils
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {
}