package io.github.xmchxup.backend.api.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@RestController
@RequestMapping("/api/test")
@Api(tags = "小测试")
@Validated
public class TestController {

    @ApiOperation("带有USER权限的Hello测试")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/hello")
    public String hello(@NotBlank String name) {
        return "Hello, " + name;
    }

    @ApiOperation("带有ADMIN权限Root Date测试")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/date")
    public LocalDate date() {
        return LocalDate.now();
    }

    @ApiOperation("whoami测试")
    @GetMapping("/whoami")
    public String whoami() {
        return "Tesla";
    }

    @ApiOperation("带有参数检验的测试")
    @PostMapping("/t1")
    public String test(@Validated @RequestBody PersonDTO p) {
        return p.getTotalPrice().toString();
    }
}
