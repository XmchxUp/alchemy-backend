package io.github.xmchxup.backend.repository;

import io.github.xmchxup.backend.model.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {
}
