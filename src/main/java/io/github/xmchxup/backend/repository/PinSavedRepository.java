package io.github.xmchxup.backend.repository;

import io.github.xmchxup.backend.model.PinSaved;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Repository
public interface PinSavedRepository extends JpaRepository<PinSaved, Long> {
}
