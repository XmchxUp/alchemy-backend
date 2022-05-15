package io.github.xmchxup.backend.service;

import io.github.xmchxup.backend.exception.http.ServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Slf4j
@Service
public class LocalUploadService {
    @Value("${file.uploadFolder}")
    private String realBasePath;
    @Value("${file.accessPath}")
    private String accessPath;

    public String storeImage(MultipartFile file) {
        // TODO: 添加MD5，用来文件检验是否已经存在
        Date now = new Date();
        String today = new SimpleDateFormat("yyyy/MM/dd").format(now);
        String fileName = UUID.randomUUID() + file.getOriginalFilename();
        String webPath = accessPath + today + "/" + fileName;
        String realPath = realBasePath + today + "/";

        try {
            File destFile = new File(realPath + fileName);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(destFile));
            stream.write(file.getBytes());
            stream.close();
        } catch (Exception e) {
            log.error("write file to local err:", e);
            throw new ServerErrorException(30003);
        }
        log.info(webPath);
        return webPath;
    }
}
