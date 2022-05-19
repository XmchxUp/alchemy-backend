package io.github.xmchxup.backend.repository;

import io.github.xmchxup.backend.model.Pin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@SpringBootTest
public class PinTest {
    @Autowired
    private PinRepository pinRepository;

    @Transactional
    @Test
    public void test() {
        for (Pin pin : pinRepository.findAll()) {
            System.out.println(pin);
            System.out.println(pin.getOwner());
        }

        System.out.println("UID: 46 Created");
        pinRepository.findAllCreatedByUserId(46L).forEach(System.out::println);

        System.out.println("UID: 46 Saved");
        pinRepository.findAllSavedByUserId(46L).forEach(System.out::println);
    }
}
