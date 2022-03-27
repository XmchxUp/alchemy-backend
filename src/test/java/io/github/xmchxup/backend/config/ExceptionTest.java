package io.github.xmchxup.backend.config;

import io.github.xmchxup.backend.core.configuration.ExceptionCodeConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@SpringBootTest
public class ExceptionTest {
    @Autowired
    private ExceptionCodeConfiguration ex;

    @Value("${sharing.cors.allowedOrigins}")
    private String[] allowedOrigins;

    @Test
    void test() {
        System.out.println(ex.getMessage(10002));
        for (String allowedOrigin : allowedOrigins) {
            System.out.println(allowedOrigin);
        }
    }
}
