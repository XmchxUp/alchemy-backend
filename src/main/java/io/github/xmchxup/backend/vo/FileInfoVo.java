package io.github.xmchxup.backend.vo;

import io.github.xmchxup.backend.model.DBFile;
import lombok.Getter;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Getter
public class FileInfoVo {
    private String fileName;
    private String fileType;
    private Long ownerId;

    public FileInfoVo(DBFile file) {
        this.fileName = file.getFileName();
        this.fileType = file.getFileType();
        this.ownerId = file.getOwnerId();
    }
}
