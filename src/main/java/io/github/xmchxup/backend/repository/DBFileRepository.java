package io.github.xmchxup.backend.repository;

import io.github.xmchxup.backend.model.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {
//    Genius 防止从数据库里查询bytes 查询速度直接起飞
    @Query("select new DBFile(f.fileName, f.fileType, f.ownerId) from DBFile f where f.ownerId = :ownerId")
    List<DBFile> findDBFilesByOwnerId(Long ownerId);

    @Modifying
    @Query("update DBFile f set f.deleteTime = :deleteTime where f.id = :uid")
    void deleteById(String uid, Date deleteTime);
}
