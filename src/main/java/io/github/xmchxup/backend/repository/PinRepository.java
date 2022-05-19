package io.github.xmchxup.backend.repository;

import io.github.xmchxup.backend.model.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Repository
public interface PinRepository extends JpaRepository<Pin, Long> {

    @Modifying
    @Query("update Pin p set p.deleteTime = :now where p.id = :pid")
    void deleteById(Long pid, Date now);

    @Query("select p from Pin p where p.owner.id = :uid order by p.createTime desc")
    List<Pin> findAllCreatedByUserId(Long uid);

    @Query("select p from Pin p join p.saves s where s.uid = :uid and p.id = s.pid")
    List<Pin> findAllSavedByUserId(Long uid);
}
