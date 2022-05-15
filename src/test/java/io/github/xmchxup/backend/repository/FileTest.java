package io.github.xmchxup.backend.repository;

import io.github.xmchxup.backend.model.DBFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@SpringBootTest
public class FileTest {
    @Autowired
    private DBFileRepository fileRepository;

    @Transactional
    @Test
    public void test() {
        List<DBFile> files = fileRepository.findDBFilesByOwnerId(46L);
        files.forEach(System.out::println);

        fileRepository.deleteById("89c0bd9c-d49a-4f01-b93d-359fc2854f0d", new Date());
    }
}
