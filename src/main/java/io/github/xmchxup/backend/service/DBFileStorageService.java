package io.github.xmchxup.backend.service;

import io.github.xmchxup.backend.core.security.CurrentUserUtils;
import io.github.xmchxup.backend.exception.http.ParameterException;
import io.github.xmchxup.backend.exception.http.ServerErrorException;
import io.github.xmchxup.backend.model.DBFile;
import io.github.xmchxup.backend.model.User;
import io.github.xmchxup.backend.repository.DBFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Service
public class DBFileStorageService {

    @Autowired
    private DBFileRepository dbFileRepository;

    @Autowired
    private CurrentUserUtils currentUserUtils;

    public DBFile storeFile(MultipartFile file) {
        // TODO: 数据库层可以添加MD5字段，用来文件检验是否已经存在，防止重复上传一样的文件
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        User currentUser = currentUserUtils.getCurrentUser()
                .orElseThrow(() -> new ParameterException(20007));

        try {
            if (fileName.contains("..")) {
                throw new ServerErrorException(30002);
            }

            DBFile dbFile = DBFile.builder()
                    .fileName(fileName)
                    .fileType(file.getContentType())
                    .data(file.getBytes())
                    .ownerId(currentUser.getId())
                    .build();

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new ServerErrorException(30000);
        }
    }

    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId).orElseThrow(() -> new ServerErrorException(30001));
    }

    @Transactional
    public void deleteById(String fileId) {
        User currentUser = currentUserUtils.getCurrentUser()
                .orElseThrow(() -> new ParameterException(20007));
        DBFile file = getFile(fileId);

        if (!currentUser.getId().equals(file.getOwnerId())) {
            throw new ServerErrorException(30005);
        }

        dbFileRepository.deleteById(fileId, new Date());
    }
}
