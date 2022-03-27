package io.github.xmchxup.backend.repository;

import io.github.xmchxup.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUnifyUid(String unifyUid);

    Optional<User> findByUnifyUidOrEmail(String unifyUid, String email);

    List<User> findByIdIn(List<Long> userIds);

    Boolean existsByUnifyUid(String unifyUid);

    Boolean existsByEmail(String email);
}
