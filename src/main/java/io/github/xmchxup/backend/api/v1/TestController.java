package io.github.xmchxup.backend.api.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@RestController
@RequestMapping("/v1/test")
@Api(tags="用户管理")
public class TestController {

    @ApiOperation("用户列表")
    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }
}
