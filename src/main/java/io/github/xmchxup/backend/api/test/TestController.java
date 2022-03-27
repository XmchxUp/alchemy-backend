package io.github.xmchxup.backend.api.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@RestController
@RequestMapping("/api/test")
@Api(tags = "小测试")
@Validated
public class TestController {

    @ApiOperation("Hello测试")
    @GetMapping("/hello")
    public String hello(@NotBlank String name) {
        return "Hello, " + name;
    }

    @PostMapping("/t1")
    public String test(@Validated @RequestBody PersonDTO p) {
        return p.getTotalPrice().toString();
    }
}
