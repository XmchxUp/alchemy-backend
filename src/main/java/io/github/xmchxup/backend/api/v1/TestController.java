package io.github.xmchxup.backend.api.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@RestController
@RequestMapping("/v1/test")
@Api(tags = "用户管理")
@Validated
public class TestController {

    @ApiOperation("用户列表")
    @GetMapping("/hello")
    public String hello(@NotBlank String name) {
        return "Hello, " + name;
    }

    @PostMapping("/t1")
    public String test(@Validated @RequestBody PersonDTO p) {
        return p.getTotalPrice().toString();
    }
}
