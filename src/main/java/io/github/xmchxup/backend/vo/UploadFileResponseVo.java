package io.github.xmchxup.backend.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UploadFileResponseVo {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
