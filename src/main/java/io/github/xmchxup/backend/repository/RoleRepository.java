package io.github.xmchxup.backend.repository;

import io.github.xmchxup.backend.enumeration.RoleName;
import io.github.xmchxup.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
