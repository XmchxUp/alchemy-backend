package io.github.xmchxup.backend.repository;

import io.github.xmchxup.backend.model.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Repository
public interface PinRepository extends JpaRepository<Pin, Long> {
}
