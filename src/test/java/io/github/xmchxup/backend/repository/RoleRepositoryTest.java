package io.github.xmchxup.backend.repository;

import io.github.xmchxup.backend.enumeration.RoleType;
import io.github.xmchxup.backend.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@SpringBootTest
public class RoleRepositoryTest {
    @Autowired
    RoleRepository roleRepository;



    @Test
    public void testInert() {
        roleRepository.save(Role.builder()
                .name(RoleType.ROLE_ADMIN).build());
    }
}
