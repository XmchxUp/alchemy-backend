package io.github.xmchxup.backend.api.v1;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@RestController
@RequestMapping("/test")
@ApiOperation("test")
public class TestController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }
}
