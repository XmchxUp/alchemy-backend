package io.github.xmchxup.backend.util;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Slf4j
public class Jackson2Test {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class Person {
        private String firstName;
        private String secondName;

        @Override
        public String toString() {
            return "Person{" +
                    "firstName='" + firstName + '\'' +
                    ", secondName='" + secondName + '\'' +
                    '}';
        }
    }

    @Test
    public void testEntity() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        String s = mapper.writeValueAsString(
                Person.builder()
                        .firstName("huayang")
                        .secondName("Sun")
        );
        log.info(s);

        Person person = mapper.readValue(s, Person.class);
        System.out.println(person.toString());
    }

    @Test
    public void testMap() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
//        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        Map<String, Object> res = new HashMap<>();
        res.put("username", "pedro");
        res.put("userAge", 24);
        String s = mapper.writeValueAsString(res);
        log.info(s);

        log.info(mapper.readValue(s, Map.class).toString());
    }

    @Test
    public void testCamel() throws JsonProcessingException {
        String str = "userAge";
        String s = StrUtil.toUnderlineCase(str);
        log.info(s);
    }
}
