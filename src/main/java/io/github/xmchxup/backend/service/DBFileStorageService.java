package io.github.xmchxup.backend.service;

import io.github.xmchxup.backend.exception.http.ServerErrorException;
import io.github.xmchxup.backend.model.DBFile;
import io.github.xmchxup.backend.repository.DBFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DBFileStorageService {

    @Autowired
    private DBFileRepository dbFileRepository;

    public DBFile storeFile(MultipartFile file){
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")) {
                throw new ServerErrorException(30002);
            }

            DBFile dbFile = new DBFile(fileName,file.getContentType(), file.getBytes());

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new ServerErrorException(30000);
        }
    }

    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId).orElseThrow(() -> new ServerErrorException(30001));
    }
}
